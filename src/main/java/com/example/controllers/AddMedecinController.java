package com.example.controllers;
import java.sql.CallableStatement;
import java.sql.SQLException;

import com.example.App;
import com.example.database.Connexion;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AddMedecinController {

    @FXML
    private VBox root;

    public static Boolean setspecialiste=false;

    @FXML
    private BorderPane header;

    @FXML
    private Label title;

    @FXML
    private TextField nom;

    @FXML
    private TextField matricule;

    @FXML
    private CheckBox isSpecialiste;

    @FXML
    private TextField specialiste;

    @FXML
    private TextField telephone;

    @FXML
    private TextField adresse;

    @FXML
    private JFXTextField numero_assurance;

    @FXML
    private Label errorLabel;

    @FXML
    void initialize() {
        isSpecialiste.setDisable(true);
        activeSpecialisteField(setspecialiste);
    }


    @FXML
    void reset() {
        nom.clear();
        matricule.clear();
        isSpecialiste.setSelected(false);
        specialiste.clear();
        telephone.clear();
        adresse.clear();
        numero_assurance.clear();
        errorLabel.setVisible(false);
    }


    @FXML
    void save() {
        // Vérifier que le nom et le matricule ont été saisis
        if (nom.getText().isEmpty() || matricule.getText().isEmpty()) {
            errorLabel.setText("Le nom et le matricule sont obligatoires");
            errorLabel.setVisible(true);
            return;
        }

        // Vérifier que la spécialité est saisie si le médecin est un spécialiste
        if (isSpecialiste.isSelected() && specialiste.getText().isEmpty()) {
            errorLabel.setText("La spécialité est obligatoire pour un spécialiste");
            errorLabel.setVisible(true);
            return;
        }

        // Vérifier que le téléphone est bien un nombre à 10 chiffres
        if (!telephone.getText().matches("\\d{9}")) {
            errorLabel.setText("Le téléphone doit être un nombre à 9 chiffres");
            errorLabel.setVisible(true);
            return;
        }

        //Verifier que le numero d'assurance est bien renseigne
        if (numero_assurance.getText().isEmpty()) {
            errorLabel.setText("Vous devez entrer votre numero d'assurance");
            errorLabel.setVisible(true);
            return;
        }

        //Verifier que le matricule est bien renseigne
        if (matricule.getText().isEmpty()) {
            errorLabel.setText("Vous devez entrer votre matricule");
            errorLabel.setVisible(true);
            return;
        }

        //Verifier que l'adresse est bien renseigne
        if (adresse.getText().isEmpty()) {
            errorLabel.setText("Vous devez entrer votre adresse");
            errorLabel.setVisible(true);
            return;
        }

        //Masquer le message d'erreur si tout est correcte
        errorLabel.setVisible(false);

        try (// Tout est OK, on peut enregistrer le médecin
        CallableStatement call = Connexion.getConncetion().prepareCall("CALL addMedecin(?,?,?,?,?,?,?)")) {
            call.setString(1, nom.getText().toString());
            call.setString(2, matricule.getText());
            call.setString(4, adresse.getText().toString());
            call.setString(5, telephone.getText().toString());
            call.setString(6, numero_assurance.getText().toString());
            call.setBoolean(7, isSpecialiste.isSelected());
            if (isSpecialiste.isSelected()) {
                call.setString(3, specialiste.getText().toString());
            } else {
                call.setString(3, null);
            }
            call.execute();
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'insertion");
            e.printStackTrace();
        }

        System.out.println("Nom : " + nom.getText());
        System.out.println("Matricule : " + matricule.getText());
        System.out.println("Spécialiste : " + isSpecialiste.isSelected());
        System.out.println("Spécialité : " + specialiste.getText());
        System.out.println("Téléphone : " + telephone.getText());
        System.out.println("Adresse : " + adresse.getText());
        System.out.println("Numero assurance : " + numero_assurance.getText());
        reset();
        App.closePopUp();
    }

    public void activeSpecialisteField(Boolean value){
        if (value) {
            specialiste.setDisable(false);
            isSpecialiste.setSelected(true);
        } else {
            specialiste.setDisable(true);
            isSpecialiste.setSelected(false);
        }
    }

}
