package uk.co.jamesmcguigan.forecaster.stock;

public class BadRequestForStompUpdate extends RuntimeException {
    public BadRequestForStompUpdate(String message) {
        super(message);
    }
}