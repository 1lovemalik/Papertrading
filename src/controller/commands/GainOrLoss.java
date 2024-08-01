package controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import model.IModel;
import model.IStock;
import model.Stock;
import view.IView;

import javax.swing.*;

/**
 * The GainOrLoss class represents a command to calculate the gain or loss.
 * This class implements the Command interface.
 */


public class GainOrLoss implements Command {

    IView view;
    IModel model;
    Scanner scanner;

    /**
     * Constructor for GainOrLoss command.
     *
     * @param view  the view interface for user interaction
     * @param model the data model interface
     */
    public GainOrLoss(IView view, IModel model) {
        this.view = view;
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user for inputs required to calculate the gain or loss,
     * retrieves the stock information from the model, and calculates the gain or loss.
     */
    public void takeInputs() {
        String ticker = view.promptForInput("Enter the ticker symbol:");
        ticker.toUpperCase();
        String startDateString = view.promptForInput("Enter start date to calculate gains over days");
        LocalDate startDate = dateCreater(startDateString);
        String endDateString = view.promptForInput("Enter end date to calculate gains over days");
        LocalDate endDate = dateCreater(endDateString);

        IStock stock = model.getStockName(ticker);
        if (stock != null) {
            double gainOrLoss = stock.calculateGainOverDays(startDate, endDate);
            view.displayMessage(
                    "The gain/loss over " + startDate.compareTo(endDate)
                    + " days is: " + gainOrLoss
                    + "$/dollars");
        } else {
            view.displayMessage("Stock not found.");
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
     * Executes the gain or loss calculation command.
     */
    @Override
    public void execute() {
        takeInputs();
    }
}
