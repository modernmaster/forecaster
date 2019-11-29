package uk.co.jamesmcguigan.forecaster.stock;

public class ServerUnavailableForStompUpdate extends RuntimeException {
    public ServerUnavailableForStompUpdate(String message) {
        super(message);
    }
}