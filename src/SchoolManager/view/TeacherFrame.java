package SchoolManager.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class TeacherFrame extends JPanel {
    public TeacherFrame() {
        setLayout(new BorderLayout());
        setBounds(120, 0, 1000 - 120, 400);

        // JButton button = new JButton("TOOL_TIP_TEXT_KEY");

        JTextField title = new JTextField("TEACHER");
        title.setFont(new Font("arial", Font.BOLD, 35));
        // title.setEnabled(false);
        title.setEditable(false);
        // title.setAlignmentX(LEFT_ALIGNMENT);
        // button.setBounds(0, 0, 500, 500);
        add(title, BorderLayout.NORTH);
        // add(button);
    }
}
