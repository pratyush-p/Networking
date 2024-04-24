package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import objects.Objects.Course;
import objects.Objects.Section;
import objects.Objects.Student;
import objects.Objects.Teacher;
import util.RosterFrame;
import util.Util;

public class SectionFrame extends JPanel {

    JLabel idLabel, courseLabel, teacherLabel, rosterLabel;
    JTextField idField, courseField, teacherField, rosterField;
    JButton add, remove;

    JList<String> mainList, courseList, teacherList;
    JPanel roster;
    DefaultListModel<String> listModel, courseModel, teacherModel;
    JScrollPane scroll, scroll2, scroll3, scroll4;

    public SectionFrame() {
        //setLayout(new BorderLayout());
        setLayout(new GridLayout(6,2));
        setBounds(125, 10, 1000 - 130, 400);

        JTextField title = new JTextField("SECTION");
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
        courseLabel = new JLabel("Courses");
        teacherLabel = new JLabel("Teachers");
        rosterLabel = new JLabel("Student Roster");
        // idLabel.setBounds(120, 30, 300, 20);

        idField = new JTextField();
        idField.setEditable(false);
        courseField = new JTextField();
        teacherField = new JTextField();
        rosterField = new JTextField();

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

        courseModel = new DefaultListModel<>();
        courseList = new JList<>(courseModel);

        teacherModel = new DefaultListModel<>();
        teacherList = new JList<>(teacherModel);

        roster = new JPanel(new GridLayout(0, 1));
        scroll = new JScrollPane(mainList);
        scroll.setVisible(true);
        scroll.setWheelScrollingEnabled(true);

        scroll2 = new JScrollPane(courseList);
        scroll2.setVisible(true);
        scroll2.setWheelScrollingEnabled(true);

        scroll3 = new JScrollPane(teacherList);
        scroll3.setVisible(true);
        scroll3.setWheelScrollingEnabled(true);

        scroll4 = new JScrollPane(roster);
        scroll4.setVisible(true);
        scroll4.setWheelScrollingEnabled(true);

        add(scroll);

        add(idLabel);add(idField);
        add(courseLabel);add(scroll2);
        add(teacherLabel);add(scroll3);
        add(rosterLabel);add(scroll4);
        add(add);
        add(remove);

        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                remove.setText("Save Edits");
            }
        };

        courseList.addListSelectionListener(listener);
        teacherList.addListSelectionListener(listener);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                refreshItemLists();
                refreshList();
                refreshFrame(-1);
            }

            @Override
            public void focusLost(FocusEvent e) {
                
            }
        });

        if (!SqlManager.getInstance().refreshSection().isEmpty()) {
            refreshList();
            refreshFrame(0);
        }

        refreshItemLists();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SqlManager.getInstance().addSection(new Section(
                    SqlManager.getInstance().findCourse(Util.lastID(courseList.getSelectedValue())), 
                    SqlManager.getInstance().findTeacher(Util.lastID(teacherList.getSelectedValue()))
                ));
                refreshList();
                refreshFrame(0);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remove.getText().equals("Remove")) {
                    SqlManager.getInstance().removeSection(Integer.parseInt(idField.getText()));
                    refreshList();
                    refreshFrame(0);    
                } else {
                    SqlManager.getInstance().editSection(new Section(
                        Integer.parseInt(idField.getText()), 
                        SqlManager.getInstance().findCourse(Util.lastID(courseList.getSelectedValue())), 
                        SqlManager.getInstance().findTeacher(Util.lastID(teacherList.getSelectedValue()))
                    ));
                    refreshList();
                    refreshFrame(0);
                }
            }
        });
    }

    public void refreshList() {
        List<Section> sectionList = SqlManager.getInstance().refreshSection();
        listModel.clear();
        for (int i = 0; i < sectionList.size(); i++) {
            listModel.add(i, sectionList.get(i).course.title + " - " + sectionList.get(i).teacher.last_name);
        }
    }

    public void refreshItemLists() {
        List<Course> allCourses =  SqlManager.getInstance().refreshCourse();
        List<Teacher> allTeachers = SqlManager.getInstance().refreshTeacher();
        courseModel.clear();
        teacherModel.clear();

        if (allCourses == null || allTeachers == null) {
            return;
        }

        for (Course c : allCourses) {
            courseModel.addElement(c.title + " - " + c.course_id);
        }

        for (Teacher t : allTeachers) {
            teacherModel.addElement(t.last_name + " - " + t.teacher_id);
        }


    }

    public void refreshFrame(int selected) {

        if (selected == -1) {
            idField.setText("");
            roster.removeAll();
            return;
        }

        mainList.setSelectedIndex(selected);
        Section selection = SqlManager.getInstance().refreshSection().get(selected);
        List<Course> allCourses =  SqlManager.getInstance().refreshCourse();
        List<Teacher> allTeachers = SqlManager.getInstance().refreshTeacher();
        List<Student> allStudents = SqlManager.getInstance().refreshStudent();
        Course selectedCourse;
        Teacher selectedTeacher;
        int id = selection.section_id;
        idField.setText("" + id);

        courseModel.clear();
        teacherModel.clear();

        for (Course c : allCourses) {
            courseModel.addElement(c.title + " - " + c.course_id);
            if (c.equals(selection.course)) {
                selectedCourse = c;
                courseList.setSelectedValue(selectedCourse.title + " - " + selectedCourse.course_id, true);
            }
        }

        for (Teacher t : allTeachers) {
            teacherModel.addElement(t.last_name + " - " + t.teacher_id);
            if (t.equals(selection.teacher)) {
                selectedTeacher = t;
                teacherList.setSelectedValue(selectedTeacher.last_name + " - " + selectedTeacher.teacher_id, true);
            }
        }

        ((GridLayout) roster.getLayout()).setRows(allStudents.size());
        roster.removeAll();

        for (Student s : allStudents) {
            roster.add(new RosterFrame(s, selection));
        }

        remove.setText("Remove");
    }
}
