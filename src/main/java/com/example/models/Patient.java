package com.example.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Patient extends Personne{
    private int id_patient;
    private double taille;
    private double poids;
    private String groupeSanguin;
    private String groupeRhesus;
    private String antecedent;
}
