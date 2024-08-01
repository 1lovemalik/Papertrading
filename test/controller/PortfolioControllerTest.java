package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;
import view.PortfolioGUIView;
import view.PortfolioView;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PortfolioControllerTest {
  PortfolioModel model;
  PortfolioView view;
  PortfolioController controller;

  @Before
  public void setUp() {
    model = new PortfolioModel(10000, new HashMap<>());
    view = new PortfolioView();
    controller = new PortfolioController(view, model);
  }

  @Test
  public void testInitialSetup() {
    assertNotNull(controller);
  }

//  @Test
//  public void testBuyStock() {
//    controller.buyStock("AAPL", 10, 150.00);
//    assertTrue(model.getPortfolio().containsKey("AAPL"));
//  }
//
//  @Test
//  public void testSellStock() {
//    controller.buyStock("AAPL", 10, 150.00);
//    controller.sellStock("AAPL", 5);
//    assertEquals(5, model.getPortfolio().get("AAPL").getQuantity());
//  }
//
//  @Test
//  public void testPortfolioValueAfterTransactions() {
//    controller.buyStock("AAPL", 10, 150.00);
//    controller.buyStock("GOOGL", 5, 1000.00);
//    assertEquals(6500.00, model.calculateTotalValue(), 0.01);
//  }
//
//  @Test
//  public void testInvalidStockTransaction() {
//    assertFalse(controller.sellStock("AAPL", 10));
//  }
//
//  @Test
//  public void testViewUpdatesAfterTransaction() {
//    controller.buyStock("AAPL", 10, 150.00);
//    assertEquals("Stock AAPL bought", view.getLastMessage());
//  }
}
