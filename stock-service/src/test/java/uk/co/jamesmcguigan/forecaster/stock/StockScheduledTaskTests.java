package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class StockScheduledTaskTests {

    @Autowired
    private StockLookupService stockLookupService;
    @Autowired
    private StockRepository stockRepository;

//    @Test
    public void testShouldCallStockServiceAndPersistIntoRepository() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/stock").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(notNullValue()));
    }
}
