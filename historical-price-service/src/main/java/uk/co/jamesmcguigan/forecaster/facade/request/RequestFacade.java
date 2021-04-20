package uk.co.jamesmcguigan.forecaster.facade.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.jamesmcguigan.forecaster.facade.StockServiceClient;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class RequestFacade implements Runnable {

    private static final String ACCEPT_HEADERS = "application/json, text/javascript, */*; q=0.01";
    private static final String CONTENT_TYPE_HEADERS = " application/json; charset=UTF-8";
    private static final String ACCEPT_ENCODING = "gzip, deflate, br";
    private static final String PRICE_SOURCE_URL = "http://charts.londonstockexchange.com/WebCharts/services/ChartWService.asmx/GetPrices";
    private static final String UNABLE_TO_CONNECT_S = "Unable to connect:%s";
    private static final String PRICE = "price";
    private static final String S_LD = "%s.LD";
    private static final String TOPIC = "Topic";
    private static final String EN = "en";
    private static final String OHLC = "ohlc";
    private static final String D = "1d";
    private static final String Y = "1y";
    private static final String SYMBOL_IS_TOO_SHORT_S = "Symbol is too short %s";
    private static final String MAKING_REQUEST_FOR = "Making request for {}";
    private static final String RESPONSE_RECEIVED_FOR = "Response received for {}";
    private static final String CALLING_OUT_TO_STOCK_SERVICE_FOR = "Calling out to stock service for {}";
    private final String symbol;
    private final StockServiceClient stockServiceClient;
    private final HistoricalPriceRepresentationTransformer historicalPriceRepresentationTransformer;

    @Override
    public void run() {
        if (symbol.length() < 4) {
            throw new IllegalArgumentException(String.format(SYMBOL_IS_TOO_SHORT_S, symbol));
        }
        try {
            Request request = Request.builder()
                    .ChartPriceType(PRICE)
                    .FromDate(null)
                    .Key(String.format(S_LD, symbol.substring(4).toUpperCase()))
                    .KeyType(TOPIC)
                    .KeyType2(TOPIC)
                    .Language(EN)
                    .OffSet(-60)
                    .RequestedDataSetType(OHLC)
                    .SampleTime(D)
                    .TimeFrame(Y)
                    .ToDate(null)
                    .UseDelay(true)
                    .build();
            RequestWrapper requestWrapper = new RequestWrapper(request);
            HttpContent content = new JsonHttpContent(GsonFactory.getDefaultInstance(), requestWrapper);
            log.debug(MAKING_REQUEST_FOR, symbol);
            Response response = sendRequest(content);
            log.debug(RESPONSE_RECEIVED_FOR, symbol);
            HistoricalPriceRepresentation historicalPriceRepresentation = historicalPriceRepresentationTransformer.transformFrom(response);
            log.debug(CALLING_OUT_TO_STOCK_SERVICE_FOR, symbol);
            stockServiceClient.patch(symbol, historicalPriceRepresentation);
        } catch (IOException e) {
            log.error(String.format(UNABLE_TO_CONNECT_S, symbol), e);
        }
    }

    private Response sendRequest(HttpContent httpContent) throws IOException {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildPostRequest(
                new GenericUrl(RequestFacade.PRICE_SOURCE_URL), httpContent);
        request.getHeaders()
                .setAccept(ACCEPT_HEADERS)
                .setContentType(CONTENT_TYPE_HEADERS)
                .setAcceptEncoding(ACCEPT_ENCODING);
        return new Response(symbol, request.execute().parseAsString());
    }
}
