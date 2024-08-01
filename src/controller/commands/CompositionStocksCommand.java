package controller.commands;

import model.IModel;
import model.Investor;
import view.IView;

import java.time.LocalDate;

/**
 * This class represents the command to get the composition of stocks in
 * a portfolio for a specific date.
 * It implements the Command interface and defines the behavior for executing this specific command.
 */
public class CompositionStocksCommand implements Command {
  private Investor investor;
  private IModel model;
  private IView view;
  private String nameCurrentPortfolio;
  private LocalDate date;

  /**
   * Constructor for CompositionStocksCommand.
   *
   * @param model    the data model interface
   * @param view     the view interface for user interaction
   * @param investor the investor object containing the portfolios
   */
  public CompositionStocksCommand(IModel model, IView view, Investor investor) {
    this.investor = investor;
    this.model = model;
    this.view = view;
  }

  /**
   * Executes the command to get the composition of stocks for a specific portfolio and date.
   */
  @Override
  public void execute() {
    takeInputs();
    investor.getCompositionOfStocks(nameCurrentPortfolio, date);
  }

  /**
   * Prompts the user to select a portfolio and enter a date for which to see the composition of stocks.
   */
  @Override
  public void takeInputs() {
    nameCurrentPortfolio = view.promptForInput("What portfolio would you like to see the composition of?");
    if (investor.getPortfolios().containsKey(nameCurrentPortfolio)) {
      String dateInput = view.promptForInput("What date would you like to see the composition of "
              + nameCurrentPortfolio + "? Input this number in this format: YYYY-MM-DD");
      this.date = dateCreater(dateInput);
    } else {
      view.displayMessage("This portfolio does not exist.");
    }
  }

  /**
   * Converts a date string in the format YYYY-MM-DD to a LocalDate object.
   *
   * @param dateInput the date string in the format YYYY-MM-DD
   * @return the corresponding LocalDate object
   */
  public LocalDate dateCreater(String dateInput) {
    String[] dateArray = dateInput.split("-");
    int year = Integer.parseInt(dateArray[0]);
    int month = Integer.parseInt(dateArray[1]);
    int day = Integer.parseInt(dateArray[2]);
    return LocalDate.of(year, month, day);
  }
}
