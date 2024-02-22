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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public Label genereazaParola;
    @FXML
    public Label parolaGenerata;
    @FXML
    public Label mesajEroare;
    @FXML
    public Pane mesajSucces;
    @FXML
    public Pane campParola;
    @FXML
    public Button inchideMesaj;
    @FXML
    public ChoiceBox<String> rol;
    @FXML
    public Label eroareNume;
    @FXML
    public Label eroareNumeUtilizator;
    @FXML
    public Label eroareEmail;
    @FXML
    public Label eroareTelefon;
    @FXML
    public Label eroareParola;
    @FXML
    public Label eroareRol;
    @FXML
    public AnchorPane paginaAdaugaMembru;
    @FXML
    public Pane containerAdaugareMembri;
    private double xOffset = 0;
    private double yOffset = 0;
    private final String[] alegereRol = {"Admin", "Profesor", "Secretar", "Contabil"};
    public void inchidePagina(ActionEvent e){
        Stage stage = (Stage) inchideAdaugareMembri.getScene().getWindow();
        stage.close();
    }
    public void inchideMesajAct(ActionEvent e){
        mesajSucces.setVisible(false);
        containerAdaugareMembri.setStyle("-fx-opacity: 1");
    }
    public void adaugareMembru(ActionEvent e){
        String email = emailCamp.getText();
        boolean esteValid = isValidEmailDomain(email);
        String nume = numeCamp.getText();
        String numeUtilizator = numeUtilizatorCamp.getText();
        String emailC = emailCamp.getText();
        String telefon = telefonCamp.getText();
        String parola = parolaGenerata.getText();
        ValidareCampuri.validareAdaugareMembri(nume, numeUtilizator, emailC, telefon, parola, rol, eroareNume, eroareNumeUtilizator,
                eroareEmail, eroareTelefon, eroareParola, eroareRol, mesajEroare, numeCamp, numeUtilizatorCamp, emailCamp, telefonCamp,
                campParola);
        if(numeCamp.getText().isEmpty() || numeUtilizatorCamp.getText().isEmpty() || emailCamp.getText().isEmpty() || telefonCamp.getText().isEmpty() ||
                parolaGenerata.getText().isEmpty() || rol.getValue() == null) {
        }else if(telefonCamp.getText().length() != 10) {
            eroareTelefon.setText("Numărul de telefon trebuie să conțină 10 caractere!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroare.setText("");
        }else if(esteValid) {
            telefonCamp.setStyle(null);
            eroareTelefon.setText("");
            PreparedStatement ps;
            Connection con = null;
            ConectareBD conectare = new ConectareBD();
            try {
                conectare.conectareBD();
                con = conectare.con;
                ps = con.prepareStatement("insert into users(name,username, email, phone, password, role) values(?, ?, ?, ?, ?, ?)");
                ps.setString(1, numeCamp.getText());
                ps.setString(2, numeUtilizatorCamp.getText());
                ps.setString(3, emailCamp.getText());
                ps.setString(4, telefonCamp.getText());
                ps.setString(5, parolaGenerata.getText());
                ps.setString(6, rol.getValue());
                ps.executeUpdate();
                mesajSucces.setVisible(true);
                containerAdaugareMembri.setStyle("-fx-opacity: 0.5");
                CuratareCampuri.curataCampuriAdaugareMembri(rol, eroareNumeUtilizator, eroareNume, eroareEmail, eroareTelefon, parolaGenerata,
                        eroareRol, numeUtilizatorCamp, numeCamp, emailCamp, telefonCamp, campParola, mesajEroare);
            }catch(Exception ex) {
                mesajEroare.setText("Nume de utilizator, telefon sau email deja folosit!");
                telefonCamp.setStyle("-fx-border-color: #ec1a1a");
                numeUtilizatorCamp.setStyle("-fx-border-color: #ec1a1a");
                emailCamp.setStyle("-fx-border-color: #ec1a1a");
                rol.setStyle(null);
                numeCamp.setStyle(null);
                campParola.setStyle(null);
            }finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
                }
            }
        }else{
            eroareEmail.setText("Emailul introdus este incorect!");
            emailCamp.setStyle("-fx-border-color: #ec1a1a");
            telefonCamp.setStyle(null);
            numeUtilizatorCamp.setStyle(null);
            rol.setStyle(null);
            numeCamp.setStyle(null);
            campParola.setStyle(null);
            mesajEroare.setText("");
            }
    }
    public void afisareParolaGenerata(MouseEvent e){
        generareParola();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
       rol.getItems().addAll(alegereRol);
    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("adaugaMembri.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 490);
        String css = Objects.requireNonNull(this.getClass().getResource("styles/adaugaMembri.css")).toExternalForm();
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
    public boolean isValidEmailDomain(String email){
        String Pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        return email.matches(Pattern);
    }
    public void generareParola(){
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
