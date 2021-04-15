package uk.co.jamesmcguigan.forecaster.stock.trends;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TrendsServiceTests {
    private ObjectMapper objectMapper;
//    private TrendsService trendsService;
//    @Mock
//    private StockRepository stockRepository;
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//    private String trendsJson;
//    private String stockJson;
//
//    @Before
//    public void setUp() throws IOException {
//        objectMapper = new ObjectMapper();
//        trendsService = new TrendsService(stockRepository);
//        trendsJson = Resources.toString(Resources.getResource("uk.co.jamesmcguigan.forecaster.service.trends.json"), Charset.defaultCharset());
//        stockJson = Resources.toString(Resources.getResource("stock.json"), Charset.defaultCharset());
//    }

//    @Test
//    @Ignore
//    public void testUpdateWillGetStockUpdateAndThenPersist() throws IOException {
//        String symbol = "lon:sxx";
//        Map<String, Trend> uk.co.jamesmcguigan.forecaster.service.trends = objectMapper.readValue(trendsJson, Map.class);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        when(stockRepository.findBySymbol(symbol)).thenReturn(stock);
//        when(stockRepository.save(stock)).thenReturn(stock);
//        trendsService.updateTrend(symbol, Maps.newHashMap(uk.co.jamesmcguigan.forecaster.service.trends));
//        verify(stockRepository).save(stock);
//        verify(stockRepository).findBySymbol(symbol);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    @Ignore
//    public void testUpdateWillGetNullStockUpdateAndThenThrowException() throws IOException {
//        String symbol = "lon:sxx";
//        Map<String, Trend> uk.co.jamesmcguigan.forecaster.service.trends = objectMapper.readValue(trendsJson, Map.class);
//        Stock stock = objectMapper.readValue(stockJson, Stock.class);
//        when(stockRepository.findBySymbol(symbol)).thenReturn(null);
//        trendsService.updateTrend(symbol, Maps.newHashMap(uk.co.jamesmcguigan.forecaster.service.trends));
//        verify(stockRepository).findBySymbol(symbol);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testUpdateWillThrowExceptionIfSymbolIsEmpty() throws IOException {
//        Map<String, Trend> uk.co.jamesmcguigan.forecaster.service.trends = objectMapper.readValue(trendsJson, Map.class);
//        trendsService.updateTrend(Strings.EMPTY, Maps.newHashMap(uk.co.jamesmcguigan.forecaster.service.trends));
//    }
//
//    @Test
//    public void testCreateHistoricalPriceEventWillCreateNewEvent() {
//        String symbol = "lon:sxx";
//        trendsService.createTrendEvent(symbol);
//        //TODO verify call to rest service
////        verify(createTrendEvent).send();
//    }

}
