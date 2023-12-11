package controller;

import model.Coordinate;
import model.Player;
import model.ReadonlyReversiModel;
import model.ReversiModel;
import view.ViewInterface;

/**
 * This is the controller for the game of Reversi.
 * Each controller controls for its own player. A controller is an observer
 * of the model, waiting for its players turn. Controller is also an observer of
 * the view, when an event is detected by the view, delegate to the controller.
 */
public class ReversiController implements ControllerFeatures, ObserverInterface {

  protected ReversiModel model;
  protected Player player;
  protected ViewInterface view;
  private boolean gameOverDisplayed;


  /**
   * Constructor for the controller.
   *
   * @param model  the model that the controller will be using.
   * @param player the player either human or AI that this controller is for.
   * @param view   the view that the controller is working with.
   */
  public ReversiController(ReversiModel model, Player player, ViewInterface view) {
    this.model = model;
    this.player = player;
    this.view = view;
    view.addFeatures(this);
    model.subscribe(this);
    gameOverDisplayed = false;
  }

  @Override
  public void getNotifiedItsYourPlayersMove() {
    //if your move, make player move based on player's strategy
    //ai player will make their move, human player would do nothing since their strat is null
    //System.out.println("Controller " + player.getPlayerNumber() + " notified!");
    if (player.getPlayerStrategy() == null && !model.isGameOver()) {
      view.displayMessage("It is your turn player" + player.getPlayerNumber());
    }

    view.display();

    //if player is computer
    if (player.getPlayerStrategy() != null) {
      Coordinate playedCoordinate = player.play(model);

      try {
        this.move(playedCoordinate);

      } catch (NullPointerException e) {
        this.pass();
      } catch (IllegalStateException ise) {
        this.pass();
      }
    }
  }

  @Override
  public void pass() {
    try {
      model.passMove(player.getPlayerNumber());
      view.display();
      //System.out.println("passed");
      model.notifyObserverTurn();

      if (model.isGameOver() && !gameOverDisplayed) {
        displayGameOver();
        gameOverDisplayed = true;
      }

    } catch (IllegalStateException ise) {
      view.displayMessage("Not your turn player" + player.getPlayerNumber());
      //System.out.println("Not your turn player " + player.getPlayerNumber());
    }
  }

  @Override
  public void move(Coordinate c) {
    //System.out.println("ReversiController cubic coord: " + c.getX() + c.getY() + c.getZ());
    try {
      model.flipCell(c, player.getPlayerNumber());
      view.display();
      //System.out.println("moved");
      model.notifyObserverTurn();
      if (model.isGameOver()) {
        if (model.isGameOver() && !gameOverDisplayed) {
          displayGameOver();
          gameOverDisplayed = true;
        }
      }
    } catch (IllegalStateException ise) {
      view.displayMessage("Not your turn player" + player.getPlayerNumber());
      //System.out.println("Not your turn player " + player.getPlayerNumber());
    } catch (IllegalArgumentException iae) {
      view.displayMessage("Illegal move for player" + player.getPlayerNumber());
      //System.out.println("Not a valid move");
    }
  }

  @Override
  public int hintHelper(ReadonlyReversiModel model,
                        Coordinate selectedHexagon) {
    //if it's not their turn, don't do anything
    if (model.getCurrentTurn() != player.getPlayerNumber()) {
      return -1;
    }
    return model.howManyCellsDoesThisMoveFlip(selectedHexagon, player.getPlayerNumber());
  }


  // game over message
  private void displayGameOver() {
    if (model.getScore(player.getPlayerNumber())
            > model.getScore(3 - player.getPlayerNumber())) {
      view.displayMessage("Player " + player.getPlayerNumber() + " wins!");
    }
    else if (model.getScore(player.getPlayerNumber())
            < model.getScore(3 - player.getPlayerNumber())) {
      view.displayMessage("Player " + (3 - player.getPlayerNumber()) + " wins!");
    }
    else {
      view.displayMessage("Tie!");
    }
  }
}