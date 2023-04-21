package com.example.controllers;
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
        if (!telephone.getText().matches("\\d{10}")) {
            errorLabel.setText("Le téléphone doit être un nombre à 9 chiffres");
            errorLabel.setVisible(true);
            return;
        }

        // Tout est OK, on peut enregistrer le médecin
        System.out.println("Nom : " + nom.getText());
        System.out.println("Matricule : " + matricule.getText());
        System.out.println("Spécialiste : " + isSpecialiste.isSelected());
        System.out.println("Spécialité : " + specialiste.getText());
        System.out.println("Téléphone : " + telephone.getText());
        System.out.println("Adresse : " + adresse.getText());
        reset();
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
