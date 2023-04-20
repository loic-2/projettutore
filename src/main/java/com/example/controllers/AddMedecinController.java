package com.example.controllers;

import java.io.IOException;

import com.example.App;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class AddMedecinController {

    @FXML
    private static MFXTextField specialiste;

    @FXML
    private static MFXCheckbox isSpecialiste;

    @FXML
    private void initialize(){
        isSpecialiste.setDisable(false);
    }

    @FXML
    private void openPopUp() throws IOException{
        App.popUpLaunch("addMedecin");
    }
    @FXML
    private void exporter(){

    }

    @FXML
    private void save(){
        System.out.println(specialiste.getText().toString());
    }

    public static MFXTextField getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(MFXTextField specialiste) {
        AddMedecinController.specialiste = specialiste;
    }

    public static MFXCheckbox getIsSpecialiste() {
        return isSpecialiste;
    }

    public void setIsSpecialiste(MFXCheckbox isSpecialiste) {
        AddMedecinController.isSpecialiste = isSpecialiste;
    }

    @FXML
    private void closePopup(){
        
    }

}
