package uk.co.jamesmcguigan.forecaster.stock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/stock",  method = RequestMethod.GET,headers = "Accept=application/json",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> index() throws JsonProcessingException {
//get list of ids here
        List<Stock> stockList = stockService.getTop20HighestGrowthStocks();
        return new ResponseEntity<>(stockList, HttpStatus.OK);
    }

    @RequestMapping(value = "/stock/{id}", method = RequestMethod.GET)
    public String byId(@PathVariable("id") String id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Stock> stockList = stockService.getTop20HighestGrowthStocks();
        return objectMapper.writeValueAsString(stockList);
    }

}
