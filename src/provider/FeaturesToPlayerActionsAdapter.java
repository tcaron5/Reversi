package provider;

import java.util.Objects;

import provider.model.HexCoordinate;
import provider.player.PlayerActions;
import controller.ControllerFeatures;

/**
 * Adapts our player features into the provider's player actions.
 */
public class FeaturesToPlayerActionsAdapter implements PlayerActions {
  private final ControllerFeatures features;
  private final int sideLength;


  /**
   * Adapts our features into the provider's features.
   * @param features our player features that we pass to the adapter.
   */
  public FeaturesToPlayerActionsAdapter(ControllerFeatures features, int sideLength) {
    Objects.requireNonNull(features);
    this.features = features;
    this.sideLength = sideLength;
  }

  @Override
  public void playMove(HexCoordinate coordinate) {
    features.move(new CoordinateAdapter().convertToCubic(
            coordinate.getRow(), coordinate.getCol(), sideLength));

    System.out.println("sidelength: " + sideLength);
    System.out.println("Features adapter playMove: " + new CoordinateAdapter().convertToCubic(
            coordinate.getRow(), coordinate.getCol(), sideLength));
  }

  @Override
  public void pass() {
    features.pass();
  }

}
