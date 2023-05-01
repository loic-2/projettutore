package com.example.controllers;

import com.example.services.CountService;
import com.example.services.implementation.CountServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label nbreConsultations;

    @FXML
    private Label nbreFiche;

    @FXML
    private Label nbreMedecins;

    @FXML
    private Label nbrePatients;

    @FXML
    private Label nbreUtilisateurs;

    private CountService countService;

    public DashboardController(){
        countService= new CountServiceImpl();
    }

    public void initialize(){
        nbreMedecins.setText(Integer.toString(this.countService.countRowDataFromDatabaseTable("Medecin")));
        nbreFiche.setText(Integer.toString(this.countService.countRowDataFromDatabaseTable("Feuille_Maladie")));
        nbreConsultations.setText(Integer.toString(this.countService.countRowDataFromDatabaseTable("Consultation")));
        nbrePatients.setText(Integer.toString(this.countService.countRowDataFromDatabaseTable("Patient")));
        nbreUtilisateurs.setText(Integer.toString(this.countService.countRowDataFromDatabaseTable("Utilisateur")));
    }
}
