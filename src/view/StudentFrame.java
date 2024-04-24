package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import management.SqlManager;
import objects.Objects.Section;
import objects.Objects.Student;


public class StudentFrame extends JPanel {

    JLabel idLabel, firstNameLabel, lastNameLabel, scheduleLabel;
    JTextField idField, firstNameField, lastNameField, scheduleField;
    JButton add, remove;

    JList<String> mainList, scheduleList;
    DefaultListModel<String> listModel, scheduleModel;
    JScrollPane scroll;

    public StudentFrame() {
        //setLayout(new BorderLayout());
        setLayout(new GridLayout(6,2));
        setBounds(125, 10, 1000 - 130, 400);

        JTextField title = new JTextField("STUDENT");
        title.setFont(new Font("arial", Font.BOLD, 30));
        title.setEditable(false);
        title.setSelectionColor(null);
        // title.setBounds(120, 10, 300, 20);
        add(title);

        JTextField empty = new JTextField();
        empty.setVisible(false);
        // add(empty);
        // add(button);
        idLabel = new JLabel("ID");
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        scheduleLabel = new JLabel("Schedule");
        // idLabel.setBounds(120, 30, 300, 20);

        idField = new JTextField();
        idField.setEditable(false);
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        scheduleField = new JTextField();

        add = new JButton("Add");
        remove = new JButton("Remove");

        listModel = new DefaultListModel<>();
        mainList = new JList<>(listModel);
        mainList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshFrame(mainList.getSelectedIndex());
                remove.setText("Remove");
            }
        });

        scheduleModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleModel);
        scheduleList.setEnabled(false);

        scroll = new JScrollPane(mainList);
        scroll.setVisible(true);
        scroll.setWheelScrollingEnabled(true);

        add(scroll);

        add(idLabel);add(idField);
        add(firstNameLabel);add(firstNameField);
        add(lastNameLabel);add(lastNameField);
        add(scheduleLabel);add(scheduleList);
        add(add);
        add(remove);

        KeyAdapter a = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                remove.setText("Save Edits");
            }
        };

        firstNameField.addKeyListener(a);
        lastNameField.addKeyListener(a);

        if (!SqlManager.getInstance().refreshStudent().isEmpty()) {
            refreshList();
            refreshFrame(0);
        }

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SqlManager.getInstance().addStudent(new Student(firstNameField.getText(), lastNameField.getText()));
                refreshList();
                refreshFrame(0);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remove.getText().equals("Remove")) {
                    SqlManager.getInstance().removeStudent(Integer.parseInt(idField.getText()));
                    refreshList();
                    refreshFrame(0);    
                } else {
                    SqlManager.getInstance().editStudent(new Student(
                        Integer.parseInt(idField.getText()), 
                        firstNameField.getText(), 
                        lastNameField.getText()
                    ));
                    refreshList();
                    refreshFrame(0);
                }
            }
        });
    }

    public void refreshList() {
        List<Student> studentList = SqlManager.getInstance().refreshStudent();
        listModel.clear();
        for (int i = 0; i < studentList.size(); i++) {
            listModel.add(i, studentList.get(i).last_name + ", " + studentList.get(i).first_name);
        }
    }

    public void refreshFrame(int selected) {

        if (selected == -1) {
            firstNameField.setText("");
            lastNameField.setText("");
            idField.setText("");
            refreshSchedule(null);
            return;
        }

        mainList.setSelectedIndex(selected);
        Student selection = SqlManager.getInstance().refreshStudent().get(selected);
        String first =  selection.first_name;
        String last =  selection.last_name;
        int id = selection.student_id;
        firstNameField.setText(first);
        lastNameField.setText(last);
        idField.setText("" + id);
        refreshSchedule(selection);
    }

    public void refreshSchedule(Student selection) {
        scheduleModel.clear();
        if (selection == null) {
            return;
        }
        List<Section> sectionList = SqlManager.getInstance().studentSchedule(selection);
        for (Section s : sectionList) {
            scheduleModel.addElement("Section ID: " + s.section_id + " - Course: " + s.course.title + " - Teacher: " + s.teacher.last_name);
        }
    }
}
