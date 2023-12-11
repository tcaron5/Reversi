package provider.view;

import java.awt.geom.Path2D;

/**
 * A subclass of Path2D.Double to represent a line-path of a single hexagon.
 */
public class Hexagon extends Path2D.Double {

  /**
   * Constructs a Hexagon.
   *
   * @param centerX - centered X coordinate
   * @param centerY - centered Y coordinate
   * @param size    - side length
   */
  public Hexagon(double centerX, double centerY, double size) {
    super();

    double angleStep = Math.PI / 3;
    double startingAngle = Math.PI / 2;

    this.moveTo(
            centerX + size * Math.cos(startingAngle),
            centerY - size * Math.sin(startingAngle));

    for (int i = 0; i < 6; i++) {
      double angle = startingAngle - angleStep * i;
      this.lineTo(
              centerX + size * Math.cos(angle),
              centerY - size * Math.sin(angle));
    }

    this.closePath();
  }
}
