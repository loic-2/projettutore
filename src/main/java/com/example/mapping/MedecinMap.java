package com.example.mapping;

import java.io.IOException;

import com.example.App;
import com.example.controllers.AddMedecinController;
import com.example.controllers.ViewMedecinController;
import com.example.models.Medecin;
import com.example.services.MedecinService;
import com.example.services.implementation.MedecinServiceImpl;

import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = false)
@Data
public class MedecinMap extends MapButton{
    private Medecin medecin;
    private MedecinService medecinService;

    public MedecinMap(){
        super();
        medecin= new Medecin();
        medecinService= new MedecinServiceImpl();
        addEventTobutton();
    }

    public void addEventTobutton(){
        supprimer.setOnAction(e -> {
            this.medecinService.deleteMedecin(this.getMedecin().getId_medecin());
        });

        modifier.setOnAction(e -> {
            try {
                AddMedecinController.medecin=this.getMedecin();
                AddMedecinController.modifier=true;
                App.popUpLaunch("addMedecin");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        view.setOnAction(e -> {
            ViewMedecinController.medecin=this.getMedecin();
            try {
                App.popUpLaunch("viewMedecin");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        download.setOnAction(e -> {
            System.out.println("Telecharger medecin: "+medecin.getNom());
        });
    }
}
