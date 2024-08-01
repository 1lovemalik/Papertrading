
Descriptions of Stock Project Design

Controller Package
IController.java
Defines the contract for a controller in the MVC architecture.

MainApplication.java
The entry point of the application. It initializes the necessary components and starts the application.

MockController.java
A mock implementation of the controller, primarily for testing purposes.

PortfolioController.java
Manages the interactions between the view and the model in the context of a portfolio.
 It handles user inputs, invokes model updates, and refreshes the view.

Commands (in commands package)
Encapsulate specific actions performed in the system.

BuyCommand.java: Executes the logic for buying stocks.
SellCommand.java: Executes the logic for selling stocks.
ComputeCommand.java: Command to compute various metrics.
GainOrLoss.java: Computes gain or loss for a stock or portfolio.
XDayCrossover.java: Implements a crossover strategy for trading.
XDayMoveCommand.java: Command for executing X-day moving averages.

Model Package
IModel.java
Defines the contract for the model in the MVC architecture.

Investor.java
Represents an investor with attributes and behaviors.

IStock.java
Defines the contract for a stock.

MockModel.java
A mock implementation of the model, used for testing purposes.

PortfolioModel.java
Represents a portfolio of stocks. It maintains the balance, list of stocks, and provides methods
 to buy, sell, and compute the total value of the portfolio.

Methods:
addStock(Stock stock): Adds a stock to the portfolio.
removeStock(String symbol): Removes a stock from the portfolio.
calculateTotalValue(): Calculates the total value of the portfolio.
Stock.java
Represents an individual stock with attributes such as symbol, quantity, and price.

Methods:
updateQuantity(int amount): Updates the quantity of the stock.
updatePrice(double newPrice): Updates the price of the stock.
getValue(): Computes the total value of the stock based on quantity and price.
View Package
IView.java
Defines the contract for the view in the MVC architecture.

PortfolioView.java
Implements IView. Responsible for displaying portfolio-related data to the user and
 capturing user inputs. It updates the user interface based on the data provided by the controller.

Test Package
StocksTest.java
General test suite for the application.

Controller Tests (in controller package)
PortfolioCommands.java
Tests the commands related to portfolio operations.

Model Tests (in model package)
MockModelTest.java
Tests the MockModel class, including:

Basic functionality.
Edge cases.
Logging of operations.
Initial state verification.
PortfolioModelTest.java
Tests the PortfolioModel class for its methods and ensures correct behavior during
operations like buying, selling, and calculating values.

View Tests (in view package)
PortFolioViewTest.java
Tests the PortfolioView class to ensure it correctly displays messages and
updates based on interactions.

How the Application Works
Initialization:

MainApplication.java initializes the PortfolioController, PortfolioModel, and PortfolioView.
User Interaction:

User interacts with the view (PortfolioView), which sends inputs to the controller (PortfolioController).
Controller Processing:

PortfolioController processes user inputs and updates the model (PortfolioModel)
using appropriate commands (e.g., BuyCommand, SellCommand).
Model Update:

PortfolioModel updates the portfolio by adding or removing stocks and recalculating the portfolio value.
View Update:

PortfolioController updates the view (PortfolioView) to reflect the changes in the model.
Detailed Class Descriptions
Controller Classes
PortfolioController.java
Handles user inputs and updates the model and view. It manages commands like buying and
 selling stocks and ensures the view reflects the current state of the model.

Command Classes
BuyCommand: Executes buying stocks and updates the portfolio.
SellCommand: Executes selling stocks.
ComputeCommand: Computes specific metrics or values.
GainOrLoss: Calculates gain or loss for a stock or portfolio.
XDayCrossover: Implements a crossover trading strategy.
XDayMoveCommand: Executes X-day moving averages.
Model Classes
PortfolioModel.java
Represents the core data structure for a user's portfolio. It maintains the balance,
list of stocks, and provides methods to buy, sell, and compute the total value of the portfolio.

Stock.java
Represents an individual stock. Contains attributes like symbol, quantity, and price.

View Classes
PortfolioView.java
Responsible for displaying information to the user and capturing user inputs.
It updates the user interface based on the data provided by the controller.

Test Classes
MockModelTest.java
Contains unit tests for the MockModel class, checking various functionalities and edge cases.

PortfolioModelTest.java
Tests the PortfolioModel class for its methods and ensures correct behavior during operations like buying,
selling, and calculating values.

PortfolioControllerTest.java
Tests the PortfolioController to ensure it correctly manages the interaction between the model and view.

StockTest.java
Tests the Stock class to ensure correct initialization and behavior of its methods.

PortfolioViewTest.java
Tests the PortfolioView class to ensure it correctly displays messages and updates based on interactions
