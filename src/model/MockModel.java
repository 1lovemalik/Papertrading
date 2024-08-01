package model;

import java.time.LocalDate;

/**
 * The MockModel class is a mock implementation of the IModel interface
 * for testing purposes. It logs method calls and delegates the actual execution
 * to a real PortfolioModel.
 */
public class MockModel implements IModel {
  StringBuilder log;
  PortfolioModel actualModel;

  public MockModel(PortfolioModel actualModel, StringBuilder log) {
    this.actualModel = actualModel;
    this.log = log;
  }

  @Override
  public IStock getStockName(String tickerName) {
    log.append("getStockName").append(tickerName);
    return actualModel.getStockName(tickerName);
  }

  public IStock produceStock(String tickerName, LocalDate date) {
    log.append("ProducedStock").append(tickerName);
    return actualModel.produceStock(tickerName, date);
  }

  @Override
  public void removeStock(String tickerName, double quantityLess) {
    log.append("removeStock").append(tickerName).append(quantityLess);
    actualModel.removeStock(tickerName, quantityLess);
  }

  public void setBalance(double balance) {
    log.append("setBalance").append(balance);
    actualModel.setBalance(balance);
  }

  public double getBalance() {
    log.append("getBalance").append(actualModel.getBalance());
    return actualModel.getBalance();
  }

  @Override
  public double calculateTotalValue() {
    log.append("calculateTotalValue").append(" " + actualModel.calculateTotalValue());
    return actualModel.calculateTotalValue();
  }

  @Override
  public double calculateTotalValueDate(LocalDate date) {
    return 0;
  }

}
