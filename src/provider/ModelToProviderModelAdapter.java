package provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Coordinate;
import provider.model.CellState;
import provider.model.GameEvents;
import provider.model.HexCoordinate;
import provider.model.ReversiModel;
import provider.player.PlayerColor;
import model.Cell;
import model.CubicCoordinate;


/**
 * Adapts our Reversi model implementation to fit provider model interface.
 */
public class ModelToProviderModelAdapter implements ReversiModel {
  private final model.ReversiModel model;
  private final int sidelength;
  private final int p;

  /**
   * Allows our model implementation to be used with the provider's model interface.
   * @param sidelength is the sidelength of the board
   * @param p players in the game
   * @param model our model implementation that we pass to the adapter.
   */
  public ModelToProviderModelAdapter(int sidelength, int p, model.ReversiModel model) {
    Objects.requireNonNull(model);
    this.model = model;
    this.sidelength = sidelength;
    this.p = p;
  }

  @Override
  public CellState getCell(HexCoordinate coordinate) {
    CubicCoordinate converted =
            new CoordinateAdapter().convertToCubic(coordinate.getRow(),
                    coordinate.getCol(), sidelength);

    Cell cell = model.getBoard().get(converted);
    if (cell.getOwner() == 1) {
      return CellState.BLACK;
    }
    else if (cell.getOwner() == 2) {
      return CellState.WHITE;
    }
    return CellState.EMPTY;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return null;
  }

  @Override
  public boolean isMoveAllowable(HexCoordinate coordinate, PlayerColor playerColor) {
    return false;
  }

  @Override
  public boolean noMovesLeft() {
    return false;
  }

  @Override
  public int getBoardSize() {
    return model.getSideLength();
  }

  @Override
  public List<HexCoordinate> getLegalMoves(PlayerColor p) {
    List<Coordinate> list =  model.getAvailableMoves(convertColorToInt(p));
    List<HexCoordinate> legalMovesHex = new ArrayList<>();

    for (Coordinate c : list) {
      legalMovesHex.add(new CoordinateAdapter().convertToHex(c, sidelength));
    }
    return legalMovesHex;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getScore(PlayerColor p) {
    return 0;
  }

  @Override
  public CellState[][] getBoardCopy() {

    return new CellState[0][];
  }

  @Override
  public int getSimulatedScore(HexCoordinate c, PlayerColor p) {

    model.ReversiModel clone = model.clone();
    clone.flipCell(new CoordinateAdapter().convertToCubic(c.getRow(), c.getCol(), sidelength),
            convertColorToInt(p));
    return model.getScore(convertColorToInt(p));
  }

  @Override
  public void move(HexCoordinate coordinate, PlayerColor p) {
    CubicCoordinate converted =
            new CoordinateAdapter().convertToCubic(coordinate.getRow(),
                    coordinate.getCol(), sidelength);
    model.flipCell(converted, convertColorToInt(p));
  }

  protected int convertColorToInt(PlayerColor p) {
    if (p.equals(PlayerColor.BLACK)) {
      return 1;
    }
    else {
      return 2;
    }
  }

  @Override
  public void pass() {
    model.passMove(model.getCurrentTurn());
  }

  @Override
  public void addGameEventListeners(PlayerColor c, GameEvents g) {

    //game events is the providers observer
    //create observer adapter
    model.subscribe(new ProviderObserverToObserverAdapter(g));
  }

  @Override
  public void startGame() {
    model.startGame(sidelength, p);

  }
}
