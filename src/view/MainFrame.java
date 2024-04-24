package view;

import java.util.List;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import management.SqlManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame extends JFrame {

    private List<JButton> buttonList;
    private GridLayout buttonGrid;
    private JPanel buttonPanel;

    private List<JPanel> frameList;
    private TeacherFrame teacherFrame;
    private StudentFrame studentFrame;
    private CourseFrame courseFrame;
    private SectionFrame sectionFrame;
    private AboutFrame aboutFrame;

    public MainFrame() {
        super("School Manager");

        setBounds(0, 0, 1000,450);
        setResizable(false);
        setAlwaysOnTop(true);
        setLayout(null);

        buttonList = List.of(
            new JButton("Teacher"),
            new JButton("Student"),
            new JButton("Course"),
            new JButton("Section"),
            new JButton("Help"),
            new JButton("Purge")
        );

        teacherFrame = new TeacherFrame();
        studentFrame = new StudentFrame();
        courseFrame = new CourseFrame();
        sectionFrame = new SectionFrame();
        aboutFrame = new AboutFrame();

        frameList = List.of(
            teacherFrame, 
            studentFrame, 
            courseFrame, 
            sectionFrame, 
            aboutFrame
        );

        buttonGrid = new GridLayout(6, 1);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 120, 400);
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
       
        buttonPanel.setLayout(buttonGrid);

        for (int i = 0; i < buttonList.size(); i++) {
            JButton btn = buttonList.get(i);
            btn.setFont(new Font("Arial", Font.BOLD, 10));
            buttonPanel.add(btn);
        }

        buttonList.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClick(0);
                teacherFrame.refreshList();
                teacherFrame.refreshFrame(-1);
            }
        });

        buttonList.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClick(1);
                studentFrame.refreshList();
                studentFrame.refreshFrame(-1);
            }
        });

        buttonList.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClick(2);
            }
        });

        buttonList.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClick(3);
            }
        });

        buttonList.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClick(4);
            }
        });

        buttonList.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClick(0);
                SqlManager.getInstance().execute("DROP TABLE `school_manager`.`course`, `school_manager`.`enrollment`, `school_manager`.`section`, `school_manager`.`student`, `school_manager`.`teacher`");
                teacherFrame.refreshList();
                teacherFrame.refreshFrame(-1);
                studentFrame.refreshList();
                studentFrame.refreshFrame(-1);
                courseFrame.refreshList();
                courseFrame.refreshFrame(-1);
                sectionFrame.refreshList();
                sectionFrame.refreshFrame(-1);
                sectionFrame.refreshItemLists();
                // SqlManager.getInstance().refreshAll();
                SqlManager.getInstance().loadAll();
            }
        });

        add(buttonPanel);
        add(teacherFrame);
        add(studentFrame);
        add(courseFrame);
        add(sectionFrame);
        add(aboutFrame);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SqlManager.getInstance().close();
            }
        });

        setVisible(true);

        buttonClick(0);

    }

    public void buttonClick(int num) {
        buttonList.forEach(b -> {
            b.setFont(new Font("Arial", Font.BOLD, 10));
        });

        frameList.forEach(f -> {
            f.setVisible(false);
            f.setFocusable(false);
        });

        frameList.get(num).setVisible(true);
        frameList.get(num).setFocusable(true);
        frameList.get(num).requestFocus();
        buttonList.get(num).setFont(new Font("Arial", Font.TRUETYPE_FONT, 15));
    }
}
