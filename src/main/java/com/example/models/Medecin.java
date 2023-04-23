package com.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static Medecin serializeResultToMedecin(ResultSet resultSet, Boolean isSpecialiste) throws SQLException{
        Medecin medecin= new Medecin();
        medecin.setAdresse(resultSet.getString("adresse"));
        medecin.setId_medecin(resultSet.getInt("id_medecin"));
        medecin.setId_personne(resultSet.getInt("id_personne"));
        medecin.setNom(resultSet.getString("nom"));
        medecin.setTelephone(resultSet.getString("telephone"));
        medecin.setNumero_assurance(resultSet.getString("numero_assurance"));
        medecin.setMatricule(resultSet.getString("matricule"));
        if (isSpecialiste) {
            medecin.setSpecialite(resultSet.getString("domaine"));
        }
        return medecin;
    }
}
