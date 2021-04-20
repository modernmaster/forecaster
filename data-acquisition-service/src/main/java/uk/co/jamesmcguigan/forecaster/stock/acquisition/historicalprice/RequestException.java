package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice;

public class RequestException {
    public static class BadRequestException extends RuntimeException {
    }
    public static class ServerFaultException extends RuntimeException {
    }
}
