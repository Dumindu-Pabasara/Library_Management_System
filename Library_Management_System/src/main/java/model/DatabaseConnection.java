/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author my
 */
class DatabaseConnection {

        public static Connection connectDB(){
       Connection con = null; 

        try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db?useSSL=false", "root", null);
            return con;
        } catch (Exception e) {
            
            System.err.println(e.toString());
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
        
    }

 

    
    
}
