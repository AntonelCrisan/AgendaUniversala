package org.example.agendauniversala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfisareMembri {
    public static ObservableList<Membri> membri(){
        ConectareBD conectare = new ConectareBD();
        ObservableList<Membri> lista = FXCollections.observableArrayList();
        PreparedStatement pst;
        Connection con = null;
        try{
            conectare.conectareBD();
            con = conectare.con;
            pst = con.prepareStatement("select name, username, email, phone, role, status from users");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                lista.add(new Membri(rs.getString("name"), rs.getString("username"), rs.getString("email"),
                        rs.getString("phone"), rs.getString("role"), rs.getString("status")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
            }
        }
        return lista;
    }
}
