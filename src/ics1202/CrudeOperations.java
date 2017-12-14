package ics1202;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
import static java.awt.Event.INSERT;
import java.sql.*;
import java.sql.SQLException;
/**
 *
 * @author 83348
 */
public class CrudeOperations {
    private int student_no;
    private String first_name;
    private String last_name;
    private String gender;
    private String programme;

    /**
     * @return the student_no
     */
    public int getStudent_no() {
        return student_no;
    }

    /**
     * @param student_no the student_no to set
     */
    public void setStudent_no(int student_no) {
        this.student_no = student_no;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the programme
     */
    public String getProgramme() {
        return programme;
    }

    /**
     * @param programme the programme to set
     */
    public void setProgramme(String programme) {
        this.programme = programme;
    }
    
    public int processGel(String g){
       if (g.equalsIgnoreCase("male")){
           return 1;
       }
       return 0;
               }
    public boolean save(){
        PreparedStatement pst = null;
        Connection conn = new DBConnector().con;
        try{
                pst = conn.prepareStatement("INSERT INTO student_details(student_no,first_name,last_name,gender,programme)VALUES(?,?,?,?,?)");
        
             pst.setInt(1, getStudent_no());
             pst.setString(2, getFirst_name());
             pst.setString(3, getLast_name());
             pst.setInt(4, processGel(getGender()));
             pst.setString(5, getProgramme());
             pst.executeUpdate();
             return true;
             
         }catch(SQLException ex){
             System.out.println("Error" + ex.getMessage());
             return false;
         }
    }
    
// Method to remove record from database 
    public boolean removeRecord(int reg_number)
        {
        
        PreparedStatement pst = null;
        Connection conn = new DBConnector(). con;
        try{
               
            pst = conn.prepareStatement("DELETE FROM student_details WHERE Student_no = ?");
                pst.setInt(1,getStudent_no());
                int xy = pst.executeUpdate();
                    if (xy == 1)
                    {
                        System.out.println("Student Number " + getStudent_no() 
                                + " has been remoed successfully." );
                        conn.commit();
                    }else{
                        System.out.println("Student Number " + getStudent_no()
                                +" does not exist" );
                    }
            }catch(SQLException se)
                {
                    return false;
                }
        return true;
        }
    
    // This method updates the record in the database
    public boolean updateRecord()
        {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection conn = new DBConnector().con;
        
        // Check that the record exists
        try{
        
        pst = conn.prepareStatement("SELECT * FROM student_details WHERE student_id = ?");
        pst.setInt(1,getStudent_no());
        rs = pst.executeQuery();
        // If the record exists, ask user which parameter they wuld like to update
        
        if (rs.next())
        {                    
        System.out.println("Which field would you like to update:\n"
                        + "1. Student_no\n2. First name\n"
                        + "3. Last name\n4. Gender\n5. Programme" );
        int param =scanner.nextInt();
        scanner.nextLine();
        
        // Depending on their selection, use setter to set value, and update database
        switch (param)
                    {
                    case 1:
                        System.out.println("Enter new ID for the student:");
                        student_no=scanner.nextInt();
                        pst = conn.prepareStatement("UPDATE student_details SET "
                                + "student_no = ? WHERE student_no = " 
                                + getStudent_no());
                        pst.setInt(1,student_no);
                        pst.executeUpdate();
                    break;
                    case 2 :
                        System.out.println("Enter new first name for the student:");
                        first_name =scanner.nextLine();
                        pst = conn.prepareStatement("UPDATE student_details SET "
                                + "first_name = ? WHERE student_id = " 
                                + getStudent_no());
                        pst.setString(1,first_name);
                        pst.executeUpdate();
                    break;
                    case 3 :
                        System.out.println("Enter new last name for the student:");
                        last_name = scanner.nextLine();
                        pst = conn.prepareStatement("UPDATE student_details SET "
                                + "last_name = ? where student_id = " 
                                + getStudent_no());
                        pst.setString(1,last_name);
                        pst.executeUpdate();
                    break;
                    case 4 :
                        System.out.println("Enter new gender for the student:");
                        String gender = scanner.nextLine();
                        pst = conn.prepareStatement("UPDATE student_details SET "
                                + "gender = ? where student_id =  " 
                                + getStudent_no());
                        pst.setInt(1,processGel(gender));
                        pst.executeUpdate();
                    break;
                    case 5 :
                        System.out.println("Enter new degree programme for the student:");
                        String programme = scanner.nextLine();
                        pst = conn.prepareStatement("UPDATE student_details SET "
                                + "degree_programme = ? where student_id = "
                                + getStudent_no());
                        pst.setString(1,programme);
                        pst.executeUpdate();
                    break;
                    default:
                        System.out.println("Invalid parameter selected");
                    }
        // Commit your changes, otherwise they will not be stored on the database
                    conn.commit();
        }
        else{
            System.out.println("That StudentID does not exist on our system.");
        }
            }catch(SQLException se)
                {
                return false;
                }
            return true;
        }
    
    // Archiving a record. This method is used for delete and update operations
    // since we would still like to have the previous data stored for future reference. 
    
    
    public boolean archiveRecord(int reg_number) {
        ResultSet rs = null; 
        PreparedStatement pst = null;
        Connection conn = new DBConnector().con;
        
        try{
                //Get the record from the database;
                
                pst = conn.prepareStatement("SELECT * FROM student_details "
                        + "WHERE student_no = ?");
                pst.setInt(1,getStudent_no());
                rs = pst.executeQuery();
                
                // exctract the details to be filled in the old 
                // (archive table)
                while (rs.next())
                    {
                        setStudent_no(rs.getInt(1));
                        setFirst_name(rs.getString(2));
                        setLast_name(rs.getString(3));
                        setGender(rs.getString(4));
                        setProgramme(rs.getString(5));
                    
                        pst = conn.prepareStatement("INSERT INTO student_old "
                                + "(student_id, first_name, last_name, gender, "
                                + "degree_programme) VALUES (?,?,?,?,?)");    
                            pst.setInt(1,getStudent_no());
                            pst.setString(2,getFirst_name());
                            pst.setString(3,getLast_name());
                            pst.setInt(4,processGel(getGender()));
                            pst.setString(5,getProgramme());
                            int rts = pst.executeUpdate();
                        
                        // Run as a transaction. Should the return status be 
                        //success, commit, else rollback.
                        if (rts == 1)
                            {                            
                                conn.commit();
                            return true;
                            }else {
                                conn.rollback();
                            return false;}
                            }
            }catch(SQLException se){
                System.out.print(se.getMessage());
                return false;
            }
        return true;
        }

    boolean checkRecord(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setPasswd(String passwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean register() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
