package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.Application;
import uk.co.jamesmcguigan.forecaster.dataacquisition.DataAcquisitionRequester;
import uk.co.jamesmcguigan.forecaster.dataacquisition.DataAcquisitionResponse;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.StockRepository;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class HistoricalPriceRequest implements DataAcquisitionRequester {

    private StockRepository stockRepository;
    private PriceTransformer priceTransformer;

    @Override
    public Callable<DataAcquisitionResponse> create(String symbol) {

        Callable<DataAcquisitionResponse> callable = () -> {
            try {

                NetHttpTransport.Builder netBuilder = new NetHttpTransport.Builder();
//               Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
//                netBuilder.setProxy(proxy);
                Application.logger.info("Processing historical price for {}",symbol);
                HttpRequestFactory requestFactory = netBuilder.build().createRequestFactory();
                Request request = Request.builder()
                        .ChartPriceType("price")
                        .FromDate(null)
                        .Key(String.format("%s.LD",symbol.substring(4).toUpperCase()))
                        .KeyType("Topic")
                        .KeyType2("Topic")
                        .Language("en")
                        .OffSet(-60)
                        .RequestedDataSetType("ohlc")
                        .SampleTime("1d")
                        .TimeFrame("1y")
                        .ToDate(null)
                        .UseDelay(true)
                        .build();

                RequestWrapper requestWrapper = new RequestWrapper(request);
                HttpContent content = new JsonHttpContent(new JacksonFactory(), requestWrapper);

                HttpRequest httpRequest = requestFactory.buildPostRequest(
                        new GenericUrl("http://charts.londonstockexchange.com/WebCharts/services/ChartWService.asmx/GetPrices"),
                        content);
                httpRequest.getHeaders().setAccept("application/json, text/javascript, */*; q=0.01");
                httpRequest.getHeaders().setContentType(" application/json; charset=UTF-8");
                httpRequest.getHeaders().setAcceptEncoding("gzip, deflate, br");

                String rawResponse = httpRequest.execute().parseAsString();
                return new DataAcquisitionResponse(symbol, rawResponse);
            } catch (IOException e) {
                Application.logger.error(String.format("Unable to connect:%s", symbol), e);
                throw e;
            }
        };
        return callable;
    }

    @Override
    public void saveResponses(Set<Future<DataAcquisitionResponse>> set) {
        List<Stock> updatedStocks = Lists.newArrayList();
        set.forEach(
                chartFuture -> {
                    try {
                        DataAcquisitionResponse scrapeResponse = chartFuture.get();
                        List<Price> historicalPrices =  priceTransformer.transformFrom(scrapeResponse);
                        Stock stock = stockRepository.findBySymbol(scrapeResponse.getSymbol());
                        stock.setHistoricalPrices(historicalPrices);
                        updatedStocks.add(stock);
                    } catch (InterruptedException e) {
                        Application.logger.error("Error while saving web scrapes", e);
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        Application.logger.error("Error while saving web scrapes", e);
                    }
                }
        );
        stockRepository.saveAll(updatedStocks);
    }
}
