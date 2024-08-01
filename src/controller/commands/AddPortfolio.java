package controller.commands;

import model.IInvestor;
import model.IModel;
import view.IView;

import java.util.Scanner;

/**
 * This class represents the command to add a new portfolio for an investor.
 * It implements the Command interface
 * and defines the behavior for executing this specific command.
 */
public class AddPortfolio implements Command {
    private final IView view;
    private final IInvestor investor;
    String name;

    /**
     * Constructor for AddPortfolio command.
     *
     * @param model    the data model interface
     * @param view     the view interface for user interaction
     * @param investor the investor interface containing the portfolios
     * @param scanner  the scanner for reading user input
     */
    public AddPortfolio(IModel model, IView view, IInvestor investor, Scanner scanner) {
        this.view = view;
        this.investor = investor;
    }

    @Override
    public void execute() {
        takeInputs();
        int beforeAdding = this.investor.getPortfolios().size();
        this.investor.addPortfolio(name);
        int afterAdding = this.investor.getPortfolios().size();

        if (afterAdding == beforeAdding + 1) {
            view.displayMessage( "This portfolio has now been created.");
        } else {
            String answer = view.promptForInput("This process has failed." +
                    " Would you like to try again? Enter 'yes or no");
            if (answer.toLowerCase() == "yes") {
                execute();
            }
        }
    }

    @Override
    public void takeInputs() {
        String namePorfolio = view.promptForInput("What would you like this New Portfolio to be called?");
        if (this.investor.getPortfolios().containsKey(namePorfolio)) {
            namePorfolio = view.promptForInput("This portfolio name already exists. Please pick another name");
        } else if (namePorfolio == null) {
            view.displayMessage(
                    "The name of a portfolio must contain at least one character!");
        }
        else {
            view.displayMessage(
                    "Great! The " + namePorfolio + "Portfolio will now be created!");
        }

        this.name = namePorfolio;
    }

}


