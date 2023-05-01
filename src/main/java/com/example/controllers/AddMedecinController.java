package com.example.controllers;
import com.example.App;
import com.example.models.Medecin;
import com.example.services.MedecinService;
import com.example.services.implementation.MedecinServiceImpl;
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

    private MedecinService medecinService;
    public static Medecin medecin=new Medecin();
    public static Boolean modifier=false;

    public AddMedecinController(){
        this.medecinService= new MedecinServiceImpl();
    }

    public AddMedecinController(Medecin medecin){
        this.medecinService= new MedecinServiceImpl();
    }

    @FXML
    void initialize() {
        isSpecialiste.setDisable(true);
        activeSpecialisteField(setspecialiste);
        if (modifier) {
            title.setText("Modifier le Medecin");
            nom.setText(medecin.getNom());
            matricule.setText(medecin.getMatricule());
            specialiste.setText(medecin.getSpecialite());
            telephone.setText(medecin.getTelephone());
            adresse.setText(medecin.getAdresse());
            numero_assurance.setText(medecin.getNumero_assurance());
        }
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

        // Tout est OK, on peut enregistrer le médecin
        mapFieldsToMedecin();
        if (modifier) {
            System.out.println(medecinService.modifyMedecin(medecin));;
        } else {
            System.out.println(medecinService.addMedecin(medecin));;
        }
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

    public void mapFieldsToMedecin(){
        medecin.setNom(nom.getText().toString());
        medecin.setMatricule(matricule.getText().toString());
        medecin.setTelephone(telephone.getText().toString());
        medecin.setAdresse(adresse.getText().toString());
        medecin.setNumero_assurance(numero_assurance.getText().toString());
        if (isSpecialiste.isSelected()) {
            medecin.setSpecialite(specialiste.getText().toString());
        }
    }

}
