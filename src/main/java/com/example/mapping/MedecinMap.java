package com.example.mapping;

import java.sql.CallableStatement;
import com.example.models.Medecin;
import com.example.services.MedecinService;
import com.example.services.implementation.MedecinServiceImpl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
@EqualsAndHashCode(callSuper = false)
@Data
public class MedecinMap extends MapButton{
    private Medecin medecin;
    
    @Exclude
    private CallableStatement call;
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
            System.out.println("Modifier medecin: "+medecin.getNom());
        });

        view.setOnAction(e -> {
            System.out.println("Afficher medecin: "+medecin.getNom());
        });

        download.setOnAction(e -> {
            System.out.println("Telecharger medecin: "+medecin.getNom());
        });
    }
}
