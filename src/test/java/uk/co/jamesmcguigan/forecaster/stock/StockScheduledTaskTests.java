package uk.co.jamesmcguigan.forecaster.stock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockScheduledTaskTests {

    @Autowired
    private StockLookupService stockLookupService;
    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testShouldCallStockServiceAndPersistIntoRepository() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/stock").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(notNullValue()));
    }
}
