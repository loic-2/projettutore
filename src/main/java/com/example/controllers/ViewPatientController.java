package com.example.controllers;

import com.example.models.Patient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ViewPatientController {
    @FXML
    private Label adresse;

    @FXML
    private Text antecedent;

    @FXML
    private Label groupe_sanguin;

    @FXML
    private Label nom;

    @FXML
    private Label numero_assurance;

    @FXML
    private Label poid;

    @FXML
    private Label rhesus;

    @FXML
    private Label taille;

    @FXML
    private Label telephone;

    public static Patient patient= new Patient();

    public void initialize(){
        nom.setText(patient.getNom());
        adresse.setText(patient.getAdresse());
        groupe_sanguin.setText(patient.getGroupeSanguin());
        rhesus.setText(patient.getGroupeRhesus());
        taille.setText(String.valueOf(patient.getTaille()));
        poid.setText(String.valueOf(patient.getPoids()));
        antecedent.setText(patient.getAntecedent());
        numero_assurance.setText(patient.getNumero_assurance());
        telephone.setText(patient.getTelephone());
    }

    @FXML
    void telecharger() {

    }
}
