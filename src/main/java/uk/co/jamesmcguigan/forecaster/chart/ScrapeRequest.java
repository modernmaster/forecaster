package uk.co.jamesmcguigan.forecaster.chart;

import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.Application;

import java.io.IOException;
import java.util.concurrent.Callable;

@Service
@NoArgsConstructor
class ScrapeRequest {

    Callable<ScrapeResponse> createRequest(String uri, String selector) {

        Callable<ScrapeResponse> callable = () -> {
            try {
                Document document = Jsoup.connect(uri).get();
                Elements test = document.select(selector);
                if (!test.isEmpty()) {
                    return new ScrapeResponse(uri, document.outerHtml());
                }
                throw new NoSuchFieldException(String.format("Cannot find at %s the following selector %s", uri, selector));
            } catch (IOException e) {
                Application.logger.error(String.format("Unable to connect:%s", uri), e);
                throw e;
            }
        };
        return callable;
    }
}
