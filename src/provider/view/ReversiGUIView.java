package provider.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import provider.player.PlayerActions;
import provider.model.HexCoordinate;
import provider.model.ReadonlyReversiModel;

/**
 * An implementation of a GUI-based view of a Reversi game.
 */
public class ReversiGUIView extends JFrame implements ReversiView {

  private final ReversiPanel panel;
  //private final HintsMenu hintsMenu;

  /**
   * Constructs a Reversi GUI view.
   *
   * @param model ReversiModel
   */
  public ReversiGUIView(ReadonlyReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = new ReversiPanel(model);
    //hintsMenu = new HintsMenu();
    this.add(panel);
    //this.setJMenuBar(hintsMenu);


    this.pack();
  }

  @Override
  public void addPlayerActions(PlayerActions p) {
    this.panel.addPlayerActions(p);
    //this.hintsMenu.addPlayerActions(p);
  }

  @Override
  public void refreshView() {
    this.panel.refreshView();
  }

  @Override
  public void showMessage(String message) {

    JOptionPane.showMessageDialog(this.panel, message);
  }

  @Override
  public void highlightMoves(List<HexCoordinate> cells) {

    this.panel.highlightMoves(cells);
  }

  @Override
  public void updateWindowTitle(String newTitle) {
    this.setTitle(newTitle);
  }
}
