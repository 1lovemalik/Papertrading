//package model;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * StockTest class.
// */
//public class StockTest {
//    Stock stock;
//    LocalDate date;
//
//    @Before
//    public void setUp() {
//        this.date = LocalDate.of(2024, 6, 11);
//        stock = new Stock("AAPL", date);
//
//        // Mocking closingPrices data for the stock
//        List<Double> closingPrices = new ArrayList<>();
//        for (int i = 1; i <= 35; i++) {
//            closingPrices.add(i * 10.0); // adding dummy data
//        }
//        stock.setClosingPrices(closingPrices); // assuming there's a method to set closing prices for testing
//    }
//
//    @Test
//    public void testStockCreation() {
//        assertEquals("AAPL", stock.getTickerName());
//    }
//
//    @Test
//    public void testGetDayValue() {
//        setUp();
//
//        double[] days = stock.getDayValueMap().get(stock.getDate());
//
//        String[] arr = new String[2];
//
//        arr[0] = "no";
//        arr[1] = "yes";
//
//        System.out.println(arr.toString() + "arr String");
//
//        System.out.println(days.toString() + " days String");
//
//        System.out.println(days.length);
//
//        Assert.assertEquals("no", arr[0]);
////        Assert.assertEquals(22.3, days[3], .01);
//
//    }
//
//
//    @Test
//    public void testCalculateGainOverDays() {
//        double gain = stock.calculateGainOverDays(5);
//        assertEquals(50, gain, .1); // Expected gain over 5 days
//
//        gain = stock.calculateGainOverDays(10);
//        assertEquals(100, gain); // Expected gain over 10 days
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testCalculateGainOverDaysNotEnoughData() {
//        stock.calculateGainOverDays(40); // Should throw exception
//    }
//
//    @Test
//    public void testCalculateXDayMovingAverage() {
//        double movingAverage = stock.calculateDayMovingAverage(5);
//        assertEquals(330.0, movingAverage, 0.001); // Expected moving average over 5 days
//
//        movingAverage = stock.calculateDayMovingAverage(10);
//        assertEquals(305.0, movingAverage, 0.001); // Expected moving average over 10 days
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testCalculateXDayMovingAverageNotEnoughData() {
//        stock.calculateDayMovingAverage(40); // Should throw exception
//    }
//
//    @Test
//    public void testIsXDayCrossover() {
//        boolean crossover = stock.isXDayCrossover(5);
//        assertTrue(crossover); // Assuming today's closing price > 5-day moving average
//
//        crossover = stock.isXDayCrossover(30);
//        assertTrue(crossover); // Assuming today's closing price > 30-day moving average
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testIsXDayCrossoverNotEnoughData() {
//        stock.isXDayCrossover(40); // Should throw exception
//    }
//
//}