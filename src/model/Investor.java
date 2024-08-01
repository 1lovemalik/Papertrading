package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Investor class represents an investor with a name and a portfolio.
 */
public class Investor implements IInvestor {
    private Map<String, PortfolioModel> portfolios;

    /**
     * Constructs an Investor with the specified name and initializes a new PortfolioModel.
     */
    public Investor() {
        this.portfolios = new HashMap<>();
    }


    public Map<String, PortfolioModel> getPortfolios() {
        return portfolios;
    }

    public void addPortfolio(String namePortfolio) {
        this.portfolios.put(namePortfolio, new PortfolioModel(0, new HashMap<>()));
    }

    public int numPortFolios() {
        return portfolios.size();
    }

    /**
     * Start creating a portfolio.
     */
    public void startRunCheck() {
        if (numPortFolios() == 0) {
            System.out.println("Lets start by creating your first portfolio!"
                    +
                    "input 'Add portfolio' to start.");
        }
    }

    /**
     * Add stock to the portfolio.
     *
     * @param name  name of the investor
     * @param stock stock wants to be added.
     */
    public void addStock(String name, IStock stock) {
        if (!this.getPortfolios().containsKey(name)) {
            throw new IllegalArgumentException("This portfolio Name does not exist!");
        } else {
            portfolios.get(name).stocks.put(stock.getTickerName(), stock);
        }
    }

    public void addStock(String name, IStock stock, LocalDate date) {
        if (!this.getPortfolios().containsKey(name)) {
            throw new IllegalArgumentException("This portfolio name does not exist!");
        } else {
            stock.setDate(date);
            portfolios.get(name).stocks.put(stock.getTickerName(), stock);
        }
    }


    public void rebalancePortfolio(String namePortfolio, ArrayList<String> nameArray,
                                   ArrayList<Integer> percentageArray, LocalDate date) {
        if (date.isBefore(portfolios.get(namePortfolio).getEarliestDate())
                || date.isAfter(portfolios.get(namePortfolio).getLatestDate())) {
            throw new IllegalArgumentException("This is not a valid date!");
        } else {
            portfolios.get(namePortfolio).rebalancePortfolio(nameArray, percentageArray, date);
        }
    }

    /**
     * Get composition of stocks in portfolio.
     *
     * @param namePortfolio name of portfolio.
     * @param date          date.
     */
    public void getCompositionOfStocks(String namePortfolio, LocalDate date) {
        if (!this.getPortfolios().containsKey(namePortfolio)) {
            throw new IllegalArgumentException("This portfolio does not exist!");
        } else {
            portfolios.get(namePortfolio).compositionStocks(date);
        }
    }

    public double getValuePortfolio(String namePortfolio, LocalDate date) {
        if (!this.getPortfolios().containsKey(namePortfolio)) {
            throw new IllegalArgumentException("this portfolio does not exist!");
        } else {
            return getPortfolios().get(namePortfolio).calculateTotalValueDate(date);
        }
    }

    /**
     * Method to find interval values.
     *
     * @param namePortfolio namePortfolio.
     * @param startDate     start date.
     * @param endDate       end date.
     * @param intervals     intervals.
     * @return hash map.
     */
    public Map<LocalDate, Double> findInternalValues(String namePortfolio, LocalDate startDate,
                                                     LocalDate endDate, int intervals) {
        if (!this.getPortfolios().containsKey(namePortfolio)) {
            throw new IllegalArgumentException("This portfolio does not exist.");
        } else {
            return portfolios.get(namePortfolio).findIntervalValues(intervals, startDate, endDate);
        }
    }


    public void removeStock(String namePortfolio, IStock stock, double quantity) {
        if (!this.getPortfolios().containsKey((namePortfolio))) {
            throw new IllegalArgumentException("This portfolio does not exist!");
        } else {
            IStock stock1 = portfolios.get(namePortfolio).stocks.get(stock.getTickerName());
            portfolios.get(namePortfolio).removeStock(stock.getTickerName(), quantity);
        }
    }

}
