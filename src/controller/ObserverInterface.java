package controller;

/**
 * This is the interface for an observer of the Reversi model.
 * Allowing one to be a listener of a game model.
 */
public interface ObserverInterface {

  /**
   * Notifies this observer that an event occurred. And then does something.
   * For Reversi, this could mean the model notifies the controller when it is their turn.
   */
  void getNotifiedItsYourPlayersMove();

}
