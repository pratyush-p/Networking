package keypressTest;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class panel extends JPanel {
    public panel() {
        setBounds(0, 0, 1000, 450);
        setBackground(Color.BLUE);
        setLayout(new GridLayout(6, 2));

        // String k = "KEY";
        // getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.KEY_PRESSED, 0), k);
        // getActionMap().put(k, new AbstractAction() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         System.err.println("key pressed");
        //     }
        // });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("pressed");
            }
        });
    }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     System.err.println(e.getKeyCode());
    // }

    // @Override
    // public void keyTyped(KeyEvent e) {
    //     System.err.println(e.getKeyCode());

    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     System.err.println(e.getKeyCode());

    // }
}
