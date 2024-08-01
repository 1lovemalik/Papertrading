package controller.commands;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.IInvestor;
import model.IModel;
import view.IView;

/**
 * The ComputeCommand class represents a command to perform various computations
 * on stock data such as moving averages, crossovers, or gain/loss calculations.
 * This class implements the Command interface.
 */
public class ComputeCommand implements Command {

  IModel model;
  IView view;
  IInvestor investor;

  JFrame frame;
  JPanel panel;
  Map<String, Command> computeCommands;

  LocalDate date;
  String tickerName;

  /**
   * Constructs a ComputeCommand with the specified model and view.
   *
   * @param model    the portfolio model
   * @param view     the portfolio view
   * @param investor the investor model
   */
  public ComputeCommand(IModel model, IView view, IInvestor investor) {
    this.model = model;
    this.view = view;
    this.investor = investor;
    this.computeCommands = new HashMap<>();
    initializeCommands();

    frame = new JFrame("Compute Options");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();
    frame.getContentPane().add(panel);

    JLabel titleLabel = new JLabel("Choose a computation:");
    panel.add(titleLabel);

    JButton averageButton = new JButton("Average");
    averageButton.addActionListener(e -> {
      handleCommandSelection("average");
    });
    panel.add(averageButton);

    JButton crossoverButton = new JButton("Crossover");
    crossoverButton.addActionListener(e -> {
      handleCommandSelection("crossover");
    });
    panel.add(crossoverButton);

    JButton gainButton = new JButton("Gain/Loss");
    gainButton.addActionListener(e -> {
      handleCommandSelection("gain");
    });
    panel.add(gainButton);

    frame.setSize(400, 300);
    frame.setVisible(false);  // Initially hidden
  }

  private void initializeCommands() {
    computeCommands.put("average", new XDayMoveCommand(view, model));
    computeCommands.put("crossover", new XDayCrossover(view, model));
    computeCommands.put("gain", new GainOrLoss(view, model));
  }

  private void handleCommandSelection(String commandName) {
    frame.dispose();

    view.promptForInput("Enter the date that you want to start the calculation (yyyy-mm-dd): ");
    String dateString = view.promptForInput("Enter the date (yyyy-mm-dd):");

    Command command = computeCommands.get(commandName);
    if (command != null) {
      command.execute();
    } else {
      throw new IllegalArgumentException("Invalid command selection.");
    }
  }

  @Override
  public void execute() {
    frame.setVisible(true);  // Show the compute options when executed
  }

  @Override
  public void takeInputs() {
  }
}
