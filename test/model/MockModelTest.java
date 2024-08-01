//package model;
//
//import controller.PortfolioController;
//import controller.commands.BuyCommand;
//import controller.commands.Command;
//import controller.commands.SellCommand;
//import org.junit.Before;
//import org.junit.Test;
//import view.PortfolioGUIView;
//import view.PortfolioView;
//
//import java.util.HashMap;
//import java.util.Scanner;
//
//import static org.junit.Assert.*;
//
//public class MockModelTest {
//  PortfolioModel model;
//  StringBuilder log;
//  MockModel mock;
//  PortfolioController controller;
//  PortfolioView view;
//  Investor investor;
//  Scanner scanner;
//  Command buyCommand;
//  Command sellCommand;
//
//  @Before
//  public void setUp() {
//    log = new StringBuilder();
//    model = new PortfolioModel(10000, new HashMap<>());
//    mock = new MockModel(model, log);
//    view = new PortfolioView();
//    controller = new PortfolioController(view, model, new PortfolioGUIView());
//    scanner = new Scanner(System.in);
//  }
//
//  @Test
//  public void testBuyCommand() {
//    buyCommand = new BuyCommand(mock, view, investor, scanner);
//    buyCommand.execute();
//    assertTrue(log.toString().contains("BuyCommand executed"));
//  }
//
//
//  @Test
//  public void testSellCommand() {
//    sellCommand = new SellCommand(mock, view, investor, scanner);
//    sellCommand.execute();
//    assertTrue(log.toString().contains("SellCommand executed"));
//  }
//
//  @Test
//  public void testProduceStock() {
//    buyCommand = new BuyCommand(mock, view, investor, scanner);
//    buyCommand.execute();
//    assertTrue(log.toString().contains("ProduceStock executed"));
//  }
//
//  @Test
//  public void testLogEntries() {
//    buyCommand = new BuyCommand(mock, view, investor, scanner);
//    sellCommand = new SellCommand(mock, view, investor, scanner);
//    buyCommand.execute();
//    sellCommand.execute();
//    String logOutput = log.toString();
//    assertTrue(logOutput.contains("BuyCommand executed"));
//    assertTrue(logOutput.contains("SellCommand executed"));
//  }
//
//  @Test
//  public void testMockModelCreation() {
//    assertEquals(10000, mock.getBalance(), 0.01);
//  }
//
//  @Test
//  public void testInsufficientBalance() {
//    mock = new MockModel(new PortfolioModel(100, new HashMap<>()), log);
//    buyCommand = new BuyCommand(mock, view, investor, scanner);
//    buyCommand.execute();
//    assertTrue(log.toString().contains("Insufficient balance"));
//  }
//
//  @Test
//  public void testNegativeBalance() {
//    mock = new MockModel(new PortfolioModel(-100, new HashMap<>()), log);
//    assertEquals(-100, mock.getBalance(), 0.01);
//  }
//
//
//  @Test
//  public void testValidTransactionLog() {
//    Scanner scanner = new Scanner(System.in);
//    buyCommand = new BuyCommand(mock, view, investor, scanner);
//    buyCommand.execute();
//    scanner.nextLine();
//    String logOutput = log.toString();
//    assertTrue(logOutput.contains("Transaction successful"));
//  }
//}
