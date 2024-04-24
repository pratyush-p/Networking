package management;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Objects.Course;
import objects.Objects.Enrollment;
import objects.Objects.Section;
import objects.Objects.Student;
import objects.Objects.Teacher;

public class SqlManager {

    public static SqlManager instance;
    private Connection con;

    public List<Student> studentList;
    public List<Teacher> teacherList;
    public List<Course> courseList;
    public List<Section> sectionList;
    public List<Enrollment> enrollmentList;

    public SqlManager() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school_manager","root","password");
                    
            loadAll();

        } catch(Exception e) { System.out.println(e);}

        instance = this;

        studentList = new ArrayList<>();
        teacherList = new ArrayList<>();
        courseList = new ArrayList<>();
        sectionList = new ArrayList<>();
        enrollmentList = new ArrayList<>();

    }

    public static SqlManager getInstance() {
        return instance;
    }

    public void execute(String string) {
        try {
            Statement stm = con.createStatement();
            stm.execute(string);    
        } catch (Exception e) {
            System.err.println(e);
        }
    } 

    public void close() {
        try {
            con.close();            
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public ResultSet executeQuery(String string) {
        try {
            Statement stm = con.createStatement();
            return stm.executeQuery(string);    
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    } 

    public void loadAll() {
        try {
            Statement stm = con.createStatement();
            stm.execute("CREATE TABLE IF NOT EXISTS student( student_id INTEGER NOT NULL AUTO_INCREMENT, first_name TEXT NOT NULL, last_name TEXT NOT NULL, PRIMARY KEY(student_id) );");  
            stm.execute("CREATE TABLE IF NOT EXISTS teacher( teacher_id INTEGER NOT NULL AUTO_INCREMENT, first_name TEXT NOT NULL, last_name TEXT NOT NULL, PRIMARY KEY(teacher_id) );");
            try {
                stm.execute("INSERT INTO teacher(teacher_id, first_name, last_name) VALUES (-1, 'Teacher', 'No');");                
            } catch (Exception e) {
                stm.execute("UPDATE teacher SET first_name='Teacher', last_name='No' WHERE teacher_id=-1;");
            }
            stm.execute("CREATE TABLE IF NOT EXISTS course( course_id INTEGER NOT NULL AUTO_INCREMENT, title TEXT NOT NULL, type INT NOT NULL, PRIMARY KEY(course_id) );");
            stm.execute("CREATE TABLE IF NOT EXISTS section( section_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, course_id INTEGER NOT NULL, FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE ON UPDATE CASCADE, teacher_id INTEGER NOT NULL, FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE CASCADE ON UPDATE CASCADE );");
            stm.execute("CREATE TABLE IF NOT EXISTS enrollment( section_id INTEGER NOT NULL, FOREIGN KEY(section_id) REFERENCES section(section_id) ON DELETE CASCADE ON UPDATE CASCADE, student_id INTEGER NOT NULL, FOREIGN KEY(student_id) REFERENCES student(student_id) ON DELETE CASCADE ON UPDATE CASCADE, PRIMARY KEY (section_id, student_id) );");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public List<Teacher> refreshTeacher() {
        List<Teacher> retList = new ArrayList<>();
        ResultSet result = executeQuery("SELECT * FROM teacher");
        try {
            result.next();
            while (!result.isAfterLast()) {
                if (result.getInt("teacher_id") != -1) {
                    retList.add(new Teacher(
                        result.getInt("teacher_id"),
                        result.getString("first_name"),
                        result.getString("last_name")
                    ));    
                }
                result.next();
            }                
        } catch (Exception e) {
            System.err.println(e + " happened in teacher refresh");
            return null;
        }

        this.teacherList = retList;
        return retList;

    }

    public void addTeacher(Teacher teacher) {
        execute("INSERT INTO teacher(first_name, last_name) VALUES ('" + teacher.first_name + "', '" + teacher.last_name + "');");
        refreshTeacher();
    }

    public void editTeacher(Teacher teacher) {
        execute("UPDATE teacher SET first_name='" + teacher.first_name + "', last_name='" + teacher.last_name + "' WHERE teacher_id=" + teacher.teacher_id + ";");
        refreshTeacher();
    }

    public void removeTeacher(int id) {
        execute("DELETE FROM teacher WHERE teacher_id=" + id + "");
        refreshTeacher();
    }

    public List<Student> refreshStudent() {
        List<Student> retList = new ArrayList<>();
        ResultSet result = executeQuery("SELECT * FROM student");
        try {
            result.next();
            while (!result.isAfterLast()) {
                retList.add(new Student(
                    result.getInt("student_id"),
                    result.getString("first_name"),
                    result.getString("last_name")
                ));
                result.next();
            }                
        } catch (Exception e) {
            System.err.println(e + " happened at student");
        }

        this.studentList = retList;
        return retList;

    }

    public void addStudent(Student student) {
        execute("INSERT INTO student(first_name, last_name) VALUES ('" + student.first_name + "', '" + student.last_name + "');");
        refreshStudent();
    }

    public void editStudent(Student student) {
        execute("UPDATE student SET first_name='" + student.first_name + "', last_name='" + student.last_name + "' WHERE student_id=" + student.student_id + ";");
        refreshStudent();
    }

    public void removeStudent(int id) {
        execute("DELETE FROM student WHERE student_id=" + id + "");
        refreshStudent();
    }

    public List<Course> refreshCourse() {
        List<Course> retList = new ArrayList<>();
        ResultSet result = executeQuery("SELECT * FROM course");
        try {
            result.next();
            while (!result.isAfterLast()) {
                retList.add(new Course(
                    result.getInt("course_id"),
                    result.getString("title"),
                    result.getInt("type")
                ));
                result.next();
            }                
        } catch (Exception e) {
            System.err.println(e + " happened at course");
        }

        this.courseList = retList;
        return retList;

    }

    public void addCourse(Course course) {
        execute("INSERT INTO course(title, type) VALUES ('" + course.title + "', " + course.type + ");");
        refreshCourse();
    }

    public void editCourse(Course course) {
        execute("UPDATE course SET title='" + course.title + "', type=" + course.type + " WHERE course_id=" + course.course_id + ";");
        refreshCourse();
    }

    public void removeCourse(int id) {
        execute("DELETE FROM course WHERE course_id=" + id + "");
        refreshCourse();
    }

    public List<Section> refreshSection() {
        List<Section> retList = new ArrayList<>();
        ResultSet result = executeQuery("SELECT * FROM section");
        try {
            result.next();
            while (!result.isAfterLast()) {
                Section potentialSection = new Section(
                    result.getInt("section_id"),
                    findCourse(result.getInt("course_id")),
                    findTeacher(result.getInt("teacher_id"))
                );

                retList.add(potentialSection);

                result.next();
            }                
        } catch (Exception e) {
            System.err.println(e + " happened at section");
        }

        this.sectionList = retList;
        return retList;

    }

    public void addSection(Section section) {
        execute("INSERT INTO section(course_id, teacher_id) VALUES ('" + section.course.course_id + "', " + section.teacher.teacher_id + ");");
        refreshSection();
    }

    public void editSection(Section section) {
        execute("UPDATE section SET course_id='" + section.course.course_id + "', teacher_id=" + section.teacher.teacher_id + " WHERE section_id=" + section.section_id + ";");
        refreshSection();
    }

    public void removeSection(int id) {
        execute("DELETE FROM section WHERE section_id=" + id + "");
        refreshSection();
    }

    public Course findCourse(int course_id) {
        refreshCourse();
        for (Course c : courseList) {
            if (c.course_id == course_id) {
                return c;
            }
        }
        return null;
    }

    public Section findSection(int section_id) {
        refreshSection();
        for (Section s : sectionList) {
            if (s.section_id == section_id) {
                return s;
            }
        }
        return null;
    }

    public Teacher findTeacher(int teacher_id) {
        refreshTeacher();
        for (Teacher c : teacherList) {
            if (c.teacher_id == teacher_id) {
                return c;
            }
        }
        return null;
    }

    public Student findStudent(int student_id) {
        refreshStudent();
        for (Student s : studentList) {
            if (s.student_id == student_id) {
                return s;
            }
        }
        return null;
    }

    public void refreshAll() {
        refreshCourse();
        refreshSection();
        refreshStudent();
        refreshTeacher();
    }

    public boolean enrollmentExists(Student student, Section section) {
        ResultSet returnSet;
        try {
            returnSet = executeQuery("SELECT section_id, student_id FROM enrollment WHERE student_id=" + student.student_id + " AND section_id=" + section.section_id + ";");
            // System.err.println("checking if enrollment exists at: " + section.section_id + " - " + student.student_id);
            returnSet.next();
            if (returnSet.getInt("student_id") == student.student_id) {
                return true;
            } else {
                return false;
            }
    
        } catch (Exception e) {
            System.err.println("couldn't find enrollment for this student: " + e);
        }
        return false;
    }

    public void addEnrollment(Student student, Section section) {
        try {
            execute("INSERT INTO enrollment (section_id, student_id) VALUES (" + section.section_id + ", " + student.student_id + ");");
        } catch (Exception e) {
            System.err.println("couldnt insert new enrollment: " + e);
        }
    }

    public void removeEnrollment(Student student, Section section) {
        try {
            execute("DELETE FROM enrollment WHERE student_id=" + student.student_id + ";");
        } catch (Exception e) {
            System.err.println("couldnt remove enrollment: " + e);
        }
    }

    public List<Section> studentSchedule(Student student) {
        List<Section> retList = new ArrayList<>();
        ResultSet result = executeQuery("SELECT * FROM enrollment");
        try {
            result.next();
            while (!result.isAfterLast()) {
                Enrollment potentialSection = new Enrollment(
                    findSection(result.getInt("section_id")),
                    findStudent(result.getInt("student_id"))
                );
                if (potentialSection.student.equals(student)) {
                    retList.add(potentialSection.section);
                }

                result.next();
            }                
        } catch (Exception e) {
            System.err.println(e + " happened at schedule portion");
        }
        return retList;
    }
}
