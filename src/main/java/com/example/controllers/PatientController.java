package com.example.controllers;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXML;

public class PatientController {
    
    @FXML
    private void openAddPopUp() throws IOException{
        App.popUpLaunch("addPatient");
    }
}
