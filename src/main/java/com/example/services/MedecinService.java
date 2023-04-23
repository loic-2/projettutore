package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.error.ServiceResponse;
import com.example.models.Medecin;

public interface MedecinService {
    ServiceResponse addMedecin(Medecin medecin);
    ServiceResponse modifyMedecin(Medecin medecin);
    ServiceResponse deleteMedecin(int id_medecin);
    List<Medecin> getAllMedecin(Boolean isSpecialiste) throws SQLException;
    Medecin getMedecin(int id_medecin,Boolean isSpecialiste) throws SQLException;
}
