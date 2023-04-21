package com.example.controllers;

import java.io.IOException;

import com.example.App;
import com.example.mapping.MedecinMap;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class MedecinController {

    private ObservableList<MedecinMap> medecinMaps= FXCollections.observableArrayList();
    @FXML
    private TableColumn<MedecinMap, HBox> action;

    @FXML
    private TableColumn<MedecinMap, String> matricule;

    @FXML
    private TableView<MedecinMap> medecin;

    @FXML
    private TableColumn<MedecinMap, String> nom;

    @FXML
    private TableColumn<MedecinMap, String> specialite;

    @FXML
    private TableColumn<MedecinMap, String> telephone;

    @FXML
    private MFXTextField rechercher;

    @FXML
    private void initialize(){
        rechercher.setTrailingIcon(new MaterialIconView(MaterialIcon.SEARCH, "24px"));
        castDataToColumn();
        addRowToTable("Ganno", "355ERRGF", "654887665", "Chirugien");
    }

    @FXML
    private void openPopUp() throws IOException{
        App.popUpLaunch("addMedecin");
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

    private void addRowToTable(String nom,String matricule,String telephone,String specialite){
        MedecinMap med= new MedecinMap();
        med.getMedecin().setMatricule(matricule);
        med.getMedecin().setNom(nom);
        med.getMedecin().setSpecialite(specialite);
        med.getMedecin().setTelephone(telephone);
        medecinMaps.add(med);
        medecin.setItems(medecinMaps);
    }

    private void castDataToColumn(){
        matricule.setStyle("-fx-alignment:center;");
        specialite.setStyle("-fx-alignment:center;");
        telephone.setStyle("-fx-alignment:center;");
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getNom()));
        action.setCellValueFactory(new PropertyValueFactory<>("buttons"));
        matricule.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getMatricule()));
        specialite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getSpecialite()));
        telephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getTelephone()));
    }
}
