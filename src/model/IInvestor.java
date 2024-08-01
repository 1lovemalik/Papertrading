package model;

import java.util.Map;

/**
 * Interface for investor.
 */
public interface IInvestor {

  void addPortfolio(String name);

  Map getPortfolios();

  void addStock(String namePortfolio, IStock stock);

  void removeStock(String namePortfolio, IStock stock, double quantity);
}
