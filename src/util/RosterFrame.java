package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import management.SqlManager;
import objects.Objects.Enrollment;
import objects.Objects.Section;
import objects.Objects.Student;

public class RosterFrame extends JButton {

    private Student student;
    private Section section;

    public RosterFrame(Student student, Section section) {
        super(student.last_name + ", " + student.first_name + " - " + student.student_id + " -- Not Enrolled");
        this.student = student;
        this.section = section;

        if (SqlManager.getInstance().enrollmentExists(student, section)) {
            enroll(true);
        } else {
            enroll(false);
        }

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isEnrolled()) {
                    SqlManager.getInstance().removeEnrollment(student, section);
                    enroll(false);
                } else {
                    SqlManager.getInstance().addEnrollment(student, section);
                    enroll(true);
                }
            }
        });
        setVisible(true);
    }

    public void enroll(boolean enrolled) {
        String nameText = student.last_name + ", " + student.first_name + " - " + student.student_id;
                String enable = " -- Not Enrolled";
                String disable = " -- Enrolled";
        if (enrolled) {
            setText(nameText + disable);
        } else {
            setText(nameText + enable);
        }
    }

    public boolean isEnrolled() {
        String nameText = student.last_name + ", " + student.first_name + " - " + student.student_id;
                String enable = " -- Not Enrolled";
                String disable = " -- Enrolled";
        return getText().equals(nameText + disable);
    }
}
