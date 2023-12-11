package provider.view;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import provider.model.CellState;
import provider.model.HexCoordinate;
import provider.model.ReadonlyReversiModel;
import provider.player.PlayerActions;


/**
 * This class acts as the main Panel for a GUI-based Reversi game,
 * containing the drawing of the game board.
 */
public class ReversiPanel extends JPanel implements ReversiView {

  private final ReadonlyReversiModel model;
  private final static int HEX_SIZE = 20;
  private final static double HORIZ_OFFSET = HEX_SIZE * Math.sqrt(3);
  private final static double VERT_OFFSET = HEX_SIZE * (3. / 2.);
  private final static double CIRCLE_RADIUS = 10;
  private final Map<HexCoordinate, Point> hexMap = new HashMap<>();
  private List<HexCoordinate> highlightedMoves = new ArrayList<>();

  /**
   * Constructs a ReversiPanel.
   *
   * @param model ReversiModel
   */
  public ReversiPanel(ReadonlyReversiModel model) {
    this.model = Objects.requireNonNull(model);
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  @Override
  public void refreshView() {
    this.repaint();
  }

  @Override
  public void showMessage(String message) {
    // panel parent handles messages
  }

  @Override
  public void updateWindowTitle(String newTitle) {
    // panel parent handles window title
  }

  @Override
  public void highlightMoves(List<HexCoordinate> cells) {
    this.highlightedMoves.clear();
    this.highlightedMoves.addAll(cells);
    this.repaint();
  }

  @Override
  public void addPlayerActions(PlayerActions player) {
    this.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        this.mouseDragged(e);
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();
        for (HexCoordinate c : ReversiPanel.this.hexMap.keySet()) {
          Point p1 = ReversiPanel.this.hexMap.get(c);
          if (p1.distance(p) <= HEX_SIZE) {
            System.out.println("Row: " + c.getRow() + " Col: " + c.getCol());

            player.playMove(c);
            ReversiPanel.this.repaint();
          }
        }
      }
    });

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'p') {
          player.pass();
          ReversiPanel.this.repaint();
        }
      }
    });
  }

  @Override
  public final Dimension getPreferredSize() {
    Dimension d = super.getPreferredSize();
    Dimension prefSize;
    Component c = getParent();
    if (c == null) {
      prefSize = new Dimension((int) d.getWidth(), (int) d.getHeight());
    } else if (c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
      prefSize = c.getSize();
    } else {
      prefSize = d;
    }
    int w = (int) prefSize.getWidth();
    int h = (int) prefSize.getHeight();

    int s = (Math.min(w, h));
    int minS = 500;
    if (s > minS) {
      return new Dimension(s, s);
    } else {
      return new Dimension(minS, minS);
    }
  }

  private void drawHex(double middleX, double middleY,
                       Graphics2D g2d, CellState state, boolean highlighted) {
    Hexagon h = new Hexagon(middleX, middleY, HEX_SIZE);
    if (highlighted) {
      g2d.setColor(Color.GREEN);
    } else {
      g2d.setColor(Color.LIGHT_GRAY);
    }
    g2d.fill(h);
    g2d.setColor(Color.BLACK);
    g2d.draw(h);
    this.drawTile(middleX, middleY, g2d, state);
  }

  private void drawTile(double middleX, double middleY, Graphics2D g2d, CellState state) {
    if (state != null && state.toColor() != null) {
      AffineTransform oldTransform = g2d.getTransform();
      g2d.translate(middleX, middleY);
      g2d.setColor(state.toColor());
      Shape circle = new Ellipse2D.Double(
              -CIRCLE_RADIUS,
              -CIRCLE_RADIUS,
              2 * CIRCLE_RADIUS,
              2 * CIRCLE_RADIUS);
      g2d.fill(circle);
      g2d.setTransform(oldTransform);
    }
  }

  private double getWidthSkip(int numCellsInRow) {
    Dimension size = this.getPreferredSize();
    double width = size.getWidth();
    double cellsTotalWidth = numCellsInRow * HORIZ_OFFSET;
    return HORIZ_OFFSET / 2 + (width - cellsTotalWidth) / 2;
  }

  private double getHeightSkip(int numRowsInGrid) {
    Dimension size = this.getPreferredSize();
    double height = size.getHeight();
    double cellsTotalHeight = numRowsInGrid * VERT_OFFSET;
    return VERT_OFFSET / 2 + (height - cellsTotalHeight) / 2;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();


    int boardSize = model.getBoardSize();
    int boardLength = boardSize * 2 - 1;

    double currentY = this.getHeightSkip(boardLength);
    // Render the top half of the board
    for (int row = 0; row < boardSize; row++) {
      int numBlankTiles = Math.abs(boardSize - row - 1);
      int numTiles = boardLength - numBlankTiles;
      double currentX = this.getWidthSkip(numTiles); //starting x-position for this row
      for (int col = numBlankTiles; col < boardLength; col++) {
        HexCoordinate c = new HexCoordinate(row, col);
        this.hexMap.put(c, new Point((int) currentX, (int) currentY));
        boolean highlighted = this.highlightedMoves.contains(c);
        this.drawHex(currentX, currentY, g2d, model.getCell(c), highlighted);
        currentX += HORIZ_OFFSET;
      }
      currentY += VERT_OFFSET;
    }

    //Render the bottom half of the board
    for (int row = boardSize; row < boardLength; row++) {
      int numBlankTiles = Math.abs(boardSize - row - 1);
      int numTiles = boardLength - numBlankTiles;
      double currentX = this.getWidthSkip(numTiles); //starting x-position for this row
      for (int col = 0; col < boardLength - numBlankTiles; col++) {
        HexCoordinate c = new HexCoordinate(row, col);
        this.hexMap.put(c, new Point((int) currentX, (int) currentY));
        boolean highlighted = this.highlightedMoves.contains(c);
        this.drawHex(currentX, currentY, g2d, model.getCell(c), highlighted);
        currentX += HORIZ_OFFSET;
      }
      currentY += VERT_OFFSET;
    }
  }
}
