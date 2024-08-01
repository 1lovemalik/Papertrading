package view;

import controller.IController;
import controller.commands.ComputeCommand;
import model.IInvestor;
import model.IModel;

import javax.swing.*;
import java.awt.*;

public class PortfolioGUIView extends JFrame implements IView {
    private IController controller;
    private JButton newPortfolioButton;
    private JButton buySellButton;
    private JButton queryButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton sellButton;
    private JButton computeButton;
    private JButton addFundsButton;
    private JButton performanceButton;
    private JButton rebalanceButton;
    private JButton numberOfPortfoliosButton;
    private JTextArea messageArea;

    private ComputeCommand computeCommand;

    public PortfolioGUIView(IModel model, IInvestor investor) {
        super("Portfolio Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        computeCommand = new ComputeCommand(model, this, investor);

        JTabbedPane tabbedPane = new JTabbedPane();

        Color buttonColor = new Color(36, 97, 142);
        Color panelColor = new Color(245, 245, 245);
        Color backgroundColor = new Color(105, 91, 164);

        JPanel portfolioPanel = new JPanel();
        portfolioPanel.setLayout(new GridLayout(2, 2, 10, 10));
        portfolioPanel.setBackground(panelColor);
        newPortfolioButton = createColoredButton("New Portfolio", buttonColor, Color.BLACK);
        saveButton = createColoredButton("Save Portfolio", buttonColor, Color.BLACK);
        loadButton = createColoredButton("Load Portfolio", buttonColor, Color.BLACK);
        numberOfPortfoliosButton = createColoredButton("Number of Portfolios", buttonColor, Color.BLACK);
        portfolioPanel.add(newPortfolioButton);
        portfolioPanel.add(saveButton);
        portfolioPanel.add(loadButton);
        portfolioPanel.add(numberOfPortfoliosButton);

        JPanel stockPanel = new JPanel();
        stockPanel.setLayout(new GridLayout(2, 2, 10, 10));
        stockPanel.setBackground(panelColor);
        buySellButton = createColoredButton("Buy Stock", buttonColor, Color.PINK);
        sellButton = createColoredButton("Sell Stock", buttonColor, Color.PINK);
        computeButton = createColoredButton("Compute Value", buttonColor, Color.PINK);
        queryButton = createColoredButton("Get Composition", buttonColor, Color.PINK);
        stockPanel.add(buySellButton);
        stockPanel.add(sellButton);
        stockPanel.add(computeButton);
        stockPanel.add(queryButton);

        JPanel fundsPanel = new JPanel();
        fundsPanel.setLayout(new GridLayout(1, 1));
        fundsPanel.setBackground(panelColor);
        addFundsButton = createColoredButton("Add Funds", buttonColor, Color.GREEN);
        fundsPanel.add(addFundsButton);

        JPanel performancePanel = new JPanel();
        performancePanel.setLayout(new GridLayout(2, 1, 10, 10));
        performancePanel.setBackground(panelColor);
        performanceButton = createColoredButton("Performance Over Time", buttonColor, Color.CYAN);
        rebalanceButton = createColoredButton("Rebalance Portfolio", buttonColor, Color.CYAN);
        performancePanel.add(performanceButton);
        performancePanel.add(rebalanceButton);

        tabbedPane.addTab("Portfolio", portfolioPanel);
        tabbedPane.addTab("Stocks", stockPanel);
        tabbedPane.addTab("Funds", fundsPanel);
        tabbedPane.addTab("Performance", performancePanel);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(backgroundColor);

        add(tabbedPane, BorderLayout.CENTER);
        add(new JScrollPane(messageArea), BorderLayout.SOUTH);
        setVisible(true);

        computeButton.addActionListener(e -> computeCommand.execute());
    }

    private JButton createColoredButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return button;
    }

    public JButton getNewPortfolioButton() {
        return newPortfolioButton;
    }

    public JButton getBuySellButton() {
        return buySellButton;
    }

    public JButton getQueryButton() {
        return queryButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSellButton() {
        return sellButton;
    }

    public JButton getComputeButton() {
        return computeButton;
    }

    public JButton getAddFundsButton() {
        return addFundsButton;
    }

    public String promptForInput(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

    @Override
    public IController setController(IController controller) {
        this.controller = controller;
        setUpButtonListeners();
        return controller;
    }

    public JButton getPerformanceButton() {
        return performanceButton;
    }

    public JButton getRebalanceButton() {
        return rebalanceButton;
    }

    public JButton getNumberOfPortfoliosButton() {
        return numberOfPortfoliosButton;
    }

    public void displayMessage(String message) {
        messageArea.append(message + "\n");
    }

    private void setUpButtonListeners() {
        getNewPortfolioButton().addActionListener(e -> controller.executeCommand("add portfolio"));
        getBuySellButton().addActionListener(e -> controller.executeCommand("buy"));
        getQueryButton().addActionListener(e -> controller.executeCommand("get composition"));
        getSellButton().addActionListener(e -> controller.executeCommand("sell"));
        getComputeButton().addActionListener(e -> controller.executeCommand("compute"));
        getAddFundsButton().addActionListener(e -> controller.executeCommand("add funds"));
        getPerformanceButton().addActionListener(e -> controller.executeCommand("performance"));
        getRebalanceButton().addActionListener(e -> controller.executeCommand("rebalance"));
        getNumberOfPortfoliosButton().addActionListener(e -> controller.executeCommand("number portfolios"));
    }
}
