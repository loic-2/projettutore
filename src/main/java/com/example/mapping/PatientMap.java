package com.example.mapping;

import com.example.models.Patient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class PatientMap extends MapButton{
    private Patient patient;

    public PatientMap(){
        super();
        patient= new Patient();
    }
}
