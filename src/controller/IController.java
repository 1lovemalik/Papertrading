package controller;

import view.IView;

/**
 * The IController interface defines the contract for controllers in the application.
 * It includes a method to start the controller.
 */
public interface IController {
  void run();

  void executeCommand(String commandName);

  IView setView(IView view);
}
