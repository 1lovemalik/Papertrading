// IView.java
package view;

import controller.IController;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The IView interface defines methods for displaying messages and prompting for input,
 * applicable to both text-based and GUI views.
 */
public interface IView {
  /**
   * Displays a message to the user.
   *
   * @param message the message to display
   */
  void displayMessage(String message);

  /**
   * Prompts the user for input with a given prompt message.
   *
   * @param prompt the prompt message
   */
  String promptForInput(String prompt);


  IController setController(IController controller);

}
