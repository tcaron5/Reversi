package view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.event.MouseInputAdapter;

import controller.ControllerFeatures;
import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * A GUI view for the game of Reversi, allowing users to click to play the game.
 * Should not be able to affect the model directly.
 */
public class SquareGUI extends AbstractGUI {

  private int squareLength;

  private Coordinate selectedSquare;

  //private boolean hintsOn;

  /**
   * Default constructor for GUI that takes in model.
   *
   * @param model model for the game.
   */
  public SquareGUI(ReadonlyReversiModel model) {
    super(model);

    this.model = Objects.requireNonNull(model); //cannot modify the view with readonly
    this.squareLength = 50;


    // Enable keyboard input for the JPanel
    setFocusable(true);
    requestFocusInWindow();

    //listener
    SquareGUI.ReversiMouseListener listener = new SquareGUI.ReversiMouseListener();
    this.addMouseListener(listener);
    SquareGUI.ReversiKeyListener keyListener = new SquareGUI.ReversiKeyListener();
    this.addKeyListener(keyListener);

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    // transform coord system?


    // Set the background color to blue
    Color customColor = new Color(52, 52, 52);
    //maybe make light or dark mode?
    setBackground(customColor);


    //placeholder code:
    Rectangle bounds = this.getBounds();


    int screenCenterX = bounds.width / 2;
    int screenCenterY = bounds.height / 2;

    //drawHexagon(g2d, screenCenterX, screenCenterY, hexRadius);
    drawSquareBoard(g2d, screenCenterX, screenCenterY);
  }


  @Override
  public Coordinate findCoordClicked(int mouseX, int mouseY) {
    for (Coordinate coordinate : model.getBoard().keySet()) {
      // Calculate screen coordinates based on the cubic coordinate offsets
      int squareCenterX = (int) coordinate.getX() * squareLength + (squareLength / 2);
      int squareCenterY = (int) coordinate.getY() * squareLength + (squareLength / 2);

      if (isPointInSquare(mouseX, mouseY, squareCenterX, squareCenterY)) {
        return coordinate;
      }
    }
    return null;
  }

  private boolean isPointInSquare(int x, int y, int squareCenterX, int squareCenterY) {
    int halfSide = squareLength / 2;

    int xMin = squareCenterX - halfSide;
    int xMax = squareCenterX + halfSide;
    int yMin = squareCenterY - halfSide;
    int yMax = squareCenterY + halfSide;

    return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
  }

  @Override
  public void addFeatures(ControllerFeatures features) {
    this.controller = features;
  }


  private void drawSquareBoard(Graphics g, int screenWidth, int screenHeight) {
    for (Coordinate coord : model.getBoard().keySet()) {
      int screenX = coord.getX() * squareLength;
      int screenY = coord.getY() * squareLength;

      drawSquare(g, screenX, screenY, squareLength, Color.GRAY);

      if (model.getBoard().get(coord).getOwner() == 1) {
        drawCircle(g, screenX + squareLength / 2, screenY + squareLength / 2,
                squareLength * 2 / 3, Color.BLACK);
      } else if (model.getBoard().get(coord).getOwner() == 2) {
        drawCircle(g, screenX + squareLength / 2, screenY + squareLength / 2,
                squareLength * 2 / 3, Color.WHITE);
      }
    }
  }

  private void drawCircle(Graphics g, int x, int y, int radius, Color color) {
    g.setColor(color);
    g.fillOval(x - (radius / 2), y - (radius / 2), radius, radius);
  }

  private void drawSquare(Graphics g, int x, int y, int squareSize, Color color) {
    g.setColor(color);
    g.fillRect(x, y, squareSize, squareSize);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, squareSize, squareSize);
  }


  private class ReversiMouseListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();

      Coordinate coordClicked = findCoordClicked(mouseX, mouseY);

      if (coordClicked != null) {
        int screenX = coordClicked.getX() * squareLength;
        int screenY = coordClicked.getY() * squareLength;
        if (model.getBoard().get(coordClicked).getOwner() == 0) {
          unhighlightSquare(getGraphics());
          deselectOrSelectSquare(coordClicked, screenX, screenY);
        }
      } else {
        // Unhighlights if clicked outside of board
        unhighlightSquare(getGraphics());
      }
    }

    private void deselectOrSelectSquare(Coordinate coordinate, int screenX, int screenY) {
      if (coordinate.equals(selectedSquare)) {
        drawSquare(getGraphics(), screenX, screenY, squareLength, Color.GRAY);
        selectedSquare = null;
      } else {
        drawSquare(getGraphics(), screenX, screenY, squareLength, Color.CYAN);
        selectedSquare = coordinate;

        showHints(getGraphics(), screenX, screenY);
      }
    }
  }

  private void showHints(Graphics g, int x, int y) {
    int numberToDisplay = controller.hintHelper(model, selectedSquare);
    if (numberToDisplay == -1) {
      return;
    }
    if (hintsOn) {
      drawNumber(getGraphics(), x, y, "" + numberToDisplay);
    }
  }

  private void drawNumber(Graphics g, int x, int y, String number) {
    g.setColor(Color.BLACK);
    g.drawString(number, x + squareLength / 2 - g.getFontMetrics().stringWidth(number) / 2,
            y + squareLength / 2);
  }

  private void unhighlightSquare(Graphics g) {
    if (selectedSquare != null) {
      int prevScreenX = selectedSquare.getX() * squareLength;
      int prevScreenY = selectedSquare.getY() * squareLength;
      drawSquare(g, prevScreenX, prevScreenY, squareLength, Color.GRAY);
    }
  }

  private class ReversiKeyListener extends KeyAdapter {
    @Override
    public void keyTyped(KeyEvent e) {

      if (e.getKeyChar() == 'p') {
        //System.out.println("Pass attempted.");
        if (controller != null) {
          controller.pass();
          selectedSquare = null;
          repaint();
        }
      }

      if (e.getKeyChar() == 'm') {
        if (selectedSquare == null) {
          //System.out.println("Please select a hexagon before attempting to move.");
          return;
        }

        //System.out.println("Move attempted at " + selectedHexagon + ".");
        if (controller != null) {
          controller.move(selectedSquare);
          selectedSquare = null;
          repaint();
        }
      }

      //SHOW HINTS
      if (e.getKeyChar() == 'h') {
        if (hintsOn) {
          hintsOn = false;
          if (selectedSquare != null) {
            int screenX = selectedSquare.getX() * squareLength;
            int screenY = selectedSquare.getY() * squareLength;
            drawSquare(getGraphics(),
                    screenX, screenY,
                    squareLength, Color.CYAN);
          }
        } else {
          hintsOn = true;
          if (selectedSquare != null) {
            int screenX = selectedSquare.getX() * squareLength;
            int screenY = selectedSquare.getY() * squareLength;
            drawSquare(getGraphics(),
                    screenX, screenY,
                    squareLength, Color.CYAN);
            showHints(getGraphics(), screenX, screenY);
          }
        }
      }


    }
  }
}
