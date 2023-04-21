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
    }
}
