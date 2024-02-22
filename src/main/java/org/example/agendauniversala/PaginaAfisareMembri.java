package org.example.agendauniversala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaginaAfisareMembri implements Initializable {
    @FXML
    private TableView<Membri> membriTabel;
    @FXML
    private TableColumn<Membri, String> numePrenumeCol;
    @FXML
    private TableColumn<Membri, String> numeUtilizatorCol;
    @FXML
    private TableColumn<Membri, String> emailCol;
    @FXML
    private TableColumn<Membri, String> telefonCol;
    @FXML
    private TableColumn<Membri, String> rolCol;
    @FXML
    private TableColumn<Membri, String> statusCol;
    @FXML
    public ChoiceBox<String> rol;
    @FXML
    public TextField campCautaNumeUtilizator;
    @FXML
    public Button inchideAfisareMembri;
    @FXML
    public Pane editarePane;
    @FXML
    public static AnchorPane afisareMembri;
    @FXML
    public TextField numeCamp;
    @FXML
    public TextField numeUtilizatorCamp;
    @FXML
    public TextField emailCamp;
    @FXML
    public TextField telefonCamp;
    @FXML
    public Button inchideCautare;
    @FXML
    public Label eroare;
    @FXML
    public Pane atentionareStergere;
    @FXML
    public Button stergereMembru;
    @FXML
    public Label eroareNume;
    @FXML
    public Label eroareNumeUtilizator;
    @FXML
    public Label eroareEmail;
    @FXML
    public Label eroareRol;
    @FXML
    public Label eroareTelefon;
    @FXML
    public Pane mesajSucces;
    @FXML
    public Pane containerAfisareMembri;
    @FXML
    public Label mesajInformare;
    @FXML
    public Label eroareEditare;
    private double xOffset = 0;
    private double yOffset = 0;
    private final String[] alegereRol = {"Admin", "Profesor", "Secretar", "Contabil"};
    ObservableList<Membri> listaM;
    public void reimprospatareTabelAct(ActionEvent e) {
        listaM = AfisareMembri.membri();
        membriTabel.setItems(listaM);
    }
    public  void inchideEditareMembru(ActionEvent e){
        editarePane.setVisible(false);
        inchideCautare.setVisible(false);
        campCautaNumeUtilizator.setText("");
        listaM = AfisareMembri.membri();
        membriTabel.setItems(listaM);
        containerAfisareMembri.setStyle("-fx-opacity: 1");
        CuratareCampuri.curataCampuriEditareMembri(rol, eroareNumeUtilizator, eroareNume, eroareEmail, eroareTelefon, eroareRol,
                eroareEditare, numeUtilizatorCamp, numeCamp, emailCamp, telefonCamp);
    }
    public void inchideCautareAct(ActionEvent e){
        listaM = AfisareMembri.membri();
        membriTabel.setItems(listaM);
        campCautaNumeUtilizator.setText("");
        campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");
        eroare.setText("");
        inchideCautare.setVisible(false);
    }
    public void editareMembru(ActionEvent e){
        if(campCautaNumeUtilizator.getText().isEmpty()){
            eroare.setText("Selectează un membru!");
            campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");
        }else{
            editarePane.setVisible(true);
            eroare.setText("");
            campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");
            containerAfisareMembri.setStyle("-fx-opacity: 0.5");

        }
    }
    public void anulareStergere(ActionEvent e){
        atentionareStergere.setVisible(false);
        containerAfisareMembri.setStyle("-fx-opacity: 1");
    }
    public void inchideMesajSucces(ActionEvent e){
        mesajSucces.setVisible(false);
        containerAfisareMembri.setStyle("-fx-opacity: 1");
    }

    public void cautaMembru(ActionEvent e){
        ConectareBD conectare = new ConectareBD();
        ObservableList<Membri> membru = FXCollections.observableArrayList();
        PreparedStatement pst;
        Connection con = null;
        if(campCautaNumeUtilizator.getText().isEmpty()){
            eroare.setText("Introduceți numele de utilizator!");
            campCautaNumeUtilizator.setStyle("-fx-border-color: #ec1a1a");
        }else{
            try {
                conectare.conectareBD();
                con = conectare.con;
                pst = con.prepareStatement("select name, username, email, phone, role, status from users where username = ?");
                pst.setString(1, campCautaNumeUtilizator.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String nume = rs.getString("name");
                    String utilizator = rs.getString("username");
                    String email = rs.getString("email");
                    String telefon = rs.getString("phone");
                    String rol = rs.getString("role");
                    String status = rs.getString("status");
                    membru.add(new Membri(nume, utilizator, email, telefon, rol, status));
                    numeCamp.setText(nume);
                    numeUtilizatorCamp.setText(utilizator);
                    emailCamp.setText(email);
                    telefonCamp.setText(telefon);
                    eroare.setText("");
                    campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");
                    inchideCautare.setVisible(true);

                }else{
                    eroare.setText("Utilizatorul nu se regăsește in baza de date!");
                    campCautaNumeUtilizator.setStyle("-fx-border-color: #ec1a1a");
                    inchideCautare.setVisible(true);
                }
                }catch (Exception ex) {
                    ex.printStackTrace();
            }
            finally {
                try{
                    if(con != null){
                        con.close();
                    }
                }catch (SQLException ex){
                    System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
                }
            }
        listaM = membru;
        membriTabel.setItems(listaM);
        }
    }

    public void stergereMembru(ActionEvent e){
        if(campCautaNumeUtilizator.getText().isEmpty()) {
            eroare.setText("Introduceți numele de utilizator sau selectați un membru!");
            campCautaNumeUtilizator.setStyle("-fx-border-color: #ec1a1a");
        }else{
            atentionareStergere.setVisible(true);
            containerAfisareMembri.setStyle("-fx-opacity: 0.5");
            stergereMembru.setOnMouseClicked(event -> {
                PreparedStatement pst;
                Connection con = null;
                ConectareBD conectare = new ConectareBD();
                try {
                    conectare.conectareBD();
                    con = conectare.con;
                    pst = con.prepareStatement("delete from users where username = ?");
                    pst.setString(1, campCautaNumeUtilizator.getText());
                    pst.executeUpdate();
                    eroare.setText("");
                    campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");
                    inchideCautare.setVisible(false);
                    campCautaNumeUtilizator.setText("");
                    listaM = AfisareMembri.membri();
                    membriTabel.setItems(listaM);
                    atentionareStergere.setVisible(false);
                    containerAfisareMembri.setStyle("-fx-opacity: 0.5");
                    mesajSucces.setVisible(true);
                    mesajInformare.setText("Membru șters cu succes!");
                }catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    try{
                        if(con != null){
                            con.close();
                        }
                    }catch (SQLException ex){
                        System.err.println("Eroare la închiderea conexiunii: " + ex.getMessage());
                    }
                }
            });
        }
    }
    public void afisareMembri(){
        numePrenumeCol.setCellValueFactory(new PropertyValueFactory<Membri, String>("numePrenume"));
        numeUtilizatorCol.setCellValueFactory(new PropertyValueFactory<Membri, String>("numeUtilizator"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Membri, String>("email"));
        telefonCol.setCellValueFactory(new PropertyValueFactory<Membri, String>("telefon"));
        rolCol.setCellValueFactory(new PropertyValueFactory<Membri, String>("rol"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Membri, String>("status"));
        statusCol.setCellFactory(column -> new TextFieldTableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setTextFill(Color.BLACK);
                } else {
                    setText(item);
                    if (item.equals("Inactiv")) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.GREEN);
                    }
                }
            }
        });
        listaM = AfisareMembri.membri();
        membriTabel.setItems(listaM);
    }
    public void randSelectat(MouseEvent e){
        Membri membruSelectat = membriTabel.getSelectionModel().getSelectedItem();
        if(membruSelectat != null){
            campCautaNumeUtilizator.setText(String.valueOf(membruSelectat.getNumeUtilizator()));
            numeUtilizatorCamp.setText(String.valueOf(membruSelectat.getNumeUtilizator()));
            numeCamp.setText(String.valueOf(membruSelectat.getNumePrenume()));
            emailCamp.setText(String.valueOf(membruSelectat.getEmail()));
            telefonCamp.setText(String.valueOf(membruSelectat.getTelefon()));
            rol.setValue(String.valueOf(membruSelectat.getRol()));
            eroare.setText("");
            campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");

        }else{
            eroare.setText("Selectează un membru!");
            campCautaNumeUtilizator.setStyle("-fx-border-color: #2a62bc");
        }
    }
    public void salvareEditareMembru(ActionEvent e){
        String email = emailCamp.getText();
        boolean esteValid = isValidEmailDomain(email);
        String nume = numeCamp.getText();
        String numeUtilizator = numeUtilizatorCamp.getText();
        String emailC = emailCamp.getText();
        String telefon = telefonCamp.getText();
        ValidareCampuri.validareEditareMembri(nume, numeUtilizator, emailC, telefon, rol, eroareNume, eroareNumeUtilizator,
                eroareEmail, eroareTelefon, eroareRol, eroareEditare, numeCamp, numeUtilizatorCamp, emailCamp, telefonCamp);
        if(numeCamp.getText().isEmpty() || numeUtilizatorCamp.getText().isEmpty() || emailCamp.getText().isEmpty() || telefonCamp.getText().isEmpty()
                || rol.getValue().isEmpty()) {
        }else if(telefonCamp.getText().length() != 10){
            eroareTelefon.setText("Numarul de telefon trebuie sa contina 10 caractere!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else if(esteValid) {
            telefonCamp.setStyle(null);
            eroareTelefon.setText("");
            PreparedStatement ps;
            Connection con = null;
            ConectareBD conectare = new ConectareBD();
            try {
                conectare.conectareBD();
                con = conectare.con;
                ps = con.prepareStatement("update users set name = ?, username = ?, email = ?, phone = ?, role = ? where username = ?");
                ps.setString(1, numeCamp.getText());
                ps.setString(2, numeUtilizatorCamp.getText());
                ps.setString(3, emailCamp.getText());
                ps.setString(4, telefonCamp.getText());
                ps.setString(5, rol.getValue());
                ps.setString(6, campCautaNumeUtilizator.getText());
                ps.executeUpdate();
                CuratareCampuri.curataCampuriEditareMembri(rol, eroareNumeUtilizator, eroareNume, eroareEmail, eroareTelefon, eroareRol,
                        eroareEditare, numeUtilizatorCamp, numeCamp, emailCamp, telefonCamp);
                mesajSucces.setVisible(true);
                editarePane.setVisible(false);
                containerAfisareMembri.setStyle("-fx-opacity: 0.5");
                mesajInformare.setText("Membru modificat cu succes!");
                listaM = AfisareMembri.membri();
                membriTabel.setItems(listaM);
            }catch(Exception ex) {
                eroareEditare.setText("Nume de utilizator, telefon sau email deja folosit!");
                telefonCamp.setStyle("-fx-border-color: #ec1a1a");
                numeUtilizatorCamp.setStyle("-fx-border-color: #ec1a1a");
                emailCamp.setStyle("-fx-border-color: #ec1a1a");
                rol.setStyle(null);
                numeCamp.setStyle(null);
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
            eroareEditare.setText("");
        }
    }
    public void curatareCampuri(ActionEvent e){
        CuratareCampuri.curataCampuriEditareMembri(rol, eroareNumeUtilizator, eroareNume, eroareEmail, eroareTelefon, eroareRol,
                eroareEditare, numeUtilizatorCamp, numeCamp, emailCamp, telefonCamp);
    }
    public boolean isValidEmailDomain(String email){
        String Pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        return email.matches(Pattern);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rol.getItems().addAll(alegereRol);
        afisareMembri();
    }
    public void inchidePagina(ActionEvent e){
        Stage stage = (Stage) inchideAfisareMembri.getScene().getWindow();
        stage.close();
    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("afisareMembri.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 750);
        String css = Objects.requireNonNull(this.getClass().getResource("styles/afisareMembri.css")).toExternalForm();
        String css2 = Objects.requireNonNull(this.getClass().getResource("styles/tabel.css")).toExternalForm();
        scene.getStylesheets().add(css);
        scene.getStylesheets().add(css2);
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
