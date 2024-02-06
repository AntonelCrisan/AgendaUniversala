package org.example.agendauniversala;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    public Pane deconectare;
    public BorderPane paginaPrincipala;
    @FXML
    private Label nume;
    @FXML
    private Label rol;
    private final String[] randuri = {"Nume sarcina", "Stare sarcina", "Nume", "Data", "Prioritate sarcina"};
    private final String[] conditie = {"Este", "Nu este"};

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
        slide.setToX(-49);
        meniuNavigare.setTranslateX(-500);
        slide.setOnFinished((ActionEvent e) ->{
            deschideMeniu.setVisible(false);
            inchideMeniu.setVisible(true);
        });
        slide.play();

    }
    public void inchideMeniu(MouseEvent event){
        TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(meniuNavigare);
            slide.setToX(-500);
            meniuNavigare.setTranslateX(-49);
            slide.setOnFinished((ActionEvent e) ->{
                deschideMeniu.setVisible(true);
                inchideMeniu.setVisible(false);
            });
            slide.play();
    }
    public void adaugaMembri(MouseEvent e) throws IOException {
        new PaginaAdaugareMembri().start();
        inchideMeniu(e);
    }
    public void deconectare(MouseEvent e) throws IOException{
        Stage stage = (Stage) deconectare.getScene().getWindow();
        stage.close();
        new Main().startDupaDeconectare();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        randuriFiltru.getItems().addAll(randuri);
//        conditieFiltru.getItems().addAll(conditie);
    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("paginaPrincipala.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 750);
        String css = Objects.requireNonNull(this.getClass().getResource("paginaPrincipala.css")).toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
