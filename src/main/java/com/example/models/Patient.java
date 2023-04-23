package com.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static Patient serializeResultToPatient(ResultSet resultSet) throws SQLException{
        Patient patient= new Patient();
        patient.setAdresse(resultSet.getString("adresse"));
        patient.setAntecedent(resultSet.getString("antecedent"));
        patient.setGroupeRhesus(resultSet.getString("groupe_rhesus"));
        patient.setGroupeSanguin(resultSet.getString("groupe_sanguin"));
        patient.setId_patient(resultSet.getInt("id_patient"));
        patient.setId_personne(resultSet.getInt("id_personne"));
        patient.setNom(resultSet.getString("nom"));
        patient.setTaille(resultSet.getDouble("taille"));
        patient.setPoids(resultSet.getDouble("poids"));
        patient.setTelephone(resultSet.getString("telephone"));
        patient.setNumero_assurance(resultSet.getString("numero_assurance"));
        return patient;
    }
}
