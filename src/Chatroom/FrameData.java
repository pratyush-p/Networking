package Chatroom;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

public class FrameData extends JFrame {
    ArrayList<Message> messageList;

    JScrollPane scrollPane;
    JList<String> messageDisplay, nameDisplay;
    DefaultListModel<String> messageModel, nameModel;
    JTextField typingArea;
    JButton enter, exit;

    public FrameData() {
        super("Chatroom");

        setSize(910,730);
        setResizable(false);
        setAlwaysOnTop(true);
        setLayout(null);

        messageModel = new DefaultListModel<>();
        messageModel.add(0, "test heheh");
        messageModel.add(1, "test2 heheh");
        messageModel.add(2, "test4 heheh");
        messageDisplay = new JList<String>(messageModel);
        messageDisplay.setFocusable(false);
        // messageDisplay.setBounds(20, 20, 800, 500);
        scrollPane = new JScrollPane(messageDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(20, 20, 600, 400);
        scrollPane.setVisible(true);

        nameModel = new DefaultListModel<>();
        nameModel.add(0, "test heheh");
        nameModel.add(1, "test2 heheh");
        nameModel.add(2, "test4 heheh");
        nameDisplay = new JList<String>(nameModel);
        nameDisplay.setFocusable(false);
        nameDisplay.setBounds(640, 20, 240, 400);

        typingArea = new JTextField();
        typingArea.setBounds(20, 440, 600, 240);

        enter = new JButton("Enter");
        enter.setBounds(640, 440, 240, 120);
        exit = new JButton("Exit");
        exit.setBounds(640, (440 + 120), 240, 120);

        add(scrollPane);
        add(nameDisplay);
        add(typingArea);
        add(enter);
        add(exit);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
