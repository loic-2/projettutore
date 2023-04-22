package com.example.models;

import lombok.Data;

@Data
public abstract class Personne {
    private String nom;
    private String numero_assurance;
    private String adresse;
    private String telephone;
    private int id_personne;
}
