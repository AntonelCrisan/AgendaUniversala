package org.example.agendauniversala;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;

public class ValidareCampuri {
    public static void validareConectare(String numeUtilizator, String parola, Label eroareNumeUtilizator, Label eroareParola, Label eroareConectare, TextField numeUtilizatorCamp, TextField parolaCamp) {
       if(numeUtilizator.isEmpty()) {
           eroareNumeUtilizator.setText("Introduceți numele de utilizator!");
           numeUtilizatorCamp.setStyle("-fx-border-color: #ec1a1a");
           eroareConectare.setText("");
        }else{
           numeUtilizatorCamp.setStyle(null);
           eroareNumeUtilizator.setText("");
       }
       if (parola.isEmpty()) {
            eroareParola.setText("Introduceți parola!");
            parolaCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareConectare.setText("");
        }else{
           parolaCamp.setStyle(null);
           eroareParola.setText("");
       }
    }
    public static void validareAdaugareMembri(String nume, String numeUtilizator, String email, String telefon, String parola,
                                              ChoiceBox<String> rol, Label eroareNume, Label eroareNumeUtilizator, Label eroareEmail,
                                              Label eroareTelefon, Label eroareParola, Label eroareRol, Label eroareAdaugare,
                                              TextField numeCamp, TextField numeUtilizatorCamp, TextField emailCamp, TextField telefonCamp,
                                              Pane parolaCamp){
        if(nume.isEmpty()) {
            eroareNume.setText("Introduceți numele și prenumele!");
            numeCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            numeCamp.setStyle(null);
            eroareNume.setText(null);
        }
        if(numeUtilizator.isEmpty()) {
            eroareNumeUtilizator.setText("Introduceți numele de utilizator!");
            numeUtilizatorCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            numeUtilizatorCamp.setStyle(null);
            eroareNumeUtilizator.setText(null);
        }
        if(email.isEmpty()){
            eroareEmail.setText("Introduceti email-ul!");
            emailCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            emailCamp.setStyle(null);
            eroareEmail.setText(null);
        }
        if(telefon.isEmpty()){
            eroareTelefon.setText("Introduceti numărul de telefon!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            eroareTelefon.setText(null);
            telefonCamp.setStyle(null);
        }
        if(telefon.isEmpty()) {
            eroareTelefon.setText("Introduceti numărul de telefon!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            eroareTelefon.setText(null);
            telefonCamp.setStyle(null);
        }
        if(parola.isEmpty()){
            eroareParola.setText("Generați o parolă!");
            parolaCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            eroareParola.setText(null);
            parolaCamp.setStyle(null);
        }
        if(rol.getValue() == null){
            eroareRol.setText("Alegeți rolul noului membru!");
            rol.setStyle("-fx-border-color: #ec1a1a");
            eroareAdaugare.setText("");
        }else{
            eroareRol.setText(null);
            rol.setStyle(null);
        }
    }
    public static void validareEditareMembri(String nume, String numeUtilizator, String email, String telefon,
                                              ChoiceBox<String> rol, Label eroareNume, Label eroareNumeUtilizator, Label eroareEmail,
                                              Label eroareTelefon, Label eroareRol, Label eroareEditare,
                                              TextField numeCamp, TextField numeUtilizatorCamp, TextField emailCamp, TextField telefonCamp){
        if(nume.isEmpty()) {
            eroareNume.setText("Introduceți numele și prenumele!");
            numeCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else{
            numeCamp.setStyle(null);
            eroareNume.setText(null);
        }
        if(numeUtilizator.isEmpty()) {
            eroareNumeUtilizator.setText("Introduceți numele de utilizator!");
            numeUtilizatorCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else{
            numeUtilizatorCamp.setStyle(null);
            eroareNumeUtilizator.setText(null);
        }
        if(email.isEmpty()){
            eroareEmail.setText("Introduceti email-ul!");
            emailCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else{
            emailCamp.setStyle(null);
            eroareEmail.setText(null);
        }
        if(telefon.isEmpty()){
            eroareTelefon.setText("Introduceti numărul de telefon!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else{
            eroareTelefon.setText(null);
            telefonCamp.setStyle(null);
        }
        if(telefon.isEmpty()) {
            eroareTelefon.setText("Introduceti numărul de telefon!");
            telefonCamp.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else{
            eroareTelefon.setText(null);
            telefonCamp.setStyle(null);
        }
        if(rol.getValue().isEmpty()){
            eroareRol.setText("Alegeți rolul noului membru!");
            rol.setStyle("-fx-border-color: #ec1a1a");
            eroareEditare.setText("");
        }else{
            eroareRol.setText(null);
            rol.setStyle(null);
        }
    }
}
