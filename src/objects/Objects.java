package objects;

public class Objects {
    public static class Student {
        public int student_id;
        public String first_name, last_name;

        public Student(int student_id, String first_name, String last_name) {
            this.student_id = student_id;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        public Student(String first_name, String last_name) {
            this.student_id = -1;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        @Override
        public boolean equals(Object obj) {
            return this.student_id == ((Student) obj).student_id;
        }
    }

    public static class Teacher {
        public int teacher_id;
        public String first_name, last_name;

        public Teacher(int teacher_id, String first_name, String last_name) {
            this.teacher_id = teacher_id;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        public Teacher(String first_name, String last_name) {
            this.teacher_id = -1;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        @Override
        public boolean equals(Object obj) {
            return this.teacher_id == ((Teacher) obj).teacher_id;
        }

    }

    public static class Course {
        public int course_id;
        public String title;
        public int type;

        public Course(int course_id, String title, int type) {
            this.course_id = course_id;
            this.title = title;
            this.type = type;
        }

        public Course(String title, int type) {
            this.course_id = -1;
            this.title = title;
            this.type = type;
        }

        @Override
        public boolean equals(Object obj) {
            return this.course_id == ((Course) obj).course_id;
        }

    }

    public static class Section {
        public int section_id;
        public Course course;
        public Teacher teacher;

        public Section(int section_id, Course course, Teacher teacher) {
            this.section_id = section_id;
            this.course = course;
            this.teacher = teacher;
        }

        public Section(Course course, Teacher teacher) {
            this.section_id = -1;
            this.course = course;
            this.teacher = teacher;
        }

        @Override
        public boolean equals(Object obj) {
            return this.section_id == ((Section) obj).section_id;
        }

    }

    public static class Enrollment {
        public Section section;
        public Student student;

        public Enrollment(Section section, Student student) {
            this.section = section;
            this.student = student;
        }

        @Override
        public boolean equals(Object obj) {
            Enrollment e = (Enrollment) obj;
            return this.section.equals(e.section) && this.student.equals(e.student);
        }
    }
}
