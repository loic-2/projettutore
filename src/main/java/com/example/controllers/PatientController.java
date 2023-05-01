package com.example.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.App;
import com.example.mapping.PatientMap;
import com.example.models.Patient;
import com.example.services.PatientService;
import com.example.services.implementation.PatientServiceImpl;

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

public class PatientController {

    private ObservableList<PatientMap> patientMaps= FXCollections.observableArrayList();

    @FXML
    private MFXTextField rechercher;

    @FXML
    private TableColumn<PatientMap, HBox> action;

    @FXML
    private TableColumn<PatientMap, String> adresse;

    @FXML
    private TableColumn<PatientMap, String> assurance;

    @FXML
    private TableColumn<PatientMap, String> telephone;

    @FXML
    private TableColumn<PatientMap, String> nom;

    @FXML
    private TableView<PatientMap> patients;

    PatientService patientService;

    public PatientController(){
        this.patientService= new PatientServiceImpl();
    }

    @FXML
    private void initialize(){
        rechercher.setTrailingIcon(new MaterialIconView(MaterialIcon.SEARCH, "24px"));
        castDataToColumn();
        updateTable();
    }
    
    @FXML
    private void openPopUp() throws IOException{
        AddPatientController.modifier=false;
        AddPatientController.patient=new Patient();
        App.popUpLaunch("addPatient");
        updateTable();
    }
    
    @FXML
    private void exporter(){

    }

    @FXML
    private void refresh(){
        updateTable();
    }

    @FXML
    private void recherche(){

    }

    @FXML
    public void supprimer() {
        this.patientService.deletePatient(patients.getSelectionModel().getSelectedItem().getPatient().getId_patient());
        updateTable();
    }

    private void updateTable(){
        patientMaps.clear();
        try {
            addRowToTable(this.patientService.getAllPatient());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        patients.setItems(patientMaps);
    }

    private void addRowToTable(List<Patient> patients){
        for (Patient patient : patients) {
            PatientMap patientMap= new PatientMap();
            patientMap.setPatient(patient);
            patientMaps.add(patientMap);
        }
    }

    private void castDataToColumn(){
        adresse.setStyle("-fx-alignment:center");
        assurance.setStyle("-fx-alignment: center");
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getNom()));
        adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getAdresse()));
        telephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getTelephone()));
        assurance.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getPatient().getNumero_assurance()));
        action.setCellValueFactory(new PropertyValueFactory<>("buttons"));
    }
}
