package keypressTest;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class frame extends JFrame {

    panel bruh;

    public frame() {
        setBounds(0, 0, 1000,450);
        setResizable(false);
        setAlwaysOnTop(true);
        setLayout(null);

        bruh = new panel();
        add(bruh);

        // addKeyListener(bruh);
        bruh.setFocusable(true);
        // bruh.requestFocus();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
