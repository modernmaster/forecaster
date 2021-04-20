package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityException {
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Job not found")
    public static class JobNotFoundException extends RuntimeException {
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Job not created")
    public static class JobNotCreatedException extends RuntimeException {
    }
}
