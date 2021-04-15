package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job.Job;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job.JobService;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job.Status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JobService jobService;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MockMvc mvc;

    @Before
    public void setUp() throws IOException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetJobWithCompletedJobShouldReturn303AndLocation() throws Exception {
        String id = "1";
        String uri = String.format("/data-acquisition/job/%s", id);
        String entityClassifer = "test";
        String entityId = "100";
        String expectedLocation = String.format("http://localhost/%s/%s", entityClassifer, entityId);
        Job job = Job.builder()
                .id(id)
                .entityClassifer(entityClassifer)
                .entityId(entityId)
                .status(Status.COMPLETED)
                .build();
        when(jobService.getJob("1")).thenReturn(job);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String location = mvcResult.getResponse().getHeader("location");
        assertThat(status, equalTo(303));
        assertThat(location, equalTo(expectedLocation));
        verify(jobService).getJob("1");
    }

    @Test
    public void testGetJobWithNonCompletedJobShouldReturn303AndLocation() throws Exception {
        String id = "1";
        String uri = String.format("/data-acquisition/job/%s", id);
        String entityClassifer = "test";
        String entityId = "100";
        Job job = Job.builder()
                .id(id)
                .entityClassifer(entityClassifer)
                .entityId(entityId)
                .status(Status.PROCESSING)
                .build();
        when(jobService.getJob("1")).thenReturn(job);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String location = mvcResult.getResponse().getHeader("location");
        Job returnedJob = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Job.class);
        assertThat(status, equalTo(200));
        assertThat(returnedJob, equalTo(job));
        assertNull(location);
        verify(jobService).getJob("1");
    }
}
