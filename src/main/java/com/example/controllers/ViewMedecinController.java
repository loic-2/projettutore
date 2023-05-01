package com.example.controllers;

import com.example.models.Medecin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewMedecinController {
    @FXML
    private Label adresse;

    @FXML
    private Label matricule;

    @FXML
    private Label nom;

    @FXML
    private Label numero_assurance;

    @FXML
    private Label specialite;

    @FXML
    private Label telephone;

    public static Medecin medecin= new Medecin();

    public void initialize(){
        telephone.setText(medecin.getTelephone());
        nom.setText(medecin.getNom());
        matricule.setText(medecin.getMatricule());
        numero_assurance.setText(medecin.getNumero_assurance());
        adresse.setText(medecin.getAdresse());
        if (medecin.getSpecialite()==null) {
            specialite.setText("Generaliste");
        } else {
            specialite.setText(medecin.getSpecialite());
        }
    }

    @FXML
    private void telecharger(){
        
    }
}
