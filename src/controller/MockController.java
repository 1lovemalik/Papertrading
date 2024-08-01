package controller;

import view.IView;

/**
 * The MockController class is a mock implementation of the IController interface
 * for testing purposes. It logs method calls and delegates the actual execution
 * to a real PortfolioController.
 */
public class MockController implements IController {
  StringBuilder log;
  PortfolioController controller;

  /**
   * Constructs a MockController with the specified log and controller.
   *
   * @param log        a StringBuilder to record method calls
   * @param controller the real PortfolioController to delegate execution to
   */
  MockController(StringBuilder log, PortfolioController controller) {
    this.log = new StringBuilder();
    this.controller = controller;
  }

  /**
   * Logs the call to the run method and then calls the run method on the real controller.
   */
  @Override
  public void run() {
    log.append("Run method was called");
    controller.run();
  }

  @Override
  public void executeCommand(String commandName) {

  }

  @Override
  public IView setView(IView view) {
    return null;
  }

}
