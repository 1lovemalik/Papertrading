package controller.commands;

import model.IModel;
import model.Investor;
import view.IView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Command for performance over time.
 */
public class PerformanceOverTimeCommand implements Command {
  IModel model;
  IView view;
  Investor investor;
  Scanner scanner;
  LocalDate startDate;
  LocalDate endDate;
  StringBuilder sb;
  int interval;
  String portfolioName;
  Map<LocalDate, Double> intervalValues;

  /**
   * Constructor for PerformanceOverTimeCommand.
   */
  public PerformanceOverTimeCommand(IModel model, IView view, Investor investor, Scanner scanner) {
    this.model = model;
    this.view = view;
    this.investor = investor;
    this.scanner = scanner;
    this.sb = new StringBuilder();
    intervalValues = new HashMap<>();
  }

  /**
   * Execute method.
   */
  @Override
  public void execute() {
    takeInputs();
    computeIntervals();
    this.intervalValues =
            investor.findInternalValues(portfolioName, this.startDate, this.endDate, this.interval);
    findMostValuable();
    applyWeight();
    printPerformance();
  }

  /**
   * take inputs method.
   */
  @Override
  public void takeInputs() {
    view.promptForInput("What portfolio would you like to see the performance over time of?");
    String portfolioName = scanner.nextLine();
    if (!investor.getPortfolios().containsKey(portfolioName)) {
      view.promptForInput("this portfolio does not exist!");
    } else {
      this.portfolioName = portfolioName;
      view.promptForInput("Lets find the performance over time of this portfolio.");
      view.promptForInput("What would you like the start date of this analysis to be?");
      try {
        this.startDate = dateCreater(scanner.nextLine());
      } catch (IllegalArgumentException e) {
        view.promptForInput(e.getMessage());
        view.promptForInput("We will now restart this command.");
        execute();
      }
      view.promptForInput("Enter the end date.");
      try {
        this.endDate = dateCreater(scanner.nextLine());
      } catch (IllegalArgumentException e) {
        view.promptForInput(e.getMessage());
        view.promptForInput("We will now restart this command.");
        execute();
      }
      if (enforceDate(startDate, endDate)) {
        view.promptForInput("Your end date cannot be before your beginning date.");
        view.promptForInput("We will now restart this command.");
        execute();
      }
    }
  }


  private void computeIntervals() {
    long dayDifference = DAYS.between(this.startDate, this.endDate);

    if (dayDifference >= 1 && dayDifference <= 24) {
      this.interval = 1;
    } else if (dayDifference < 30) {
      this.interval = 5;
    } else if (dayDifference < 60) {
      this.interval = 10;
    } else if (dayDifference < 120) {
      this.interval = 15;
    } else if (dayDifference < 240) {
      this.interval = 20;
    } else if (dayDifference < 365) {
      this.interval = 30;
    } else if (dayDifference < 1825) {
      this.interval = 90;
    } else {
      this.interval = 365;
    }
  }


  private LocalDate dateCreater(String dateInput) {
    String[] dateArray = dateInput.split("-");
    if (dateArray.length != 3) {
      throw new IllegalArgumentException("Invalid date format");
    } else {
      int year = Integer.parseInt(dateArray[0]);
      int month = Integer.parseInt(dateArray[1]);
      int day = Integer.parseInt(dateArray[2]);
      return LocalDate.of(year, month, day);
    }
  }


  private void applyWeight() {
    double max = findMostValuable();
    if (max > 0) {
      for (LocalDate date : this.intervalValues.keySet()) {
        Double currentValue = this.intervalValues.get(date);
        double relativeScale = Math.floor((currentValue * 50) / max);
        this.intervalValues.put(date, relativeScale);
      }
    }
  }


  private void printPerformance() {
    List<LocalDate> dates = new ArrayList<>(this.intervalValues.keySet());
    Collections.sort(dates);
    List<Double> values = new ArrayList<>(this.intervalValues.values());
    sb.append("Performance of ")
            .append(portfolioName)
            .append(" From ").append(this.startDate.toString())
            .append(" to ").append(this.endDate.toString())
            .append("\n");

    for (int i = 0; i < dates.size(); i++) {
      double numA = values.get(i);
      int numberAsterisks = (int) numA;
      sb.append(dates.get(i).toString())
              .append(":")
              .append("*".repeat(numberAsterisks))
              .append("\n"); // Append newline character after each entry
    }

    sb.append("Scale: * = ").append(findMostValuable() / 50).append("\n");

    // Print the accumulated StringBuilder content
    System.out.println(sb.toString());
  }


  private double findMostValuable() {
    this.intervalValues = investor.findInternalValues(portfolioName,
            startDate, endDate, interval);
    // Ensure interval values are populated

    double mostValuable = 0;

    for (Double entry : intervalValues.values()) {
      if (entry > mostValuable) {
        mostValuable = entry;
      }
    }
    return mostValuable;
  }


  private boolean enforceDate(LocalDate startDate, LocalDate endDate) {
    return endDate.isBefore(startDate);
  }

}

