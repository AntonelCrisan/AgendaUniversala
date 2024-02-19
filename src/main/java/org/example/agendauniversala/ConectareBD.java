package org.example.agendauniversala;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConectareBD {
    Connection con;
    public void conectareBD(){
        try{
            String urlBD = "jdbc:mysql://localhost:3306/agenda_universala";
            String user = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(urlBD, user, password);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
