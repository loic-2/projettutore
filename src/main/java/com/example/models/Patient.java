package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Patient {
    private String nom;
    private String numeroSecurite;
    private String telephone;
    private String adresse;
    private double taille;
    private double poids;
    private String groupeSanguin;
    private String groupeRhesus;
}
