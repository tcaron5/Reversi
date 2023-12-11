import javax.swing.JFrame;

//import model.SquareReversi;
import model.SquareReversi;
import provider.strategy.AsManyAsPossible;
import provider.StrategyAdapter;
import controller.ReversiController;
import model.HexagonReversi;
import model.Player;
import model.PlayerImpl;
import model.ReversiModel;
import strategy.AvoidCornerNeighborStrategy;
import strategy.CaptureMaxCellsThisMove;
import strategy.CheckCornersFirstStrategy;
import strategy.GoForCornersStrategy;
import strategy.MiniMaxStrategy;
import strategy.RandomValidMoveStrategy;
import view.AbstractGUI;
import view.HexagonGUI;
import view.SquareGUI;
import view.ViewInterface;

/**
 * This is the main runner for the Reversi game.
 */

public class Reversi {

  /**
   * The main method for reversi.
   *
   * @param args arguments.
   */
  public static void main(String[] args) {

    if (args.length != 3) {
      System.out.println("Usage: Reversi <gameType> <playerType1> <playerType2> \n"
              + "Possible game types: square, hexagon \n"
              + "Possible player types: human, minimax, avoidcornerneighbor, capturemaxcells, "
              + "checkcornersfirst, goforcorners, randomvalidmove");
      System.exit(1);
    }

    String gameType = args[0];
    String player1Type = args[1];
    String player2Type = args[2];

    Player player1 = createPlayer(player1Type, 1);
    Player player2 = createPlayer(player2Type, 2);

    ReversiModel model = createGameType(gameType);
    //start game
    model.startGame(4, 2);

    AbstractGUI g1;
    AbstractGUI g2;
    ViewInterface view1;
    ViewInterface view2;
    if ("square".equalsIgnoreCase(gameType)) {
      g1 = new SquareGUI(model);
      g2 = new SquareGUI(model);
      view1 = g1;
      view2 = g2;

    }
    else if ("hexagon".equalsIgnoreCase(gameType)) {
      g1 = new HexagonGUI(model);
      g2 = new HexagonGUI(model);
      view1 = g1;
      view2 = g2;

    }
    else {
      throw new IllegalArgumentException("Unknown game type: " + gameType);
    }

    ReversiController controller1 = new ReversiController(model, player1, view1);
    ReversiController controller2 = new ReversiController(model, player2, view2);

    view1.addFeatures(controller1);
    view2.addFeatures(controller2);

    // Create the main JFrame to display the game
    JFrame frame1 = new JFrame("Reversi Game Player 1");
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(500, 500);
    frame1.add(g1);
    frame1.setVisible(true);

    // Create the main JFrame to display the game
    JFrame frame2 = new JFrame("Reversi Game Player 2");
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.setSize(500, 500);
    frame2.add(g2);
    frame2.setVisible(true);
  }

  private static Player createPlayer(String playerType, int playerNumber) {
    switch (playerType.toLowerCase()) {
      case "human":
        return new PlayerImpl(null, playerNumber);
      case "minimax":
        return new PlayerImpl(new MiniMaxStrategy(5), playerNumber);
      case "avoidcornerneighbor":
        return new PlayerImpl(new AvoidCornerNeighborStrategy(), playerNumber);
      case "capturemaxcells":
        return new PlayerImpl(new CaptureMaxCellsThisMove(), playerNumber);
      case "checkcornersfirst":
        return new PlayerImpl(new CheckCornersFirstStrategy(), playerNumber);
      case "goforcorners":
        return new PlayerImpl(new GoForCornersStrategy(), playerNumber);
      case "randomvalidmove":
        return new PlayerImpl(new RandomValidMoveStrategy(), playerNumber);
      case "providerstrategy1":
        return new PlayerImpl(new StrategyAdapter(new AsManyAsPossible()), playerNumber);
      default:
        throw new IllegalArgumentException("Unknown player type: " + playerType);
    }

  }

  private static ReversiModel createGameType(String gameType) {
    switch (gameType.toLowerCase()) {
      case "hexagon":
        return new HexagonReversi();
      case "square":
        return new SquareReversi();
      default:
        throw new IllegalArgumentException("Unknown game type: " + gameType);
    }
  }


}