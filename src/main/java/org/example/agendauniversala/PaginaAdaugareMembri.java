package org.example.agendauniversala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaginaAdaugareMembri implements Initializable {
    @FXML
    public Button inchideAdaugareMembri;
    @FXML
    public Button adaugaButon;
    @FXML
    public TextField numeCamp;
    @FXML
    public TextField numeUtilizatorCamp;
    public TextField emailCamp;
    @FXML
    public TextField telefonCamp;
    @FXML
    public Button parolaButon;
    public Label parolaGenerata;
    @FXML
    public Label mesaj;
    public Pane mesajSucces;
    public Button inchideMesaj;
    @FXML
    public ChoiceBox<String> rol;
    private double xOffset = 0;
    private double yOffset = 0;
    private final String[] alegereRol = {"Admin", "Profesor", "Secretar", "Contabil"};
    public void inchidePagina(ActionEvent e){
        Stage stage = (Stage) inchideAdaugareMembri.getScene().getWindow();
        stage.close();
    }
    public void inchideMesajAct(ActionEvent e){
        mesajSucces.setVisible(false);
    }
    public void adaugareMembru(ActionEvent e){
        String email = emailCamp.getText();
        boolean esteValid = isValidEmailDomain(email);
        if(numeCamp.getText().isEmpty() || numeUtilizatorCamp.getText().isEmpty() || emailCamp.getText().isEmpty() || telefonCamp.getText().isEmpty() ||
            parolaGenerata.getText().isEmpty() || rol.getValue() == null){
            mesaj.setText("Toate câmpurile sunt obligatorii!");
            mesaj.setStyle("-fx-text-fill: #ec1a1a");
        }else if(telefonCamp.getText().length() != 10){
            mesaj.setText("Numarul de telefon trebuie sa contina 10 caractere!");
            mesaj.setStyle("-fx-text-fill: #ec1a1a");
        }else if(esteValid){
            PreparedStatement ps;
            ConectareBD conectare = new ConectareBD();
            try{
                conectare.conectareBD();
                Connection con = conectare.con;
                ps = con.prepareStatement("insert into users(name,username, email, phone, password, role) values(?, ?, ?, ?, ?, ?)");
                ps.setString(1, numeCamp.getText());
                ps.setString(2, numeUtilizatorCamp.getText());
                ps.setString(3, emailCamp.getText());
                ps.setString(4, telefonCamp.getText());
                ps.setString(5, parolaGenerata.getText());
                ps.setString(6, rol.getValue());
                ps.executeUpdate();
                mesajSucces.setVisible(true);
                mesaj.setText(null);
                numeCamp.setText(null);
                numeUtilizatorCamp.setText(null);
                emailCamp.setText(null);
                telefonCamp.setText(null);
                parolaGenerata.setText(null);
            }
            catch(Exception ex){
                mesaj.setText("Nume de utilizator, telefon sau email deja folosit!");
                mesaj.setStyle("-fx-text-fill: #ec1a1a");
            }
        }else {
            mesaj.setText("Emailul introdus este incorect!");
            mesaj.setStyle("-fx-text-fill: #ec1a1a");
        }
    }
    public void afisareParolaGenerata(ActionEvent e){
        generareParola();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
       rol.getItems().addAll(alegereRol);

    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("adaugaMembri.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 490);
        String css = Objects.requireNonNull(this.getClass().getResource("adaugaMembri.css")).toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    private boolean isValidEmailDomain(String email){
        String Pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        return email.matches(Pattern);
    }
    private void generareParola(){
        String caractere = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@-_";
        SecureRandom random = new SecureRandom();
        StringBuilder parolaB = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(caractere.length());
            parolaB.append(caractere.charAt(randomIndex));
        }
        parolaGenerata.setText(parolaB.toString());
    }
}
