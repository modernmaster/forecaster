package uk.co.jamesmcguigan.forecaster.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityException {
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Stock not found")
    public static class StockNotFoundException extends RuntimeException {
    }
    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Stock already present")
    public static class DuplicateStockFoundException extends RuntimeException {
    }

}
