package controller;

import java.util.HashMap;

import model.IInvestor;
import model.IModel;
import model.Investor;
import model.PortfolioModel;
import view.IView;
import view.PortfolioGUIView;

/**
 * The MainApplication class serves as the entry point for the portfolio management application.
 * It initializes the model, view, and controller, and starts the application.
 */
public class MainApplication {

    /**
     * The main method where the application starts.
     * It sets up the PortfolioModel, PortfolioView,
     * and PortfolioController, then runs the controller.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        IInvestor investor = new Investor();
        IModel model = new PortfolioModel(0, new HashMap<>());  // Assume Model is your implementation of IModel
        IView view = new PortfolioGUIView(model,investor );
        IController controller = new PortfolioController(view, model);

        view.setController(controller);
        controller.setView(view);

        controller.run();
    }
}
//TO-DO: modify to allow user to choose between text-based / GUI view


