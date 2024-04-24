package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import objects.Objects.Teacher;

public class TeacherFrame extends JPanel {

    JLabel idLabel, firstNameLabel, lastNameLabel, sectionsLabel;
    JTextField idField, firstNameField, lastNameField, sectionsField;
    JButton add, remove;

    JList<String> mainList, sectionsList;
    DefaultListModel<String> listModel, sectionsModel;
    JScrollPane scroll, scroll2;

    public TeacherFrame() {
        setLayout(new GridLayout(6,2));
        setBounds(125, 10, 1000 - 130, 400);
        JTextField title = new JTextField("TEACHER");
        title.setFont(new Font("arial", Font.BOLD, 30));
        title.setEditable(false);
        title.setSelectionColor(null);
        add(title);

        JTextField empty = new JTextField();
        empty.setVisible(false);
        idLabel = new JLabel("ID");
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        sectionsLabel = new JLabel("Sections Taught");

        idField = new JTextField();
        idField.setEditable(false);
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        sectionsField = new JTextField();

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

        sectionsModel = new DefaultListModel<>();
        sectionsList = new JList<>(sectionsModel);
        sectionsList.setEnabled(false);

        scroll = new JScrollPane(mainList);
        scroll.setVisible(true);
        scroll.setWheelScrollingEnabled(true);

        scroll2 = new JScrollPane(sectionsList);
        scroll2.setVisible(true);
        scroll2.setWheelScrollingEnabled(true);

        add(scroll);

        add(idLabel);add(idField);
        add(firstNameLabel);add(firstNameField);
        add(lastNameLabel);add(lastNameField);
        add(sectionsLabel);add(scroll2);
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

        if (!SqlManager.getInstance().refreshTeacher().isEmpty()) {
            refreshList();
            refreshFrame(-1);
        }

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SqlManager.getInstance().addTeacher(new Teacher(firstNameField.getText(), lastNameField.getText()));
                refreshList();
                refreshFrame(0);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remove.getText().equals("Remove")) {
                    SqlManager.getInstance().removeTeacher(Integer.parseInt(idField.getText()));
                    refreshList();
                    refreshFrame(0);    
                } else {
                    SqlManager.getInstance().editTeacher(new Teacher(
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
        List<Teacher> teacherList = SqlManager.getInstance().refreshTeacher();
        listModel.clear();

        if (teacherList ==null) {
            return;
        }

        for (int i = 0; i < teacherList.size(); i++) {
            listModel.add(i, teacherList.get(i).last_name + ", " + teacherList.get(i).first_name);
        }
    }

    public void refreshFrame(int selected) {

        if (selected == -1) {
            firstNameField.setText("");
            lastNameField.setText("");
            idField.setText("");
            refreshSections(null);
            return;
        }

        mainList.setSelectedIndex(selected);
        Teacher selection = SqlManager.getInstance().refreshTeacher().get(selected);
        String first =  selection.first_name;
        String last =  selection.last_name;
        int id = selection.teacher_id;
        firstNameField.setText(first);
        lastNameField.setText(last);
        idField.setText("" + id);
        refreshSections(selection);
    }

    public void refreshSections(Teacher selection) {
        sectionsModel.clear();
        if (selection == null) {
            return;
        }
        for (Section s : SqlManager.getInstance().sectionList) {
            if (s.teacher.equals(selection)) {
                sectionsModel.addElement("Section ID: " + s.section_id + " - Course: " + s.course.title);
            }
        }
    }
}
