package view;

import controller.IController;

import java.util.Scanner;

/**
 * The PortfolioView class provides the user interface for the portfolio management application.
 * It implements the IView interface.
 */
public class PortfolioView implements IView {
    Scanner scanner = new Scanner(System.in);
    IController controller;

    /**
     * Constructs a PortfolioView.
     */
    public PortfolioView() {
        // not needed
    }

    public String promptForInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public IController setController(IController controller) {
       return this.controller = controller;
    }


    public void displayMessage(String message) {
        System.out.println(message);
    }

}
