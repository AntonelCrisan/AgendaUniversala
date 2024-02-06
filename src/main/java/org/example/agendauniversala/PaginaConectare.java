package org.example.agendauniversala;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class PaginaConectare implements Initializable{
    @FXML
    private Button iesireButon;
    @FXML
    private Button conectareButon;
    @FXML
    private Label eroareMesaj;
    @FXML
    private TextField numeCamp;
    @FXML
    private PasswordField parolaCamp;
    private static String rol;
    private static String numeUtilizator;
    private static int id;
    private static String nume;
    public void iesireButonAct(ActionEvent e){
        Stage stage = (Stage) iesireButon.getScene().getWindow();
        stage.close();
    }
    public void conenctareButonAct(ActionEvent e){
        PreparedStatement pst;
        ConectareBD conectare = new ConectareBD();
        try{
            conectare.conectareBD();
            Connection con = conectare.con;
            pst = con.prepareStatement("select id,password,role, name, username from users where username=? and password=?");
            pst.setString(1, numeCamp.getText());
            pst.setString(2, parolaCamp.getText());
            ResultSet rs = pst.executeQuery();
            if(numeCamp.getText().isEmpty() || parolaCamp.getText().isEmpty()){
                eroareMesaj.setText("Toate câmpurile sunt obligatorii!");
            }else{
                if(rs.next()) {
                    String rol = rs.getString("role");
                    setRol(rol);
                    int id = rs.getInt("id");
                    setId(id);
                    setNumeUtilizator(numeCamp.getText());
                    String name = rs.getString("name");
                    setNume(name);
                    eroareMesaj.setText(null);
                    Stage stage = (Stage) iesireButon.getScene().getWindow();
                    stage.close();
                    new PaginaPrincipala().start();
                }else{
                    eroareMesaj.setText("Numele sau parola este incorectă!");

                }
            }
        }catch(Exception ex){
            numeCamp.setText("");
            parolaCamp.setText("");
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        PaginaConectare.rol = rol;
    }
    public String getNumeUtilizator() {
        return numeUtilizator;
    }
    public void setNumeUtilizator(String numeUtilizator) {
        PaginaConectare.numeUtilizator = numeUtilizator;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        PaginaConectare.id = id;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        PaginaConectare.nume = nume;
    }
}