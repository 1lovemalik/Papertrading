package controller.commands;

import model.IModel;
import model.Investor;
import model.PortfolioModel;
import view.IView;

/**
 * This class represents the command to add funds to a portfolio.
 * It implements the Command interface and defines the behavior for executing this specific command.
 */
public class AddFunds implements Command {
  private IView view;
  private Investor investor;

  /**
   * Constructor for AddFunds command.
   *
   * @param model    the data model interface
   * @param view     the view interface for user interaction
   * @param investor the investor object containing the portfolios
   */
  public AddFunds(IModel model, IView view, Investor investor) {
    this.view = view;
    this.investor = investor;
  }

  /**
   * Executes the add funds command by prompting the user for inputs and updating the selected portfolio.
   */
  @Override
  public void execute() {
    takeInputs();
    String namePortfolio = view.promptForInput("Enter the name of the portfolio:");
    PortfolioModel currentPortfolio = investor.getPortfolios().get(namePortfolio);
    if (currentPortfolio == null) {
      view.displayMessage("Portfolio not found.");
      return;
    }
    String amountStr = view.promptForInput("How much money would you like to add?");
    int amount = Integer.parseInt(amountStr);
    currentPortfolio.addFunds(amount);
    view.displayMessage("Funds added successfully.");
  }

  /**
   * Prompts the user to select a portfolio to add funds to.
   */
  @Override
  public void takeInputs() {
    view.displayMessage("What portfolio would you like to add funds to?");
  }
}
