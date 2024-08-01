package controller.commands;

import java.time.LocalDate;


import model.IInvestor;
import model.IModel;
import model.IStock;
import view.IView;

/**
 * The BuyCommand class represents a command to buy stocks.
 * This class implements the Command interface and defines the behavior for executing this specific command.
 */
public class BuyCommand implements Command {
    private IStock stock;
    private IModel model;
    private IView view;
    private int quantity;
    private IInvestor investor;
    private String namePortfolio;

    /**
     * Constructs a BuyCommand with the specified portfolio model and view.
     *
     * @param portfolioModel the model of the portfolio
     * @param portfolioView  the view of the portfolio
     * @param investor       the investor interface containing the portfolios
     */
    public BuyCommand(IModel portfolioModel, IView portfolioView, IInvestor investor) {
        this.view = portfolioView;
        this.model = portfolioModel;
        this.investor = investor;
    }

    /**
     * Prompts the user for inputs and updates the stock and quantity fields.
     */
    @Override
    public void takeInputs() {
        namePortfolio = view.promptForInput("What portfolio would you like to add this stock to?");
        if (investor.getPortfolios().get(namePortfolio) == null) {
            view.displayMessage("This portfolio does not exist.");
            view.displayMessage("Try again.");
            takeInputs();
        } else {
            String tickerInput = view.promptForInput("Enter tickerSymbol").toLowerCase();
            String inputQuantity = view.promptForInput("Enter quantity").toLowerCase();
            this.quantity = Integer.parseInt(inputQuantity);
            String dateInput = view.promptForInput("Enter date bought. Enter in YYYY-MM-DD format");
            this.stock = model.produceStock(tickerInput.toUpperCase(), dateCreater(dateInput));
            view.displayMessage( "This stock has been purchased!");
        }
    }

    /**
     * Executes the buy command by taking inputs, checking funds, and updating the portfolio.
     */
    @Override
    public void execute() {
        takeInputs();
        double moneyRequired = stock.getPriceOfStock() * quantity;
        if (model.getBalance() < moneyRequired) {
            view.displayMessage(
                    "You do not have the funds to do this! Add more funds.");
        } else {
            double newQuantity = stock.getQuantity() + quantity;
            stock.setQuantity(newQuantity);
            view.displayMessage(
                    "You have successfully bought " + quantity + " shares of " + stock.getTickerName());
        }
        model.setBalance(model.getBalance() - moneyRequired);
        investor.addStock(namePortfolio, stock);
    }

    /**
     * Converts a date string in the format YYYY-MM-DD to a LocalDate object.
     *
     * @param dateInput the date string in the format YYYY-MM-DD
     * @return the corresponding LocalDate object
     */
    private LocalDate dateCreater(String dateInput) {
        String[] dateArray = dateInput.split("-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        return LocalDate.of(year, month, day);
    }
}
