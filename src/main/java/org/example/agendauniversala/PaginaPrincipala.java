package org.example.agendauniversala;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
public class PaginaPrincipala implements Initializable {
    @FXML
    public Label sarcini;
    @FXML
    public Label calendar;
    @FXML
    public Label filtreaza;
    @FXML
    public Label sorteaza;
    @FXML
    public Label cauta;
    @FXML
    public Label adaugaSarcina;
    @FXML
    public ImageView deschideMeniu;
    @FXML
    public ImageView inchideMeniu;
    @FXML
    public AnchorPane meniuNavigare;
    @FXML
    public TableView tabelaSarcini;
    public AnchorPane paginaPrincipala;
    @FXML
    private Label nume;
    @FXML
    private Label rol;
    @FXML
    public Button inchidePagina;
    @FXML
    public Pane setari;
    @FXML
    public Pane afisareMembri;
    @FXML
    public Pane adaugareMembri;
    @FXML
    public Pane deconectare;
    @FXML
    public Label setariText;
    @FXML
    public Label afisareText;
    @FXML
    public Label adaugaText;
    @FXML
    public Label deconectareText;
    @FXML
    public ImageView setariIcon;
    @FXML
    public ImageView membriIcon;
    @FXML
    public ImageView adaugaIcon;
    @FXML
    public ImageView deconectareIcon;
    @FXML
    private ImageView contIcon;
    @FXML
    private Label contText;
    @FXML
    private Pane cont;
    private double xOffset = 0;
    private double yOffset = 0;
    public void afisareSarcini(MouseEvent event){
        tabelaSarcini.setVisible(true);
        sarcini.setStyle("-fx-underline: true");
        calendar.setStyle("-fx-underline: false");
    }
    public void afisareCalendar(MouseEvent event){
        tabelaSarcini.setVisible(false);
        calendar.setStyle("-fx-underline: true");
        sarcini.setStyle("-fx-underline: false");
    }
    public void deschideMeniu(MouseEvent event){
        nume.setText(new PaginaConectare().getNume());
        rol.setText(new PaginaConectare().getRol());
        meniuNavigare.setVisible(true);
        meniuNavigare.setTranslateX(-500);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(meniuNavigare);
        slide.setToX(0);
        meniuNavigare.setTranslateX(-500);
        slide.setOnFinished((ActionEvent e) ->{
            inchideMeniu.setVisible(true);
        });
        slide.play();

    }
    public void inchideMeniu(MouseEvent event){
        TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(meniuNavigare);
            slide.setToX(-500);
            meniuNavigare.setTranslateX(0);
            slide.setOnFinished((ActionEvent e) ->{
                inchideMeniu.setVisible(false);
            });
            slide.play();
    }
    public void adaugaMembriAct(MouseEvent e) throws IOException {
        new PaginaAdaugareMembri().start();
        inchideMeniu(e);
    }
    public void afisareMembriAct(MouseEvent e) throws IOException {
        new PaginaAfisareMembri().start();
        inchideMeniu(e);
    }
    public void afisareSetari(MouseEvent e) throws  IOException{
        new PaginaSetari().start();
        inchideMeniu(e);
    }
    public void inchidePagina(ActionEvent e){
        Stage stage = (Stage) paginaPrincipala.getScene().getWindow();
        stage.close();
        PreparedStatement pst;
        Connection con = null;
        ConectareBD conectare = new ConectareBD();
        try{
            conectare.conectareBD();
            con = conectare.con;
            pst = con.prepareStatement("UPDATE users SET status = 'Inactiv' WHERE username = ?");
            pst.setString(1, new PaginaConectare().getNumeUtilizator());
            pst.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception);
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
    public void deconectare(MouseEvent e) throws IOException{
        Stage stagePaginaPrincipala = (Stage) deconectare.getScene().getWindow();
        stagePaginaPrincipala.close();
        new Main().startDupaDeconectare();
        PreparedStatement pst;
        Connection con = null;
        ConectareBD conectare = new ConectareBD();
        try{
            conectare.conectareBD();
            con = conectare.con;
            pst = con.prepareStatement("UPDATE users SET status = 'Inactiv' WHERE username = ?");
            pst.setString(1, new PaginaConectare().getNumeUtilizator());
            pst.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception);
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
    private final String obtineRol = new PaginaConectare().getRol();
    public void afisareDupaRol(String rol){
        if(rol.equals("Admin")){
            return;
        }else{
            afisareMembri.setVisible(false);
            adaugareMembri.setVisible(false);
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cont.setOnMouseEntered(e -> {
            cont.setStyle("-fx-background-color: #2a62bc;");
            contText.setStyle("-fx-text-fill: #ffffff;");
            contIcon.setVisible(true);
        });
        cont.setOnMouseExited(e -> {
            cont.setStyle("-fx-background-color: #ffffff");
            contText.setStyle("-fx-text-fill: #2a62bc;");
            contIcon.setVisible(false);
        });
        setari.setOnMouseEntered(e -> {
            setari.setStyle("-fx-background-color: #2a62bc;");
            setariText.setStyle("-fx-text-fill: #ffffff;");
            setariIcon.setVisible(true);
        });
        setari.setOnMouseExited(e -> {
            setari.setStyle("-fx-background-color: #ffffff");
            setariText.setStyle("-fx-text-fill: #2a62bc;");
            setariIcon.setVisible(false);

        });
        adaugareMembri.setOnMouseEntered(e -> {
            adaugareMembri.setStyle("-fx-background-color: #2a62bc;");
            adaugaText.setStyle("-fx-text-fill: #ffffff;");
            adaugaIcon.setVisible(true);
        });
        adaugareMembri.setOnMouseExited(e -> {
            adaugareMembri.setStyle("-fx-background-color: #ffffff");
            adaugaText.setStyle("-fx-text-fill: #2a62bc;");
            adaugaIcon.setVisible(false);

        });
        afisareMembri.setOnMouseEntered(e -> {
            afisareMembri.setStyle("-fx-background-color: #2a62bc;");
            afisareText.setStyle("-fx-text-fill: #ffffff;");
            membriIcon.setVisible(true);
        });
        afisareMembri.setOnMouseExited(e -> {
            afisareMembri.setStyle("-fx-background-color: #ffffff");
            afisareText.setStyle("-fx-text-fill: #2a62bc;");
            membriIcon.setVisible(false);
        });
        deconectare.setOnMouseEntered(e -> {
            deconectare.setStyle("-fx-background-color: #2a62bc;");
            deconectareText.setStyle("-fx-text-fill: #ffffff;");
            deconectareIcon.setVisible(true);
        });
        deconectare.setOnMouseExited(e -> {
            deconectare.setStyle("-fx-background-color: #ffffff");
            deconectareText.setStyle("-fx-text-fill: #2a62bc;");
            deconectareIcon.setVisible(false);
        });
        afisareDupaRol(obtineRol);
    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("paginaPrincipala.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1461, 770);
        String css = Objects.requireNonNull(this.getClass().getResource("styles/paginaPrincipala.css")).toExternalForm();
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
