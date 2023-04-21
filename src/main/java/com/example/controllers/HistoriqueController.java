package com.example.controllers;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class HistoriqueController {

    @FXML
    private MFXTextField rechercher;

    @FXML
    private void initialize(){
        rechercher.setTrailingIcon(new MaterialIconView(MaterialIcon.SEARCH, "24px"));
    }
    
    @FXML
    private void exporter(){

    }

    @FXML
    private void refresh(){

    }

    @FXML
    private void recherche(){

    }
}
