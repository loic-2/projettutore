package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.App;
import com.example.mapping.MedecinMap;
import com.example.models.Medecin;
import com.example.services.MedecinService;
import com.example.services.implementation.MedecinServiceImpl;

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

public class MedecinController extends MainController{

    private ObservableList<MedecinMap> medecinMaps= FXCollections.observableArrayList();
    @FXML
    private TableColumn<MedecinMap, HBox> action;

    static boolean isSpecialiste=false;

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

    private MedecinService medecinService;

    public MedecinController(){
        medecinService= new MedecinServiceImpl();
    }

    @FXML
    private void initialize(){
        rechercher.setTrailingIcon(new MaterialIconView(MaterialIcon.SEARCH, "24px"));
        if (!isSpecialiste) {
            medecin.getColumns().get(3).setText("Adresse");
        }
        castDataToColumn();
        updateTable();
    }

    @FXML
    private void openPopUp() throws IOException{
        AddMedecinController.modifier=false;
        AddMedecinController.medecin=new Medecin();
        App.popUpLaunch("addMedecin");
        updateTable();
    }
    
    @FXML
    protected void exporter(){
        
    }

    @FXML
    private void refresh(){
        updateTable();
    }

    @FXML
    private void supprimer(){
        deletItemFromTable(medecin.getSelectionModel().getSelectedItem());
        updateTable();
    }

    @FXML
    private void recherche(){
        System.out.println(rechercher.getText().toString());
    }

    private void addRowToTable(List<Medecin> medecins){
        for (Medecin medecin : medecins) {
            MedecinMap med= new MedecinMap();
            med.setMedecin(medecin);
            medecinMaps.add(med);
        }
        medecin.setItems(medecinMaps);
    }

    private void castDataToColumn(){
        matricule.setStyle("-fx-alignment:center;");
        specialite.setStyle("-fx-alignment:center;");
        telephone.setStyle("-fx-alignment:center;");
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getNom()));
        action.setCellValueFactory(new PropertyValueFactory<>("buttons"));
        matricule.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getMatricule()));
        if (isSpecialiste) {
            specialite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getSpecialite()));
        }else{
            specialite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getAdresse()));
        }
        telephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getTelephone()));
    }

    public void deletItemFromTable(MedecinMap medecinMap){
            this.medecinService.deleteMedecin(medecinMap.getMedecin().getId_medecin());
    }

    public void updateTable(){
        medecinMaps.clear();
        try {
            addRowToTable(this.medecinService.getAllMedecin(isSpecialiste));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
