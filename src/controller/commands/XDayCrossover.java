package controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import model.IModel;
import model.IStock;
import model.Stock;
import view.IView;

import javax.swing.*;

/**
 * The XDayCrossover class represents a command to execute an X-day crossover strategy.
 * This class implements the Command interface.
 */
public class XDayCrossover implements Command {

  IView view;
  IModel model;
  Scanner scanner;

  /**
   * Constructs an XDayCrossover command with the specified view and model.
   *
   * @param view  the view interface for user interaction
   * @param model the data model interface
   */
  public XDayCrossover(IView view, IModel model) {
    this.view = view;
    this.model = model;
    this.scanner = new Scanner(System.in);
  }

  /**
   * Prompts the user for inputs required to execute the X-day crossover strategy,
   * retrieves the stock information from the model, and checks for a crossover.
   */
  public void takeInputs() {
    String ticker = view.promptForInput("Enter the ticker symbol:");
    String startDateString = view.promptForInput("Enter start date for the X-day crossover:");
    LocalDate startDate = dateCreater(startDateString);
    String endDateString = view.promptForInput("Enter end date for the X-day crossover:");
    LocalDate endDate = dateCreater(endDateString);

    IStock stock = model.getStockName(ticker);
    if (stock != null) {
      boolean isCrossover = stock.isXDayCrossover(startDate, endDate);
      if (isCrossover) {
        view.displayMessage(
                "A crossover occurred for "
                + ticker + " on the "
                + startDate.datesUntil(endDate).count() + "-day moving average.");
      } else {
        view.displayMessage(
                "No crossover occurred for "
                + ticker + " on the "
                + startDate.datesUntil(endDate).count() + "-day moving average.");
      }
    } else {
      view.displayMessage(
              "Stock not found.");
    }
  }

  private LocalDate dateCreater(String dateInput) {
    String[] dateArray = dateInput.split("-");

    int year = Integer.parseInt(dateArray[0]);
    int month = Integer.parseInt(dateArray[1]);
    int day = Integer.parseInt(dateArray[2]);
    return LocalDate.of(year, month, day);
  }

  /**
   * Executes the X-day crossover command.
   */
  @Override
  public void execute() {
    takeInputs();
  }
}
