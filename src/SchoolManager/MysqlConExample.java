package SchoolManager;

import java.sql.*;
class MysqlConExample{
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school_manager","root","password");


            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}  