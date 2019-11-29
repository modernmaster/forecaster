package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Stock already present")
public class DuplicateStockFoundException extends RuntimeException {
    public DuplicateStockFoundException(String message) {
        super(message);
    }
}

