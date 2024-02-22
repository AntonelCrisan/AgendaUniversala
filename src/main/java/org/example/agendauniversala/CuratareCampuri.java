package org.example.agendauniversala;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
public class CuratareCampuri {
    public static void curataCampuriAdaugareMembri(ChoiceBox<String> rol, Label eroareNumeUtilizator, Label eroareNume, Label eroareEmail, Label eroareTelefon,
                                                   Label eroareParola, Label eroareRol, TextField numeUtilizatorCamp, TextField numeCamp, TextField emailCamp,
                                     TextField telefonCamp, Pane parolaCamp, Label eroareAdaugare){
        numeCamp.setText("");
        numeUtilizatorCamp.setText("");
        emailCamp.setText("");
        telefonCamp.setText("");
        rol.setValue("");
        eroareNume.setText("");
        eroareNumeUtilizator.setText("");
        eroareEmail.setText("");
        eroareTelefon.setText("");
        eroareRol.setText("");
        eroareParola.setText("");
        telefonCamp.setStyle(null);
        rol.setStyle(null);
        numeCamp.setStyle(null);
        emailCamp.setStyle(null);
        numeUtilizatorCamp.setStyle(null);
        parolaCamp.setStyle(null);
        eroareAdaugare.setText("");
    }
    public static void curataCampuriEditareMembri(ChoiceBox<String> rol, Label eroareNumeUtilizator, Label eroareNume, Label eroareEmail,
                                                  Label eroareTelefon, Label eroareRol, Label eroareEditare, TextField numeUtilizatorCamp, TextField numeCamp,
                                                  TextField emailCamp, TextField telefonCamp){
        numeCamp.setText("");
        numeUtilizatorCamp.setText("");
        emailCamp.setText("");
        telefonCamp.setText("");
        rol.setValue("");
        eroareNume.setText("");
        eroareNumeUtilizator.setText("");
        eroareEmail.setText("");
        eroareTelefon.setText("");
        eroareRol.setText("");
        telefonCamp.setStyle(null);
        rol.setStyle(null);
        numeCamp.setStyle(null);
        emailCamp.setStyle(null);
        numeUtilizatorCamp.setStyle(null);
        eroareEditare.setText("");
    }
    public static void curataCampuriModificareEmail(Label eroareEmail, Label eroareConfEmail, Label eroare, TextField emailCamp,
                                                    TextField confEmailCamp){
        eroare.setText("");
        eroareEmail.setText("");
        eroareConfEmail.setText("");
        emailCamp.setStyle(null);
        confEmailCamp.setStyle(null);
        emailCamp.setText("");
        confEmailCamp.setText("");
    }
    public static void curataCampuriModificareParola(Label eroareParola, Label eroareConfParola, Label eroare, TextField parolaCamp,
                                                    TextField confParolaCamp){
        eroare.setText("");
        eroareParola.setText("");
        eroareConfParola.setText("");
        parolaCamp.setStyle(null);
        confParolaCamp.setStyle(null);
        parolaCamp.setText("");
        confParolaCamp.setText("");
    }
    public static void curataCampTelefon(Label eroareTelefon, Label eroare, TextField telefonCamp){
        eroareTelefon.setText("");
        eroare.setText("");
        telefonCamp.setText("");
        telefonCamp.setStyle(null);
    }
}
