package org.example.agendauniversala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AfisareMembri {
    public static ObservableList<Membri> membri(){
        ConectareBD conectare = new ConectareBD();
        ObservableList<Membri> lista = FXCollections.observableArrayList();
        PreparedStatement pst;
        try{
            conectare.conectareBD();
            Connection con = conectare.con;
            pst = con.prepareStatement("select name, username, email, phone, role, status from users");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                lista.add(new Membri(rs.getString("name"), rs.getString("username"), rs.getString("email"),
                        rs.getString("phone"), rs.getString("role"), rs.getString("status")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }
}
