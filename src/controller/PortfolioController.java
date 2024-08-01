package controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.AddFunds;
import controller.commands.AddPortfolio;
import controller.commands.BuyCommand;
import controller.commands.Command;
import controller.commands.ComputeCommand;
import controller.commands.CompositionStocksCommand;
import controller.commands.NumberOfPortfolios;
import controller.commands.PerformanceOverTimeCommand;
import controller.commands.RebalanceCommand;
import controller.commands.SellCommand;
import model.IModel;
import model.Investor;
import view.IView;

/**
 * This class represents Controller that handles the interactions between the model and the view.
 */
public class PortfolioController implements IController {
    private IView view;
    LocalDate date;

    Scanner scanner;

    IModel model;
    // "CommandName" -> Command
    public Map<String, Command> commands;

    Investor investor;

    /**
     * Public constructor for the controller class. Takes in delegates from View and controller.
     *
     * @param view  is the delegate for the Portfolio view class.
     * @param model is the delegate for the Portfolio model class
     */
    public PortfolioController(IView view, IModel model) {
        this.view = view;
        this.model = model;
        this.commands = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.investor = new Investor();
        initializeCommands();
    }

    /**
     * This is the method to populate the hashmap with the current commands.
     */
    private void initializeCommands() {
        commands.put("buy", new BuyCommand(model, view, investor));
        commands.put("sell", new SellCommand(model, view, investor, scanner));
        commands.put("compute", new ComputeCommand(model, view, investor));
        commands.put("add portfolio", new AddPortfolio(model, view, investor, scanner));
        commands.put("add funds", new AddFunds(model, view, investor));
        commands.put("get composition", new CompositionStocksCommand(model, view, investor));
        commands.put("number portfolios", new NumberOfPortfolios(model, view, investor, scanner));
        commands.put("performance", new PerformanceOverTimeCommand(model, view, investor, scanner));
        commands.put("rebalance", new RebalanceCommand(model, view, investor, scanner));
    }


    /**
     * Startup screen as when investors are first starting they have no portfolios.
     */
    private void startScreen() {
        if (investor.numPortFolios() == 0) {
            view.promptForInput("Lets start by creating your first portfolio!");
            view.promptForInput("To do this, Input 'Add portfolio");
        } else {
            view.promptForInput("Command List: 'Add portfolio', 'Get Composition', ''Buy', 'Sell',"
                    +
                    "'Number portfolios', 'Compute' ,'Rebalance', 'Performance', or 'Quit'");
        }
    }



    /**
     * Helper method for setUpButtonListeners. Takes in the names of the respective commands and executes.
     * @param commandName
     */
    public void executeCommand(String commandName) {
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute();
        } else {
            view.displayMessage("Invalid command: " + commandName);
        }
    }

    @Override
    public IView setView(IView view) {
        return this.view = view;
    }


    /**
     * Starts the controller, processing user input in a loop until the user decides to quit.
     */
    public void run() {
        boolean running = true;
        while (running) {

            startScreen();
            String commandInput = scanner.nextLine().toLowerCase();
            Command command = commands.get(commandInput.toLowerCase());
            if (command != null) {
                command.execute();
            } else if (commandInput.equalsIgnoreCase("quit")) {
                running = false;
            } else {
                view.displayMessage("Invalid choice: try again.");
            }
        }
    }
}


