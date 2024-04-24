import java.sql.*;
class MysqlConExample{
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school_manager","root","password");

            Statement stm = con.createStatement();
            stm.execute("INSERT INTO student (id, first_name, last_name) VALUES (1, 'Jim', 'Smith');");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}  