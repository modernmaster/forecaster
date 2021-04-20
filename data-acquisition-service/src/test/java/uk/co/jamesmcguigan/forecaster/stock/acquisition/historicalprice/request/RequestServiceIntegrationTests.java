package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.request;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestServiceIntegrationTests {

    @Autowired
    private RequestService requestService;

    @Test
    public void testCreateRequestObjectAndReturnResponse() throws IOException {
        Response response = requestService.makeRequest("lon:sxx");
        assertNotNull(response);
    }
}
