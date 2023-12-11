package provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Coordinate;
import provider.model.CellState;
import provider.model.HexCoordinate;
import provider.model.ReadonlyReversiModel;
import provider.player.PlayerColor;
import model.Cell;
import model.CubicCoordinate;

/**
 * Adapts our Read Only Reversi model implementation to fit provider model read only interface.
 */
public class ReadOnlyModelToProviderReadOnlyAdapter implements ReadonlyReversiModel {
  private final model.ReadonlyReversiModel readOnlyModel;
  private final int sidelength;

  /**
   * Allows our read only implementation to be used with the provider's read only interface.
   * @param sidelength is the sidelength of the board
   * @param readOnlyModel our model implementation that we pass to the adapter.
   */
  public ReadOnlyModelToProviderReadOnlyAdapter(int sidelength,
                                                model.ReadonlyReversiModel readOnlyModel) {
    Objects.requireNonNull(readOnlyModel);
    this.readOnlyModel = readOnlyModel;
    this.sidelength = sidelength;
  }

  @Override
  public CellState getCell(HexCoordinate coordinate) {
    CubicCoordinate converted =
            new CoordinateAdapter().convertToCubic(coordinate.getRow(),
                    coordinate.getCol(), sidelength);

    Cell cell = readOnlyModel.getBoard().get(converted);
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
    return readOnlyModel.getSideLength();
  }

  @Override
  public List<HexCoordinate> getLegalMoves(PlayerColor p) {
    List<Coordinate> list =  readOnlyModel.getAvailableMoves(convertColorToInt(p));
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

    model.ReversiModel clone = readOnlyModel.clone();
    clone.flipCell(new CoordinateAdapter().convertToCubic(c.getRow(), c.getCol(), sidelength),
            convertColorToInt(p));
    return readOnlyModel.getScore(convertColorToInt(p));
  }


  protected int convertColorToInt(PlayerColor p) {
    if (p.equals(PlayerColor.BLACK)) {
      return 1;
    }
    else {
      return 2;
    }
  }



}
