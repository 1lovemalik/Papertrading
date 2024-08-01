package model;

import java.time.LocalDate;

/**
 * The IModel interface defines the contract for model classes in the application.
 * It includes methods to manage stocks and the portfolio balance.
 */
public interface IModel {

  IStock getStockName(String tickerName);

  IStock produceStock(String tickerName, LocalDate date);

  void removeStock(String tickerName, double quantityLess);

  void setBalance(double balance);

  double getBalance();

  double calculateTotalValue();

  double calculateTotalValueDate(LocalDate date);


  /*
  take inputs for intended weight of each stock

  take in tickerName, weight, and date

  CHECKS
  non-negative numbers
  all weights need to add to 1 or 100
  map String name -> weight
  map.size must == arrayList portfolio in Investor size

  check that every stock input is within the investor portfolio. <- helper method

  every stock must occur once in input
  test all exceptions.

   */
}
