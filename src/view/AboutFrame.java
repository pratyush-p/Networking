package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.Border;

public class AboutFrame extends JPanel {

    JLabel idLabel, firstNameLabel, lastNameLabel, scheduleLabel;
    JTextField idField, firstNameField, lastNameField, scheduleField;
    JButton add, remove;

    JList<String> mainList;
    DefaultListModel<String> listModel;
    JScrollPane scroll;

    public AboutFrame() {
        //setLayout(new BorderLayout());
        setLayout(new GridLayout(6,2));
        setBounds(125, 10, 1000 - 130, 400);

        JTextField title = new JTextField("ABOUT");
        title.setFont(new Font("arial", Font.BOLD, 30));
        title.setEditable(false);
        title.setSelectionColor(null);
        // title.setBounds(120, 10, 300, 20);
        add(title);

        JTextField empty = new JTextField();
        empty.setVisible(false);
        // add(empty);
        // add(button);
        idLabel = new JLabel("Created by: ");
        firstNameLabel = new JLabel("Pratyush Prakash");
        lastNameLabel = new JLabel("Version:");
        scheduleLabel = new JLabel("24.1.1");
        // idLabel.setBounds(120, 30, 300, 20);

        idField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        scheduleField = new JTextField();

        add = new JButton("Add");
        remove = new JButton("Remove");

        listModel = new DefaultListModel<>();
        mainList = new JList<>(listModel);

        listModel.addElement("Test");
        listModel.addElement("Test2");
        listModel.addElement("Test3");
        listModel.addElement("Test4");
        listModel.addElement("Test5");
        listModel.addElement("Test6");

        scroll = new JScrollPane(mainList);
        scroll.setVisible(true);
        scroll.setWheelScrollingEnabled(true);

        add(scroll);

        add(idLabel);add(idField);
        add(firstNameLabel);add(firstNameField);
        add(lastNameLabel);add(lastNameField);
        add(scheduleLabel);add(scheduleField);
        add(add);
        add(remove);

        scroll.setVisible(false);
        idField.setVisible(false);
        firstNameField.setVisible(false);
        lastNameField.setVisible(false);
        scheduleField.setVisible(false);
        add.setVisible(false);
        remove.setVisible(false);
    }
}
