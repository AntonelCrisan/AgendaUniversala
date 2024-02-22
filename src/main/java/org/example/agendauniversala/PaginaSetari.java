package org.example.agendauniversala;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaginaSetari implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private AnchorPane paginaSetari;
    @FXML
    private Pane modificareEmail;
    @FXML
    private Pane setariPane;
    @FXML
    private Pane modificareParola;
    @FXML
    private Pane modificareTelefon;
    @FXML
    private Pane mesajSucces;
    @FXML
    private Label emailCont;
    @FXML
    private Label telefonCont;
    @FXML
    private Label eroareEmail;
    @FXML
    private Label eroareConfEmail;
    @FXML
    private Label mesajEroareEmail;
    @FXML
    private TextField emailCamp;
    @FXML
    private TextField confEmailCamp;
    @FXML
    private Label mesaj;
    @FXML
    private TextField parolaCamp;
    @FXML
    private TextField confParolaCamp;
    @FXML
    private Label eroareParola;
    @FXML
    private Label eroareConfParola;
    @FXML
    private Label mesajEroareParola;
    @FXML
    private TextField telefonCamp;
    @FXML
    private Label eroareTelefon;
    @FXML
    private Label mesajEroareTelefon;
    public void iesireSetari(ActionEvent e){
        Stage stage = (Stage) paginaSetari.getScene().getWindow();
        stage.close();
    }
    public void deschideModificareEmail(ActionEvent e){
        modificareEmail.setVisible(true);
        setariPane.setStyle("-fx-opacity: 0.5");
    }
    public void inchideModificareEmail(ActionEvent e){
        modificareEmail.setVisible(false);
        setariPane.setStyle("-fx-opacity: 1");
        CuratareCampuri.curataCampuriModificareEmail(eroareEmail, eroareConfEmail, mesajEroareEmail, emailCamp, confEmailCamp);
    }
    public void deschideModificareParola(ActionEvent e){
        modificareParola.setVisible(true);
        setariPane.setStyle("-fx-opacity: 0.5");
    }
    public void inchideModificareParola(ActionEvent e){
        modificareParola.setVisible(false);
        setariPane.setStyle("-fx-opacity: 1");
        CuratareCampuri.curataCampuriModificareParola(eroareParola, eroareConfParola, mesajEroareParola, parolaCamp, confParolaCamp);
    }
    public void deschideModificareTelefon(ActionEvent e){
        modificareTelefon.setVisible(true);
        setariPane.setStyle("-fx-opacity: 0.5");
    }
    public void inchideModificareTelefon(ActionEvent e){
        modificareTelefon.setVisible(false);
        setariPane.setStyle("-fx-opacity: 1");
        CuratareCampuri.curataCampTelefon(eroareTelefon, mesajEroareTelefon, telefonCamp);
    }
    public void inchideMesajSucces(ActionEvent e){
        mesajSucces.setVisible(false);
        setariPane.setStyle("-fx-opacity: 1");
    }
    public void modificareEmail(ActionEvent e){
        boolean esteValidEmail = isValidEmailDomain(emailCamp.getText());
        if(emailCamp.getText().isEmpty()){
            eroareEmail.setText("Câmp obligatoriu!");
            emailCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroareEmail.setText("");
        }else if(!emailCamp.getText().equals(confEmailCamp.getText())){
            confEmailCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareConfEmail.setText("Adresele de e-mail furnizate nu sunt identice!");
            mesajEroareEmail.setText("");
            eroareEmail.setText("");
            emailCamp.setStyle("-fx-border-color: #2a62bc");
        }else if(esteValidEmail){
                PreparedStatement pst;
                ConectareBD conectare = new ConectareBD();
                Connection con = null;
                try{
                    conectare.conectareBD();
                    con = conectare.con;
                    pst = con.prepareStatement("UPDATE users SET email = ? WHERE username = ?");
                    pst.setString(1, emailCamp.getText());
                    pst.setString(2, new PaginaConectare().getNumeUtilizator());
                    pst.executeUpdate();
                    emailCont.setText(emailCamp.getText());
                    modificareEmail.setVisible(false);
                    mesaj.setText("Email modificat cu succes!");
                    mesajSucces.setVisible(true);
                    CuratareCampuri.curataCampuriModificareEmail(eroareEmail, eroareConfEmail, mesajEroareEmail, emailCamp, confEmailCamp);
                }catch (SQLException exception){
                    exception.printStackTrace();
                    mesajEroareEmail.setText("Eroare, încercați mai târziu!");
                }finally {
                    try{
                        if(con != null){
                            con.close();
                        }
                    }catch (SQLException ex) {
                        System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
                    }
                }
        }else{
            eroareEmail.setText("Email invalid!");
            emailCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroareEmail.setText("");
            eroareConfEmail.setText("");
            confEmailCamp.setStyle("-fx-border-color: #2a62bc");
        }
    }
    public boolean isValidEmailDomain(String email){
        String Pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        return email.matches(Pattern);
    }
    public void modificareParola(ActionEvent e){
        if(parolaCamp.getText().isEmpty()){
            eroareParola.setText("Câmp obligatoriu!");
            parolaCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroareParola.setText("");
        }else if(parolaCamp.getText().length() <= 8){
            eroareParola.setText("Parola trebuie să conțină cel puțin 8 caractere!");
            parolaCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroareParola.setText("");
        }else if(!parolaCamp.getText().equals(confParolaCamp.getText())){
            confParolaCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareConfParola.setText("Parolele nu sunt identice!");
            mesajEroareParola.setText("");
            eroareParola.setText("");
            parolaCamp.setStyle("-fx-border-color: #2a62bc");
        }else{
            PreparedStatement pst;
            ConectareBD conectare = new ConectareBD();
            Connection con = null;
            try{
                conectare.conectareBD();
                con = conectare.con;
                pst = con.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                pst.setString(1, parolaCamp.getText());
                pst.setString(2, new PaginaConectare().getNumeUtilizator());
                pst.executeUpdate();
                CuratareCampuri.curataCampuriModificareParola(eroareParola, eroareConfParola, mesajEroareParola, parolaCamp, confParolaCamp);
                modificareParola.setVisible(false);
                mesajSucces.setVisible(true);
                mesaj.setText("Parola schimbată cu succes!");
            }catch (SQLException exception){
                exception.printStackTrace();
                mesajEroareParola.setText("Eroare, încercați mai târziu!");
            }finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
                }
            }
        }
    }
    public void modificareTelefon(ActionEvent e){
        if(telefonCamp.getText().isEmpty()){
            eroareTelefon.setText("Camp obligatoriu!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroareTelefon.setText("");
        }else if(telefonCamp.getText().length() != 10){
            eroareTelefon.setText("Numărul de telefon trebuie să conțină 10 caractere!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            mesajEroareTelefon.setText("");
        }else{
            PreparedStatement pst;
            ConectareBD conectare = new ConectareBD();
            Connection con = null;
            try{
                conectare.conectareBD();
                con = conectare.con;
                pst = con.prepareStatement("UPDATE users SET phone = ? WHERE username = ?");
                pst.setString(1, telefonCamp.getText());
                pst.setString(2, new PaginaConectare().getNumeUtilizator());
                pst.executeUpdate();
                telefonCont.setText(telefonCamp.getText());
                CuratareCampuri.curataCampTelefon(eroareTelefon, mesajEroareTelefon, telefonCamp);
                modificareTelefon.setVisible(false);
                mesaj.setText("Numarul de telefon schimbat cu succes!");
                mesajSucces.setVisible(true);
            }catch (SQLException exception){
                exception.printStackTrace();
            }finally {
                try{
                    if(con != null){
                        con.close();
                    }
                }catch (SQLException ex){
                    System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
                }
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaginaConectare obtineDate = new PaginaConectare();
        emailCont.setText(obtineDate.getEmail());
        telefonCont.setText(obtineDate.getTelefon());
    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("setari.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        String css = Objects.requireNonNull(this.getClass().getResource("styles/setari.css")).toExternalForm();
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
}
