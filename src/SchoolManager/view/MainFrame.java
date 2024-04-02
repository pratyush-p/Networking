package SchoolManager.view;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import java.awt.*;

public class MainFrame extends JFrame {

    private List<JButton> buttonList;
    private GridLayout buttonGrid;
    private JPanel buttonPanel;

    public MainFrame() {
        super("School Manager");

        setBounds(0, 0, 1000,435);
        setResizable(false);
        setAlwaysOnTop(true);
        setLayout(null);

        buttonList = List.of(
            new JButton("Teacher"),
            new JButton("Student"),
            new JButton("Course"),
            new JButton("Section"),
            new JButton("File"),
            new JButton("Help")
        );

        buttonGrid = new GridLayout(6, 1);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 120, 400);
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
       
        buttonPanel.setLayout(buttonGrid);
        buttonList.forEach(button -> {
            // button.setSize(640, 480/6);
            // mainListModel.addElement(button.getName());
            buttonPanel.add(button);
        });

        add(buttonPanel);
        add(new TeacherFrame());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
