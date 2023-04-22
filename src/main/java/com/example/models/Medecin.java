package com.example.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Medecin extends Personne{
    private String matricule;
    private int id_medecin;
    private String specialite;
}
