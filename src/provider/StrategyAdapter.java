package provider;

import provider.model.HexCoordinate;
import provider.player.PlayerColor;
import model.CubicCoordinate;
import model.ReadonlyReversiModel;
import strategy.ReversiStrategy;

/**
 * Turns provider strategy implementations to work with our code.
 */
public class StrategyAdapter implements ReversiStrategy {

  private final provider.strategy.ReversiStrategy providerStrategy; //provider strategy
  //private final ReversiModel reversiModel; //our model

  public StrategyAdapter(provider.strategy.ReversiStrategy strategy) {
    this.providerStrategy = strategy;
    //this.reversiModel = reversiModel;
  }

  @Override
  public CubicCoordinate chooseCoord(ReadonlyReversiModel model, int player) {
    HexCoordinate coord =
            providerStrategy.chooseMove(new
                            ReadOnlyModelToProviderReadOnlyAdapter(model.getSideLength(),
                            model),
                    intToPlayerColor(player));
    return new CoordinateAdapter().convertToCubic(coord.getRow(),
            coord.getCol(), model.getSideLength());
  }

  protected PlayerColor intToPlayerColor(int player) {
    if (player == 1) {
      return PlayerColor.BLACK;
    }
    else {
      return PlayerColor.WHITE;
    }
  }
}
