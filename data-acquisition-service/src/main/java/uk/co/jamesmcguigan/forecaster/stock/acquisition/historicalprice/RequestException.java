package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

public class RequestException {
    public static class BadRequestException extends RuntimeException {
    }
    public static class ServerFaultException extends RuntimeException {
    }
}
