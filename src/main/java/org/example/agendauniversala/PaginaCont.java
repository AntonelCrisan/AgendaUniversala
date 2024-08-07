package org.example.agendauniversala;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView ;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaginaCont implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private AnchorPane paginaCont;
    @FXML
    private Label literaCont;
    @FXML
    private Label numeUtilizatorCont;
    @FXML
    private Label numeCont;
    @FXML
    private Label emailCont;
    @FXML
    private Label telefonCont;
    @FXML
    private ImageView pozaProfil;
    @FXML private Pane schimbaPoza;
    private static String url;
    public void inchidePagina(ActionEvent e){
        Stage stage = (Stage) paginaCont.getScene().getWindow();
        stage.close();
    }
    public void afisareDateCont(){
        String imagine = new PaginaConectare().getNume();
        String primaLitera = String.valueOf(imagine.charAt(0));
        literaCont.setText(primaLitera);
        numeUtilizatorCont.setText(new PaginaConectare().getNumeUtilizator());
        numeCont.setText(new PaginaConectare().getNume());
        emailCont.setText(new PaginaConectare().getEmail());
        telefonCont.setText(new PaginaConectare().getTelefon());
        PreparedStatement pst;
        Connection con = null;
        ConectareBD conectare = new ConectareBD();
        try {
            conectare.conectareBD();
            con = conectare.con;
            pst = con.prepareStatement("SELECT ProfilePicture FROM users WHERE username = ?");
            pst.setString(1, new PaginaConectare().getNumeUtilizator());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String path = rs.getString("ProfilePicture");
                if(path != null){
                    Image img = new Image(path);
                    pozaProfil.setImage(img);
                }
            }else{
                System.out.println("Erroare!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(con != null){
                    con.close();
                }
            }catch (SQLException exception){
                System.err.println("Eroare la închiderea conexiunii: " + exception.getMessage());
            }
        }
    }
    private void schimbaPozaProfil() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectati o imagine");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            url = file.toURI().toURL().toString();
            Image image = new Image(url);
            pozaProfil.setImage(image);
        }
        PreparedStatement pst;
        Connection con = null;
        ConectareBD conectare = new ConectareBD();
        try {
            conectare.conectareBD();
            con = conectare.con;
            pst = con.prepareStatement("UPDATE users SET ProfilePicture = ? WHERE username = ?");
            pst.setString(1, url);
            pst.setString(2, new PaginaConectare().getNumeUtilizator());
            pst.executeUpdate();
        }catch (SQLException e){
            System.err.println("Eroare la închiderea conexiunii: " + e.getMessage());

        }finally {
            try{
                if(con != null){
                    con.close();
                }
            }catch (SQLException exception){
                System.err.println("Eroare la închiderea conexiunii: " + exception.getMessage());
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afisareDateCont();
        schimbaPoza.setOnMouseClicked(e -> {
            try {
                schimbaPozaProfil();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });
        Circle clip = new Circle(65, 65, 65);
        pozaProfil.setClip(clip);
    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cont.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 550);
        String css = Objects.requireNonNull(this.getClass().getResource("styles/cont.css")).toExternalForm();
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
