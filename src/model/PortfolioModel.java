package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * The PortfolioModel class represents the model in the portfolio management application.
 * It holds the balance and the stocks in the portfolio.
 */
public class PortfolioModel implements IModel {
    double balance;
    // TODO: String == tickerName
    public Map<String, IStock> stocks;

    /**
     * Constructs a PortfolioModel with the specified initial balance and an empty stock map.
     *
     * @param balance the initial balance of the portfolio
     * @param stocks  a map of stock ticker symbols to stock instances
     */
    public PortfolioModel(int balance, Map<String, Stock> stocks) {
        this.balance = 30000;
        this.stocks = new HashMap<>();
    }

    /**
     * Retrieves the stock associated with the given ticker name.
     *
     * @param tickerName the ticker symbol of the stock
     * @return the Stock object associated with the ticker symbol
     */
    public IStock getStockName(String tickerName) {
        return stocks.get(tickerName);
    }

    /**
     * Produces a stock with the given ticker name if it does not already exist in the portfolio.
     *
     * @param tickerName the ticker symbol of the stock
     * @return the Stock object associated with the ticker symbol
     */
    public IStock produceStock(String tickerName, LocalDate date) {
        if (!stocks.containsKey(tickerName)) {
            Stock stock = new Stock(tickerName, date);
            stocks.put(tickerName, stock);
            System.out.println("hdiid");
        }
        return stocks.get(tickerName);
    }


    @Override
    public void removeStock(String tickerName, double quantityLess) {
        double stockQuantity = this.stocks.get(tickerName).getQuantity();
        if (stockQuantity < quantityLess) {
            throw new IllegalArgumentException("You cannot sell more stocks than you own.");
        } else if (stockQuantity - quantityLess > 0) {
            stocks.get(tickerName).setQuantity(stockQuantity - quantityLess);
        } else if (stockQuantity - quantityLess == 0) {
            stocks.remove(tickerName);
        }
    }

    public void removeStock(String tickerName, double quantityLess, LocalDate date) {
        double stockQuantity = this.stocks.get(tickerName).getQuantity();
        if (stockQuantity < quantityLess) {
            throw new IllegalArgumentException("You cannot sell more stocks than you own.");
        } else if (!this.stocks.containsKey(tickerName)) {
            throw new IllegalArgumentException("You cannot sell a stock that you do not own.");
        } else if (date.isBefore(stocks.get(tickerName).getDate())) {
            throw new IllegalArgumentException("You cannot sell a stock before it has been bought");
        } else if (stockQuantity - quantityLess > 0) {
            stocks.get(tickerName).setQuantity(stockQuantity - quantityLess);
        } else if (stockQuantity - quantityLess == 0) {
            stocks.remove(tickerName);
        }
    }


    public void addFunds(double funds) {
        this.balance += funds;
    }

    /**
     * Sets the balance of the portfolio.
     *
     * @param balance the new balance of the portfolio
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the balance of the portfolio.
     *
     * @return the balance of the portfolio
     */
    public double getBalance() {
        return balance;
    }


    /**
     * Calculate total value.
     *
     * @return total value of portfolio
     */
    public double calculateTotalValue() {
        double totalValue = 0;
        for (IStock stock : stocks.values()) {
            totalValue += stock.getPriceOfStock() * stock.getQuantity();
        }
        return totalValue;
    }

    @Override
    public double calculateTotalValueDate(LocalDate date) {
        double totalValue = 0;
        for (IStock stock : stocks.values()) {
            double currValue = stock.getValueStock(date);
            totalValue += currValue;
        }
        return totalValue;
    }


    /**
     * Rebalance portfolio method.
     *
     * @param stockNameArray       stockNameArray
     * @param stockPercentageArray stockPercentageArray
     */
    public void rebalancePortfolio(ArrayList<String> stockNameArray,
                                   ArrayList<Integer> stockPercentageArray, LocalDate date) {
        double totalValue = calculateTotalValue();
        // Step 2: Calculate the target value for each stock based on the specified percentages
        Map<String, Double> targetValues = new HashMap<>();
        for (int i = 0; i < stockNameArray.size(); i++) {
            String stockName = stockNameArray.get(i);
            int percentage = stockPercentageArray.get(i);
            double targetValue = totalValue * percentage / 100;
            targetValues.put(stockName, targetValue);
        }
        // Step 3: Adjust stock quantities to match the target values
        for (String stockName : stockNameArray) {
            double currentStockValue = stocks.get(stockName).getPriceOfStock(date)
                    * stocks.get(stockName).getQuantity();
            double targetStockValue = targetValues.get(stockName);

            if (currentStockValue > targetStockValue) {
                // Sell excess stock
                double amountToSell = currentStockValue - targetStockValue;
                int sharesToSell = (int) (amountToSell / stocks.get(stockName).getPriceOfStock(date));
                removeStock(stockName, sharesToSell);
            } else if (currentStockValue < targetStockValue) {
                // Buy more stock
                double amountToBuy = targetStockValue - currentStockValue;
                int sharesToBuy = (int) (amountToBuy / stocks.get(stockName).getPriceOfStock(date));
                stocks.get(stockName).setQuantity(stocks.get(stockName).getQuantity() + sharesToBuy);
            }
        }
    }

    public LocalDate getEarliestDate() {
        LocalDate date = LocalDate.MAX;
        LocalDate currentDate;
        for (String stock : stocks.keySet()) {
            currentDate = stocks.get(stock).getDate();
            if (currentDate.isBefore(date)) {
                date = currentDate;
            }
        }
        if (date == LocalDate.MAX) {
            throw new IllegalStateException("There are no stocks within this portfolio");
        } else {
            return date;
        }
    }

    public LocalDate getLatestDate() {
        LocalDate date = LocalDate.MIN;
        LocalDate currentDate;
        for (String stock : stocks.keySet()) {
            currentDate = stocks.get(stock).getDate();
            if (currentDate.isAfter(date)) {
                date = currentDate;
            }
        }
        if (date == LocalDate.MIN) {
            throw new IllegalStateException("There are no stocks within this portfolio");
        } else {
            return date;
        }
    }

    /**
     * composition of stock.
     *
     * @param date date wants to be calculated.
     */
    public void compositionStocks(LocalDate date) {
        Map<String, IStock> localStockMap = new HashMap<>(this.stocks);


        System.out.println("The composition of stocks within this portfolio is:");
        Object[] arrTickerName = localStockMap.keySet().toArray();
        for (int i = 0; i < arrTickerName.length; i++) {

            if (date.isBefore(localStockMap.get(arrTickerName[i]).getDate())) {
                localStockMap.remove(arrTickerName[i]);
            }

            double quantity = localStockMap.get(arrTickerName[i]).getQuantity();

            System.out.println((i + 1) + " " + " " + arrTickerName[i] + " " + quantity);
        }

    }

    /**
     * Find intervals values method.
     *
     * @param interval  interval
     * @param startDate start date
     * @param endDate   end date
     * @return hash map.
     */
    public Map<LocalDate, Double> findIntervalValues(int interval,
                                                     LocalDate startDate,
                                                     LocalDate endDate) {
        long daysUntil = DAYS.between(startDate, endDate);
        int daysAcc = 0;
        HashMap<LocalDate, Double> intervalValues = new HashMap<>();

        while (daysAcc <= daysUntil) {  // Fixed condition
            LocalDate currentDate = startDate.plusDays(daysAcc);
            double currentValue = calculateTotalValueDate(currentDate);

            if (currentValue > 0) { // Only add if value is greater than 0
                intervalValues.put(currentDate, currentValue);
            }

            daysAcc += interval; // Move to the next interval
        }
        return intervalValues;
    }

}
