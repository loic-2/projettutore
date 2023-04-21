package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Medecin {
    private String matricule;
    private String nom;
    private String telephone;
    private String specialite;
}
