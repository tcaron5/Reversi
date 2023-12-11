//package cs3500.view;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.InputEvent;
//import java.awt.event.KeyEvent;
//
//import javax.swing.JMenuBar;
//import javax.swing.JRadioButtonMenuItem;
//import javax.swing.JMenu;
//import javax.swing.ButtonGroup;
//import javax.swing.KeyStroke;
//import javax.swing.AbstractAction;
//
//import cs3500.player.HintsLevel;
//import cs3500.player.PlayerActions;
//
///**
// * An extension of JMenuBar to represent the bar of a Reversi game,
// * containing one menu that allows users to select hints.
// */
//public class HintsMenu extends JMenuBar implements Menu {
//
//  private final JRadioButtonMenuItem none;
//  private final JRadioButtonMenuItem possible;
//  private final JRadioButtonMenuItem best;
//
//  /**
//   * Constructs a HintsMenu object.
//   */
//  public HintsMenu() {
//    JMenuBar menuBar = new JMenuBar();
//    JMenu menu = new JMenu("Hints");
//    menu.setMnemonic(KeyEvent.VK_H);
//    ButtonGroup group = new ButtonGroup();
//    none = new JRadioButtonMenuItem("Don't show hints.");
//    none.setAccelerator(KeyStroke.getKeyStroke(
//            KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
//    group.add(none);
//    menu.add(none);
//    none.setSelected(true);
//    possible = new JRadioButtonMenuItem("Show possible moves.");
//    possible.setAccelerator(KeyStroke.getKeyStroke(
//            KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK));
//    group.add(possible);
//    menu.add(possible);
//    best = new JRadioButtonMenuItem("Show best move.");
//    best.setAccelerator(KeyStroke.getKeyStroke(
//            KeyEvent.VK_3, InputEvent.ALT_DOWN_MASK));
//    group.add(best);
//    menu.add(best);
//    menuBar.add(menu);
//    this.add(menuBar);
//  }
//
//  @Override
//  public void addPlayerActions(PlayerActions p) {
//    none.addActionListener(new AbstractAction() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        p.requestHints(HintsLevel.NONE);
//      }
//    });
//
//    possible.addActionListener(new AbstractAction() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        p.requestHints(HintsLevel.ALL_MOVES);
//      }
//    });
//
//    best.addActionListener(new AbstractAction() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        p.requestHints(HintsLevel.BEST_MOVE);
//      }
//    });
//  }
//
//
//}
