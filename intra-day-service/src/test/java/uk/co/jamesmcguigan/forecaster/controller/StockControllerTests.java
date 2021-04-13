package uk.co.jamesmcguigan.forecaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StockControllerTests {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private String pricesJson;
    private MockMvc mvc;
    private String stockJson;

    @Before
    public void setUp() throws IOException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        pricesJson = Resources.toString(Resources.getResource("prices.json"), Charset.defaultCharset());
        stockJson = Resources.toString(Resources.getResource("stock.json"), Charset.defaultCharset());
    }

    @Test
    @Ignore
    public void testStockGetWillReturnNotSupported() throws Exception {
        String id = "lon:sxx";
        String uri = String.format("/api/stocks/%s", id);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(405, status);
    }
//
//    @Test
//    @Ignore
//    public void testUpdateStockWithCorrectIdWillReturn200AndReturnId() throws Exception {
//        String id = "lon:sxx";
//        String uri = String.format("/stock/%s", id);
////        Stock stock = objectMapper.readValue(stockJson, Stock.class);
////        when(stockLookupService.getStockBySymbol(id)).thenReturn(stock);
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(stockJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
////        verify(stockLookupService).getStockBySymbol(id);
////        verify(stockLookupService).updateStock(stock);
//    }
//
//    @Test
//    @Ignore
//    public void testUpdateStockWithInorrectStockWillReturn400() throws Exception {
//        String id = "lon:sxx";
//        String uri = String.format("/stock/%s", id);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
////        when(stockLookupService.getStockBySymbol(id)).thenReturn(stock);
//        doThrow(new IllegalArgumentException())
//                .when(stockLookupService).updateStock(stock);
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(stockJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(400, status);
//        //      verify(stockLookupService).getStockBySymbol(id);
//        verify(stockLookupService).updateStock(stock);
//    }
//
//    @Test
//    @Ignore
//    public void testUpdateStockWithIncorrectStockWillReturnTeapot() throws Exception {
//        String id = "lon:sxx";
//        String uri = String.format("/stock/%s", id);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        //    when(stockLookupService.getStockBySymbol(id)).thenReturn(stock);
//        doThrow(new UnsupportedOperationException())
//                .when(stockLookupService).updateStock(stock);
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(stockJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(418, status);
//        //  verify(stockLookupService).getStockBySymbol(id);
//        verify(stockLookupService).updateStock(stock);
//    }
//
//    @Test
//    @Ignore
//    public void testUpdateStockWithNullStockWillReturn404() throws Exception {
//        String id = "lon:sxx";
//        String uri = String.format("/stock/%s", id);
//        Stock stock = null;
//        // when(stockLookupService.getStockBySymbol(id)).thenReturn(stock);
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(stockJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(404, status);
//        //verify(stockLookupService).getStockBySymbol(id);
//    }
//
//    @Test
//    @Ignore
//    public void testCreateStockWillReturn201AndLocation() throws Exception {
//        String id = "LON:OPM";
//        String uri = String.format("/stock/%s", id);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        //when(stockLookupService.getStockBySymbol(id)).thenReturn(null);
//        when(stockLookupService.createStock(id)).thenReturn(stock);
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(stockJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
//        assertEquals(201, status);
//        assertEquals("http://localhost/stock/LON:OPM/", location);
//        //verify(stockLookupService).getStockBySymbol(id);
//        verify(stockLookupService).createStock(id);
//    }
//
//    @Test
//    @Ignore
//    public void testCreateStockForDuplicateWillReturn409() throws Exception {
//        String id = "LON:OPM";
//        String uri = String.format("/stock/%s", id);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        //when(stockLookupService.getStockBySymbol(id)).thenReturn(stock);
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(stockJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
//        assertEquals(409, status);
//        assertNull(location);
//        //verify(stockLookupService).getStockBySymbol(id);
//    }
//
//    @Test
//    @Ignore
//    public void testPatchForHistoricalPricesWillReturn204() throws Exception {
//        String id = "LON:OPM";
//        String uri = String.format("/stock/%s?feature=historicalprices", id);
//
//        List<Price> stock = objectMapper.readValue(pricesJson, new TypeReference<List<Price>>() {
//        });
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(pricesJson)).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        assertThat(status, equalTo(HttpStatus.NO_CONTENT));
//    }
//
//    @Test
//    @Ignore
//    public void testPatchForTrendsWillReturn204() throws Exception {
//        String id = "LON:OPM";
//        String uri = String.format("/stock/%s?feature=trends", id);
//
//        List<Price> stock = objectMapper.readValue(pricesJson, new TypeReference<List<Price>>() {
//        });
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(pricesJson)).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        assertThat(status, equalTo(HttpStatus.NO_CONTENT));
//    }

}
