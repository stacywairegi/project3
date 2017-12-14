package ics1202;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STACY
 */
public class DBConnector {
    Connection con = null;
    
public DBConnector(){
    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException ex) {
        System.out.println("Where is your MySQL JDBC Driver?");
        ex.printStackTrace();
        
        Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
    }
               
        
    try {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ics1202","root","");
//           Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("Select * from student_details");
//            while(rs.next()){
//                System.out.println(rs.getInt("student_no") + ", " +rs.getString("first_name") + ", " +rs.getString("last_name") +", " +rs.getInt("gender") + ", " + rs.getString("programme"));
    } catch (SQLException ex) {
        System.out.println("Connection Failed! Check output console!");
        ex.printStackTrace();
        
    
    }
     if(con!= null){
         System.out.println("You made it, take control your database now!");
     } else{
        System.out.println("Failed tomake connection!"); 
     }
            }
            
      
}
