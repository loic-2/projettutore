package com.example.controllers;

import com.example.App;
import com.example.error.Response;
import com.example.models.Patient;
import com.example.services.PatientService;
import com.example.services.implementation.PatientServiceImpl;
import com.jfoenix.controls.JFXTextArea;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import lombok.Getter;
import lombok.Setter;

public class AddPatientController {

    @FXML
    private MFXTextField nom;
    @FXML
    private MFXTextField poid;
    @FXML
    private MFXTextField taille;

    @FXML
    private MFXTextField numeroSecurite;

    @FXML
    private MFXTextField adresse;

    @FXML
    private JFXTextArea antecedent;

    @FXML
    private MFXTextField telephone;

    @FXML
    private Label title;

    @FXML
    private Label erreur;

    private ToggleGroup groupe;

    private ToggleGroup rhesus;

    @FXML
    private MFXRadioButton groupeA;

    @FXML
    private MFXRadioButton groupeB;

    @FXML
    private MFXRadioButton groupeAB;

    @FXML
    private MFXRadioButton groupeO;

    @FXML
    private MFXRadioButton rhesusPositif;

    @FXML
    private MFXRadioButton rhesusNegatif;

    @FXML
    private MFXDatePicker date;

    public static Boolean modifier=false;

    @Getter
    @Setter
    private Response response;

    public static Patient patient= new Patient();
    private PatientService patientService;

    public AddPatientController(){
        patientService= new PatientServiceImpl();
    }

    @FXML
    private void initialize(){
        nom.setLeadingIcon(new MaterialIconView(MaterialIcon.PERSON,"24px"));
        nom.setGraphicTextGap(3);
        telephone.setLeadingIcon(new MaterialIconView(MaterialIcon.PHONE,"24px"));
        adresse.setLeadingIcon(new MaterialIconView(MaterialIcon.PLACE,"24px"));
        numeroSecurite.setLeadingIcon(new MaterialIconView(MaterialIcon.SECURITY,"24px"));
        taille.setLeadingIcon(new MaterialIconView(MaterialIcon.FORMAT_SIZE,"24px"));
        groupe= new ToggleGroup();
        rhesus= new ToggleGroup();
        groupeA.setToggleGroup(groupe);
        groupeB.setToggleGroup(groupe);
        groupeAB.setToggleGroup(groupe);
        groupeO.setToggleGroup(groupe);
        rhesusNegatif.setToggleGroup(rhesus);
        rhesusPositif.setToggleGroup(rhesus);

        //initialisation des champs
        if (modifier) {
            title.setText("Modifier le Patient");
            nom.setText(patient.getNom());
            numeroSecurite.setText(patient.getNumero_assurance());
            telephone.setText(patient.getTelephone());
            taille.setText(String.valueOf(patient.getTaille()));
            poid.setText(String.valueOf(patient.getPoids()));
            antecedent.setText(patient.getAntecedent());
            adresse.setText(patient.getAdresse());
            groupe.getToggles().forEach(toggle -> {
                if (toggle instanceof MFXRadioButton) {
                    MFXRadioButton radioButton = (MFXRadioButton) toggle;
                    if (radioButton.getText().equals(patient.getGroupeSanguin())) {
                        groupe.selectToggle(radioButton);
                    }
                }
            });
            rhesus.getToggles().forEach(toggle -> {
                if (toggle instanceof MFXRadioButton) {
                    MFXRadioButton radioButton = (MFXRadioButton) toggle;
                    if (radioButton.getText().equals(patient.getGroupeRhesus())) {
                        rhesus.selectToggle(radioButton);
                    }
                }
            });
        }
    }

    @FXML
    public void enregistrer(){

        //verification que les champs ne sont pas vides
        if (nom.getText().isEmpty() || adresse.getText().isEmpty() || taille.getText().isEmpty() ||
        poid.getText().isEmpty() || numeroSecurite.getText().isEmpty() || telephone.getText().isEmpty()
        || antecedent.getText().isEmpty()) {
            erreur.setText("Tous les champs sont obligatoires");
            erreur.setVisible(true);
            return;
        }

        if (date.getValue()==null) {
            erreur.setText("Vous devez selectionner la date de naissance");
            erreur.setVisible(true);
            return;
        }

        if (groupe.getSelectedToggle()==null) {
            erreur.setText("Vous devez selectionner votre groupe sanguin");
            erreur.setVisible(true);
            return;
        }

        if (rhesus.getSelectedToggle()==null) {
            erreur.setText("Vous devez selectionner votre rhesus");
            erreur.setVisible(true);
            return;
        }

        erreur.setVisible(false);
        mapFieldsToPatient();
        
        if (modifier) {
            response=patientService.modifyPatient(patient);
        } else {
            response=patientService.addPatient(patient);
        }
        if (response.getCode()==200) {
            App.closePopUp();
            renitialiser();
        } else {
            erreur.setText(response.getMessage());
            erreur.setVisible(true);
        }
    }

    @FXML
    public void renitialiser(){
        nom.clear();
        date.clear();
        poid.clear();
        antecedent.clear();
        numeroSecurite.clear();
        adresse.clear();
        telephone.clear();
        taille.clear();
    }

    public void mapFieldsToPatient(){
        MFXRadioButton seleButton= (MFXRadioButton) groupe.getSelectedToggle();
        MFXRadioButton selButtonRhe= (MFXRadioButton) rhesus.getSelectedToggle();
        patient.setAdresse(adresse.getText().toString());
        patient.setAntecedent(antecedent.getText().toString());
        patient.setGroupeRhesus(selButtonRhe.getText().toString());
        patient.setGroupeSanguin(seleButton.getText());
        patient.setPoids(Double.parseDouble(poid.getText().toString()));
        patient.setTaille(Double.parseDouble(taille.getText().toString()));
        patient.setNumero_assurance(numeroSecurite.getText().toString());
        patient.setTelephone(telephone.getText().toString());
        patient.setNom(nom.getText().toString());

    }
    
}
