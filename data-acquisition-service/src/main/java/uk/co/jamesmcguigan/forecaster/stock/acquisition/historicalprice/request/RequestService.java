package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.request;

import java.io.IOException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.DataAcquisitionServiceApplication;

@Service
@AllArgsConstructor
public class RequestService {

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

    public Response makeRequest(String symbol) throws IOException {
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
            HttpContent content = new JsonHttpContent(new JacksonFactory(), requestWrapper);
            String rawResponse = sendRequest(content);
            return new Response(symbol, rawResponse);
        } catch (IOException e) {
            DataAcquisitionServiceApplication.logger.error(String.format(UNABLE_TO_CONNECT_S, symbol), e);
            throw e;
        }
    }

    private String sendRequest(HttpContent httpContent) throws IOException {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildPostRequest(
                new GenericUrl(RequestService.PRICE_SOURCE_URL), httpContent);
        request.getHeaders()
                .setAccept(ACCEPT_HEADERS)
                .setContentType(CONTENT_TYPE_HEADERS)
                .setAcceptEncoding(ACCEPT_ENCODING);
        return request.execute().parseAsString();
    }

}
