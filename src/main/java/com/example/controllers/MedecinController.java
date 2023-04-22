package com.example.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.App;
import com.example.database.Connexion;
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

    }

    private void addRowToTable(String nom,String matricule,String telephone,String specialite,String adresse){
        MedecinMap med= new MedecinMap();
        med.getMedecin().setMatricule(matricule);
        med.getMedecin().setNom(nom);
        med.getMedecin().setSpecialite(specialite);
        med.getMedecin().setTelephone(telephone);
        med.getMedecin().setAdresse(adresse);
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
        if (isSpecialiste) {
            specialite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getSpecialite()));
        }else{
            specialite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getAdresse()));
        }
        telephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getTelephone()));
    }

    public void deletItemFromTable(MedecinMap medecinMap){
        medecin.getItems().remove(medecinMap);
    }

    public void updateTable(){
        medecinMaps.clear();
        PreparedStatement preparedStatement;
        try {
            if (isSpecialiste) {
                preparedStatement = Connexion.getConncetion().prepareStatement("SELECT * FROM medecinSpecialisteInfo");
            } else {
                preparedStatement = Connexion.getConncetion().prepareStatement("SELECT * FROM medecinGeneralisteInfo");
            }
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()) {
                addRowToTable(resultSet.getString("nom"), resultSet.getString("matricule"), resultSet.getString("telephone"),
                 isSpecialiste? resultSet.getString("domaine"):"Aucune",resultSet.getString("adresse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
