package model;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

/**
 * The IStock interface defines the contract for stock classes in the application.
 * It includes methods to get stock details and perform various calculations.
 */
public interface IStock {
  String getTickerName();

  double getPriceOfStock();

  double getQuantity();

  void setQuantity(double quantity);

  double calculateGainOverDays(LocalDate startDate, LocalDate endDate);

  double calculateDayMovingAverage(LocalDate startDate, LocalDate endDate);

  boolean isXDayCrossover(LocalDate startDate, LocalDate endDate);

  LocalDate getDate();

  double getValueStock(LocalDate date);

  double getPriceOfStock(LocalDate date);

  void setDate(LocalDate date);
}
