package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/**
 * This class represents a Stock that contains the same fields as the columns in the Excel file.
 */
public class Stock implements IStock {
    private final String apiKey = "KABTICZOTHQMZ7GP";
    URL url = null;

    private final String tickerName;

    private double quantity;

    private Map<LocalDate, double[]> dayValueMap;

    BufferedReader reader;

    private List<Double> closingPrices = new ArrayList<>();

    private LocalDate date;

    double[] dayValueLocal;


    /**
     * Constructs a Stock with the specified ticker name.
     *
     * @param tickerName the ticker symbol of the stock
     * @param date       date
     */
    public Stock(String tickerName, LocalDate date) {
        this.tickerName = tickerName;
        this.quantity = 0;
        this.url = null;
        int startLine = 1;
        this.dayValueLocal = new double[5];
        this.dayValueMap = new HashMap<>();
        this.date = date;
        apiCall();

        try {

            this.reader = new BufferedReader(new FileReader("src\\" + tickerName + ".csv"));
            StringBuilder sb = new StringBuilder();
            String currentLine;

            int counter = startLine;


            while ((currentLine = reader.readLine()) != null) {
                if (counter > startLine) {
                    sb.append(currentLine).append("\n");
                    String[] value = currentLine.split(",");

                    String dates = value[0];

                    String[] dateArray = dates.split("-");

                    int year = Integer.parseInt(dateArray[0]);
                    int month = Integer.parseInt(dateArray[1]);
                    int day = Integer.parseInt(dateArray[2]);

                    LocalDate date1 = LocalDate.of(year, month, day);


                    double[] dayValueLocal = new double[5];

                    dayValueLocal[1] = Double.parseDouble(value[1]);
                    dayValueLocal[2] = Double.parseDouble(value[2]);
                    dayValueLocal[3] = Double.parseDouble(value[3]);
                    dayValueLocal[4] = Double.parseDouble(value[4]);

                    dayValueMap.put(date1, dayValueLocal);
                }

                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void apiCall() {
        try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */

            url = new URL("https://www.alphavantage"
                    + ".co/query?function=TIME_SERIES_DAILY"
                    + "&outputsize=full"
                    + "&symbol"
                    + "=" + tickerName + "&apikey=" + apiKey + "&datatype=csv");
        } catch (MalformedURLException e) {

            throw new RuntimeException("the alphavantage API has either changed or "
                    + "no longer works");
        }
        InputStream in = null;
        StringBuilder output = new StringBuilder();

        try {

            /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */

            in = url.openStream();
            int b;

            while ((b = in.read()) != -1) {
                output.append((char) b);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\"
                    + tickerName
                    + ".csv"));
            writer.write(output.toString());
            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("No price data found for " + tickerName);
        }
        populateClosingPrices();
    }

    /**
     * Retrieves the ticker name of the stock.
     *
     * @return the ticker name of the stock
     */
    public String getTickerName() {
        return this.tickerName;
    }

    /**
     * Retrieves the price of the stock.
     *
     * @return the price of the stock
     */
    public double getPriceOfStock() {
        double[] days = getDayValueMap().get(this.date);
        if (days == null) {
            throw new IllegalArgumentException("No price data found for " + this.date);
        } else {
            return days[4];
        }
    }

    /**
     * Implementation of getPriceOfStock that returns the value of a stock on a given day.
     *
     * @param date The date that the stock price is being fetched.
     * @return the price of the stock.
     */
    public double getPriceOfStock(LocalDate date) {
        double[] days = getDayValueMap().get(date);
        if (days == null) {
            throw new IllegalArgumentException("No price data found for " + date);
        } else {
            return days[4];
        }
    }


    /**
     * Get value of stock.
     *
     * @param date date
     * @return value of stock.
     */
    public double getValueStock(LocalDate date) {
        try {
            return getDayValueMap().get(date)[4];
        } catch (NullPointerException e) {
            e.getMessage();
            return 0;
        }
    }

    /**
     * Retrieves the quantity of the stock.
     *
     * @return the quantity of the stock
     *
     */
    @Override
    public double getQuantity() {
        System.out.println();
        return this.quantity;
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<LocalDate, double[]> getDayValueMap() {
        return dayValueMap;
    }

    /**
     * Sets the quantity of the stock.
     *
     * @param quantity the new quantity of the stock
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    void populateClosingPrices() {
        // Get the dates and sort them in chronological order
        List<LocalDate> dates = new ArrayList<>(this.dayValueMap.keySet());
        Collections.sort(dates);

        // Iterate over the sorted dates
        for (LocalDate date : dates) {
            // Get the array of day values for the current date
            double[] prices = this.dayValueMap.get(date);
            if (prices != null && prices.length > 4) {
                // Add the closing price (3rd index) to the closingPrices list
                this.closingPrices.add(prices[4]);
            }
        }
    }


    /**
     * Calculates the X-day gains of the stock.
     *
     * @return the X-day gains
     */
    @Override
    public double calculateGainOverDays(LocalDate startDate, LocalDate endDate) {
        int days = startDate.compareTo(endDate);

        if (!this.dayValueMap.containsKey(startDate) || !this.dayValueMap.containsKey(endDate)) {
            throw new IllegalArgumentException("This day does not have any data:"
                    + " This day might be a weekend");
        }

        double startValue = this.dayValueMap.get(date)[4];
        double endValue = this.dayValueMap.get(endDate)[4];
        if (closingPrices.size() < days) {
            throw new IllegalArgumentException("Not enough data to calculate"
                    + days + "day gain.");
        }
        return startValue - endValue;
    }

    /**
     * Calculates the X-day moving average of the stock.
     *
     * @return the X-day moving average
     */
    public double calculateDayMovingAverage(LocalDate startDate, LocalDate endDate) {
        int daysUntilEnd = 0;
        double sum = 0;
        int daysLost = 0;
        Stream<LocalDate> datesStream = startDate.datesUntil(endDate);
        long days = datesStream.count();

        while (daysUntilEnd < days) {
            // Stock value is not available during weekends
            if (!this.dayValueMap.containsKey(startDate.plusDays(daysUntilEnd))) {
                daysLost++;
                days++;
                daysUntilEnd++;
            } else {
                double currentValue = this.dayValueMap.get(startDate.plusDays(daysUntilEnd))[4];
                sum += currentValue;
                daysUntilEnd++;
            }
        }

        days -= daysLost;
        if (days == 0) {
            throw new IllegalArgumentException("Not enough data to calculate"
                    + days + "day moving average.");
        }
        return sum / days;
    }

    /**
     * Determines if there is a x-day crossover for the stock.
     *
     * @return true if there is a x-day crossover, false otherwise
     */
    public boolean isXDayCrossover(LocalDate startDate, LocalDate endDate) {
        double movingAverage = calculateDayMovingAverage(startDate, endDate);
        double endDateValue = this.dayValueMap.get(endDate)[4];
        long days = startDate.datesUntil(endDate).count();
        if (days <= 0) {
            throw new IllegalArgumentException("The end date has to be later than start date!");
        }
        return endDateValue > movingAverage;
    }
}
