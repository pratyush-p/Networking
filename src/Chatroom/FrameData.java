package Chatroom;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
    String myName;

    public static ArrayList<String> nameList = new ArrayList<>();

    public FrameData(String name, ObjectOutputStream os) {
        super("Chatroom");

        setSize(910,730);
        setResizable(false);
        setAlwaysOnTop(true);
        setLayout(null);

        messageModel = new DefaultListModel<>();
        messageDisplay = new JList<String>(messageModel);
        messageDisplay.setFocusable(false);
        // messageDisplay.setBounds(20, 20, 800, 500);
        scrollPane = new JScrollPane(messageDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(20, 20, 600, 400);
        scrollPane.setVisible(true);

        nameModel = new DefaultListModel<>();
        nameDisplay = new JList<String>(nameModel);
        nameDisplay.setFocusable(false);
        nameDisplay.setBounds(640, 20, 240, 400);

        typingArea = new JTextField();
        typingArea.setBounds(20, 440, 600, 240);

        enter = new JButton("Enter");
        enter.setBounds(640, 440, 240, 120);
        enter.addActionListener((l) -> {
            try {
                if (!typingArea.getText().equals("")) {
                    os.writeObject(new Message(typingArea.getText(), name));
                }
                typingArea.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exit = new JButton("Exit");
        exit.setBounds(640, (440 + 120), 240, 120);
        exit.addActionListener((l) -> {
            System.exit(0);
        });

        add(scrollPane);
        add(nameDisplay);
        add(typingArea);
        add(enter);
        add(exit);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        nameList.add(name);
        this.myName = name;
        try {
            os.writeObject(new Message("", name));

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String preMade : nameList) {
            System.err.println("premade name list rn: " + preMade);
            // nameModel.addElement(preMade);
            try {
                os.writeObject(new Message("", preMade));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void newMessage(Message m, ObjectOutputStream os) {
        if (m.text.equals("")) {
            for (int i = 0; i < nameModel.getSize(); i++) {
                if (m.name.equals(nameModel.get(i))) {
                    return;
                }
            }
            System.err.println("added name to frame data");
            nameModel.addElement(m.name);
            nameList.add(m.name);
            try {
                os.writeObject(new Message("", this.myName));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            messageModel.addElement(m.name + ": " + m.text);
        }
    }
}
