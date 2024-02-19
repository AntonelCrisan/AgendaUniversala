package org.example.agendauniversala;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class PaginaConectare implements Initializable{
    @FXML
    public Button iesireButon;
    @FXML
    public Button conectareButon;
    @FXML
    public Label eroareParola;
    @FXML
    public Label eroareNume;
    public Label eroareConectare;
    @FXML
    public TextField numeCamp;
    @FXML
    public PasswordField parolaCamp;
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
        PreparedStatement pst2;
        ConectareBD conectare = new ConectareBD();
        try{
            conectare.conectareBD();
            Connection con = conectare.con;
            pst = con.prepareStatement("SELECT id,password,role, name, username FROM users WHERE username=? AND password=?");
            pst2 = con.prepareStatement("UPDATE users SET status = 'Activ' WHERE username = ?");
            pst.setString(1, numeCamp.getText());
            pst.setString(2, parolaCamp.getText());
            pst2.setString(1, numeCamp.getText());
            ResultSet rs = pst.executeQuery();
            pst2.executeUpdate();
            String nume = numeCamp.getText();
            String parola = parolaCamp.getText();
            if(numeCamp.getText().isEmpty() || parolaCamp.getText().isEmpty()){
                ValidareCampuri.validareConectare(nume, parola, eroareNume, eroareParola,eroareConectare, numeCamp, parolaCamp);
            }else if(rs.next()) {
                String rol = rs.getString("role");
                setRol(rol);
                int id = rs.getInt("id");
                setId(id);
                setNumeUtilizator(numeCamp.getText());
                String name = rs.getString("name");
                setNume(name);
                eroareNume.setText(null);
                eroareParola.setText(null);
                Stage stage = (Stage) iesireButon.getScene().getWindow();
                stage.close();
                new PaginaPrincipala().start();
            }else{
                eroareConectare.setText("Numele sau parola este incorectă!");
                numeCamp.setStyle("-fx-border-color: #ec1a1a");
                parolaCamp.setStyle("-fx-border-color: #ec1a1a");
                eroareNume.setText(null);
                eroareParola.setText(null);
            }
        }catch(Exception ex){
            numeCamp.setText("");
            parolaCamp.setText("");
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
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