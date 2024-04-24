package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import management.SqlManager;
import objects.Objects.Course;

public class CourseFrame extends JPanel {

    JLabel idLabel, courseLabel, typeLabel;
    JTextField idField, courseField, typeField;
    JButton add, remove;

    JList<String> mainList, radioList;
    DefaultListModel<String> listModel, radioModel;
    JScrollPane scroll;

    public CourseFrame() {
        //setLayout(new BorderLayout());
        setLayout(new GridLayout(6,2));
        setBounds(125, 10, 1000 - 130, 400);

        JTextField title = new JTextField("COURSE");
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
        courseLabel = new JLabel("Course Name");
        typeLabel = new JLabel("Type");
        // idLabel.setBounds(120, 30, 300, 20);

        idField = new JTextField();
        idField.setEditable(false);
        courseField = new JTextField();
        typeField = new JTextField();

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

        radioModel = new DefaultListModel<>();
        radioList = new JList<>(radioModel);
        radioModel.addElement("Academic");
        radioModel.addElement("KAP");
        radioModel.addElement("AP");

        scroll = new JScrollPane(mainList);
        scroll.setVisible(true);
        scroll.setWheelScrollingEnabled(true);

        add(scroll);

        add(idLabel);add(idField);
        add(courseLabel);add(courseField);
        add(typeLabel);add(radioList);
        add(add);
        add(remove);

        KeyAdapter a = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                remove.setText("Save Edits");
            }
        };

        courseField.addKeyListener(a);
        // typeField.addKeyListener(a);
        radioList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                remove.setText("Save Edits");
            }
        });

        if (!SqlManager.getInstance().refreshCourse().isEmpty()) {
            refreshList();
            refreshFrame(0);
        }

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SqlManager.getInstance().addCourse(new Course(courseField.getText(), getRadioInt()));
                refreshList();
                refreshFrame(0);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remove.getText().equals("Remove")) {
                    SqlManager.getInstance().removeCourse(Integer.parseInt(idField.getText()));
                    refreshList();
                    refreshFrame(0);    
                } else {
                    SqlManager.getInstance().editCourse(new Course(
                        Integer.parseInt(idField.getText()), 
                        courseField.getText(), 
                        getRadioInt()
                    ));
                    refreshList();
                    refreshFrame(0);
                }
            }
        });
    }

    public void refreshList() {
        List<Course> courseList = SqlManager.getInstance().refreshCourse();
        listModel.clear();
        for (int i = 0; i < courseList.size(); i++) {
            listModel.add(i, courseList.get(i).title);
        }
    }

    public void refreshFrame(int selected) {

        if (selected == -1) {
            courseField.setText("");
            radioList.setSelectedIndex(0);
            idField.setText("");
            return;
        }

        mainList.setSelectedIndex(selected);
        Course selection = SqlManager.getInstance().refreshCourse().get(selected);
        String title =  selection.title;
        int type =  selection.type;
        int id = selection.course_id;
        courseField.setText(title);
        radioList.setSelectedIndex(type - 1);
        idField.setText("" + id);
    }

    public int getRadioInt() {
        return radioList.getSelectedIndex() + 1;
    }
}
