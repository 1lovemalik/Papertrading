package controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import model.IModel;
import model.IStock;
import model.Stock;
import view.IView;

import javax.swing.*;

/**
 * The XDayMoveCommand class represents a command to execute an X-day move average strategy.
 * This class implements the Command interface.
 */
public class XDayMoveCommand implements Command {
    IView view;
    IModel model;
    Scanner scanner;

    /**
     * Constructs an XDayMoveCommand with the specified view and model.
     *
     * @param view  the view interface for user interaction
     * @param model the data model interface
     */
    public XDayMoveCommand(IView view, IModel model) {
        this.view = view;
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user for inputs required to execute the X-day moving average strategy,
     * retrieves the stock information from the model, and calculates the moving average.
     */
    public void takeInputs() {
        String ticker = view.promptForInput("Enter the ticker symbol:");
        if (ticker == null || ticker.length() < 3) {
            view.displayMessage(
                    "A ticker name must be at least 2 letters!");
            execute();
        } else
        ticker.toUpperCase();
        String startDateString = view.promptForInput("Enter start date for the X-day moving average:");
        LocalDate startDate = dateCreater(startDateString);
        String endDateString = view.promptForInput("Enter end date for the X-day moving average:");
        LocalDate endDate = dateCreater(endDateString);


        IStock stock = model.getStockName(ticker);
        if (stock != null) {
            double movingAverage = stock.calculateDayMovingAverage(startDate, endDate);
            view.displayMessage(
                    "The " + startDate.datesUntil(endDate).count() + "-day moving average for "
                            + ticker + " is: " + movingAverage);
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
     * Executes the X-day moving average command.
     */
    @Override
    public void execute() {
        takeInputs();
    }
}
