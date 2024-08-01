package controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import model.IModel;
import model.IStock;
import model.Investor;
import model.Stock;
import view.IView;

import javax.swing.*;

/**
 * The SellCommand class represents a command to sell stocks.
 * This class implements the Command interface.
 */
public class SellCommand implements Command {

    Scanner scanner;

    IStock stock;
    IModel model;
    IView view;
    double quantity;
    Investor investor;
    String namePortfolio;

    /**
     * Constructs a SellCommand with the specified portfolio model and view.
     *
     * @param portfolioModel the model of the portfolio
     * @param portfolioView  the view of the portfolio
     * @param scanner        scanner.
     */
    public SellCommand(IModel portfolioModel,
                       IView portfolioView, Investor investor,
                       Scanner scanner) {
        this.view = portfolioView;
        this.model = portfolioModel;
        this.scanner = new Scanner(System.in);
        this.investor = investor;
    }

    /**
     * take inputs method.
     */
    public void takeInputs() {
        this.namePortfolio = view.promptForInput("What portfolio would you like to sell a stock in?");


        if (!investor.getPortfolios().containsKey(namePortfolio)) {
            view.displayMessage(
                    "This portfolio does not exist.");
            execute();
        }

        String tickerInput = view.promptForInput("Enter tickerSymbol");
        enforceTickerName(tickerInput);
        String inputQuantity = view.promptForInput("Enter quantity");
        enforceQuantity(Double.parseDouble(inputQuantity));
        String dateInput = view.promptForInput("Enter date sold. Enter in YYYY-MM-DD format");

        this.stock = model.produceStock(tickerInput, dateCreater(dateInput));
        if (dateCreater(dateInput).isBefore(this.stock.getDate())) {
            String answer = view.promptForInput("This stock cannot be sold before it was bought! " +
                    "Would you like to restart this process?");
            if (answer.toLowerCase() == "yes" || answer.toLowerCase() == "y") {
                execute();
            }
        }
        this.quantity = Double.parseDouble(inputQuantity);
    }

    /**
     * Executes the sell command by taking inputs, checking stock availability,
     * and updating the portfolio.
     */
    @Override
    public void execute() {
        takeInputs();
        if (this.quantity > stock.getQuantity()) {
            throw new IllegalArgumentException("You cannot sell more stock than you currently own.");
        } else {
            double newQuantity = stock.getQuantity() - this.quantity;
            stock.setQuantity(newQuantity);
            view.displayMessage(
                    "You have successfully sold "
                            + this.quantity + "shares of " + stock.getTickerName());
        }
        investor.removeStock(namePortfolio, stock, quantity);
    }

    private LocalDate dateCreater(String dateInput) {
        String[] dateArray = dateInput.split("-");

        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        return LocalDate.of(year, month, day);
    }

    private void enforceTickerName(String tickerNameInput) {
        if (tickerNameInput.length() < 4 || tickerNameInput.length() > 5) {
            view.displayMessage(
                    "The ticker name must 4 or 5 characters.");
            execute();
        }

    }

    private void enforceQuantity(double input) {
        if (input < 0) {
            view.displayMessage(
                    "You cannot sell a negative number of stocks! ");
            execute();
        }
    }


}

