package uk.co.jamesmcguigan.forecaster.stock;

public class StockAlert {
  private Stock stock;
  private String message;

  public StockAlert(Stock stock, String message) {
    this.stock = stock;
    this.message = message;
  }

  public Stock getStock() {
    return stock;
  }

  public String getMessage() {
    return message;
  }
}
