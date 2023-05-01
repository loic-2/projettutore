package com.example.mapping;

import java.io.IOException;

import com.example.App;
import com.example.controllers.AddPatientController;
import com.example.controllers.ViewPatientController;
import com.example.models.Patient;
import com.example.services.PatientService;
import com.example.services.implementation.PatientServiceImpl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class PatientMap extends MapButton{
    private Patient patient;
    private PatientService patientService;

    public PatientMap(){
        super();
        patient= new Patient();
        patientService= new PatientServiceImpl();
        addEventTobutton();
    }

    public void addEventTobutton(){
        supprimer.setOnAction(e->{
            this.patientService.deletePatient(this.patient.getId_patient());
        });
        modifier.setOnAction(e->{
            AddPatientController.patient=this.getPatient();
            AddPatientController.modifier=true;
            try {
                App.popUpLaunch("addPatient");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        view.setOnAction(e->{
            try {
                ViewPatientController.patient=this.getPatient();
                App.popUpLaunch("viewPatient");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        download.setOnAction(e->{
            
        });
    }
}
