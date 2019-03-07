package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.exparity.hamcrest.date.DateMatchers;
import org.junit.Before;
import org.junit.Test;
import uk.co.jamesmcguigan.forecaster.dataacquisition.DataAcquisitionResponse;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class PriceTransformerTest {

    private PriceTransformer priceTransformer;

    @Before
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        priceTransformer = new PriceTransformer(objectMapper);
    }

    @Test
    public void testTransformCorrectResponseIntoHistoricalPrice() throws IOException {
        URL resource = Resources.getResource(this.getClass(), "sxx-price-response.json");
        String contents = Resources.toString(resource, Charset.defaultCharset());
        DataAcquisitionResponse dataAcquisitionResponse = new DataAcquisitionResponse("lon:sxx", contents);
        List<Price> prices = priceTransformer.transformFrom(dataAcquisitionResponse);
        assertNotNull(prices);
        Price price = prices.get(251);
        assertThat(price.getClosing(), equalTo(20.44));
        assertThat(price.getDaysHigh(), equalTo(20.54));
        assertThat(price.getDaysLow(), equalTo(20.19));
        assertThat(price.getOpening(), equalTo(20.2));
//        assertThat(price.getDate(), DateMatchers.sameDay(LocalDate.of(2019, 02, 22).));
    }

}
