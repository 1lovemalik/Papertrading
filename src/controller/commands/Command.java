package controller.commands;

/**
 * The Command interface defines the contract for all command classes in the application.
 * It includes a method to execute the command.
 */
public interface Command {

  /**
   * Executes the command.
   */
  void execute();

  void takeInputs();

}