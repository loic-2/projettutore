package com.example.mapping;

import com.example.models.Medecin;

import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = false)
@Data
public class MedecinMap extends MapButton{
    private Medecin medecin;

    public MedecinMap(){
        super();
        medecin= new Medecin();
        addEventTobutton();
    }

    public void addEventTobutton(){
        supprimer.setOnAction(e -> {
            System.out.println("Supprimer medecin: "+medecin.getNom());
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
