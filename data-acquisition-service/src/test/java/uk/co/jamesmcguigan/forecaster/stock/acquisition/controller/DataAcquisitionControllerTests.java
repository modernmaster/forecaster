package uk.co.jamesmcguigan.forecaster.stock.acquisition.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataAcquisitionControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private JobService jobService;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Ignore
    public void testPostARequestForHistoricalPricesShouldReturn202() throws Exception {
        String id = "LON:OPM";
        String uri = String.format("/data-acquisition/historical-prices/%s", id);
        when(jobService.createJob(id, "historicalPrices")).thenReturn("1");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String location = mvcResult.getResponse().getHeader("location");
        assertThat(status, equalTo(202));
        assertThat(location, equalTo("http://localhost/data-acquisition/job/1"));
        verify(jobService).createJob(id, "historicalPrices");
    }

    @Test
    @Ignore
    public void testPostARequestForHistoricalPricesWithMissingIdShouldReturn404() throws Exception {
        String uri = "/data-acquisition/historical-prices/";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertThat(status, equalTo(404));
    }

    @Test
    @Ignore
    public void testPostARequestForTrendsShouldReturn202() throws Exception {
        String id = "LON:OPM";
        String uri = String.format("/data-acquisition/trends/%s", id);
        when(jobService.createJob(id, "trends")).thenReturn("1");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String location = mvcResult.getResponse().getHeader("location");
        assertThat(status, equalTo(202));
        assertThat(location, equalTo("http://localhost/data-acquisition/job/1"));
        verify(jobService).createJob(id, "trends");
    }

    @Test
    public void testPostARequestForTrendsWithMissingIdShouldReturn404() throws Exception {
        String uri = "/data-acquisition/trends/";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertThat(status, equalTo(404));
    }

}
