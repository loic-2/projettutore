package com.example.controllers;

import com.jfoenix.controls.JFXTextArea;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class AddPatientController {

    @FXML
    private MFXTextField nom;
    @FXML
    private MFXTextField poid;
    @FXML
    private MFXTextField taille;

    @FXML
    private MFXTextField numeroSecurite;

    @FXML
    private MFXTextField adresse;

    @FXML
    private JFXTextArea antecedent;

    @FXML
    private MFXTextField telephone;

    @FXML
    private Label erreur;

    private ToggleGroup groupe;

    private ToggleGroup rhesus;

    @FXML
    private MFXRadioButton groupeA;

    @FXML
    private MFXRadioButton groupeB;

    @FXML
    private MFXRadioButton groupeAB;

    @FXML
    private MFXRadioButton groupeO;

    @FXML
    private MFXRadioButton rhesusPositif;

    @FXML
    private MFXRadioButton rhesusNegatif;

    @FXML
    private MFXDatePicker date;

    @FXML
    private void initialize(){
        nom.setLeadingIcon(new MaterialIconView(MaterialIcon.PERSON,"24px"));
        nom.setGraphicTextGap(3);
        telephone.setLeadingIcon(new MaterialIconView(MaterialIcon.PHONE,"24px"));
        adresse.setLeadingIcon(new MaterialIconView(MaterialIcon.PLACE,"24px"));
        numeroSecurite.setLeadingIcon(new MaterialIconView(MaterialIcon.SECURITY,"24px"));
        taille.setLeadingIcon(new MaterialIconView(MaterialIcon.FORMAT_SIZE,"24px"));
        groupe= new ToggleGroup();
        rhesus= new ToggleGroup();
        groupeA.setToggleGroup(groupe);
        groupeB.setToggleGroup(groupe);
        groupeAB.setToggleGroup(groupe);
        groupeO.setToggleGroup(groupe);
        rhesusNegatif.setToggleGroup(rhesus);
        rhesusPositif.setToggleGroup(rhesus);
    }

    @FXML
    public void enregistrer(){
        System.out.println("nom: "+nom.getText());
        System.out.println("taille "+taille.getText());
        if (date.getValue()!=null) {
            System.out.println("date: "+date.getValue().toString());
        }
        System.out.println("antecedent: "+antecedent.getText());
        System.out.println("poid: "+poid.getText());
        System.out.println("numero: "+numeroSecurite.getText());
        System.out.println("tel: "+telephone.getText());
        System.out.println("adresse: "+adresse.getText());
        //System.out.println("groupe: "+groupe.getSelectedToggle().getUserData().toString());
        //System.out.println("rhesus: "+rhesus.getSelectedToggle().getUserData().toString());

        //verification que les champs ne sont pas vides
        if (nom.getText().isEmpty() || adresse.getText().isEmpty() || taille.getText().isEmpty() ||
        poid.getText().isEmpty() || numeroSecurite.getText().isEmpty() || telephone.getText().isEmpty()
        || antecedent.getText().isEmpty()) {
            erreur.setText("Tous les champs sont obligatoires");
            erreur.setVisible(true);
            return;
        }

        if (date.getValue()==null) {
            erreur.setText("Vous devez selectionner la date de naissance");
            erreur.setVisible(true);
            return;
        }

        if (groupe.getSelectedToggle()==null) {
            erreur.setText("Vous devez selectionner votre groupe sanguin");
            erreur.setVisible(true);
            return;
        }

        if (rhesus.getSelectedToggle()==null) {
            erreur.setText("Vous devez selectionner votre rhesus");
            erreur.setVisible(true);
            return;
        }

        erreur.setVisible(false);
    }

    @FXML
    public void renitialiser(){
        nom.clear();
        date.clear();
        poid.clear();
        antecedent.clear();
        numeroSecurite.clear();
        adresse.clear();
        telephone.clear();
        taille.clear();
    }
    
}
