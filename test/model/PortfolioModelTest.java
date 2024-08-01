package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PortfolioModelTest {
  IModel model;
  Stock googStock;
  IInvestor investor;
  LocalDate dateGoog;

  @Before
  public void setUp() {
    model = new PortfolioModel(10000, new HashMap<>());
    dateGoog = LocalDate.of(2024, 5,8);
    googStock = new Stock("GOOG", dateGoog);
    investor = new Investor();
  }

  @Test
  public void testInitialBalance() {
    assertEquals(30000, model.getBalance(), 0.01);
  }

//  @Test
//  public void testAddStock() {
//    Stock stock = new Stock("AAPL");
//    model.addStock(stock);
//    assertEquals(1, model.getPortfolio().size());
//    assertTrue(model.getPortfolio().containsKey("AAPL"));
//  }
//
//  @Test
//  public void testRemoveStock() {
//    Stock stock = new Stock("AAPL");
//    model.addStock(stock);
//    model.removeStock("AAPL");
//    assertEquals(0, model.getPortfolio().size());
//  }
//
//  @Test
//  public void testCalculateTotalValue() {
//    Stock stock1 = new Stock("AAPL");
//    Stock stock2 = new Stock("GOOGL");
//    model.addStock(stock1);
//    model.addStock(stock2);
//    assertEquals(6500.00, model.calculateTotalValue(), 0.01);
//  }
//
//  @Test
//  public void testInsufficientFunds() {
//    Stock stock = new Stock("AAPL");
//    assertFalse(model.buyStock(stock));
//  }
//
//  @Test
//  public void testExceedingSell() {
//    Stock stock = new Stock("AAPL");
//    model.addStock(stock);
//    assertFalse(model.sellStock("AAPL", 20));
//  }


  @Test
  public void testEmptyPortfolioValue() {
    assertEquals(0, model.calculateTotalValue(), 0.01);
  }

  @Test
  public void testComposition() {
    setUp();
  }

  @Test
  public void testStocks() {

    IStock stockCompare = model.produceStock("GOOG", dateGoog);
    Assert.assertEquals(googStock.getTickerName(), stockCompare.getTickerName());
    Assert.assertEquals(googStock.getQuantity(), stockCompare.getQuantity());

    investor.addPortfolio("first");
    investor.addStock("first", googStock);



  }
}
