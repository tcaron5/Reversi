import controller.ReversiController;
import model.Coordinate;
import model.Player;
import model.ReversiModel;
import view.ViewInterface;

/**
 * Mock controller used to confirm feature methods work as expected.
 * Outputs the behavior in the log.
 */
public class MockController extends ReversiController {

  private StringBuilder log;


  /**
   * Constructor for the mock controller.
   *
   * @param model the model that the controller will be using.
   * @param player the player either human or AI that this controller is for.
   * @param view the view that the controller is working with.
   * @param log the log to capture the actions.
   */
  public MockController(ReversiModel model, Player player, ViewInterface view, StringBuilder log) {
    super(model, player, view);
    this.log = log;
  }

  @Override
  public void getNotifiedItsYourPlayersMove() {
    super.getNotifiedItsYourPlayersMove();
    log.append("NOTIFIED: " + player.getPlayerNumber() + "\n");
  }


  @Override
  public void pass() {
    log.append("PASS: " + player.getPlayerNumber() + "\n");
    super.pass();
    //model.notifyObserverTurn();
  }

  @Override
  public void move(Coordinate c) {
    log.append("Player " + player.getPlayerNumber()
            + " MOVE: (" + c.getX() + " " + c.getY()
            + " " + c.getZ() + ") " + "\n");
    super.move(c);
    //model.notifyObserverTurn();
  }

}