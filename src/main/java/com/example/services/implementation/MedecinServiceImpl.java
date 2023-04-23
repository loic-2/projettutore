package com.example.services.implementation;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.Connexion;
import com.example.error.ServiceResponse;
import com.example.models.Medecin;
import com.example.services.MedecinService;

public class MedecinServiceImpl implements MedecinService{

    private ServiceResponse serviceResponse;
    private CallableStatement call;
    ResultSet resultSet;
    private PreparedStatement query;

    public MedecinServiceImpl(){
        this.serviceResponse= new ServiceResponse();
    }

    @Override
    public ServiceResponse addMedecin(Medecin medecin) {
            // Tout est OK, on peut enregistrer le médecin
            try {
                call = Connexion.getConncetion().prepareCall("CALL addMedecin(?,?,?,?,?,?,?)");
                call.setString(1, medecin.getNom());
                call.setString(2, medecin.getMatricule());
                call.setString(4, medecin.getMatricule());
                call.setString(5, medecin.getTelephone());
                call.setString(6, medecin.getNumero_assurance());
                call.setBoolean(7, medecin.getSpecialite()==null? false: true);
                if (medecin.getSpecialite()!=null) {
                    call.setString(3, medecin.getSpecialite());
                } else {
                    call.setString(3, null);
                }
                call.execute();
                serviceResponse.setResponse(200, "Insertion du medecin reussi");
            } catch (SQLException e) {
                serviceResponse.setResponse(510, e.getMessage());
            }
        return serviceResponse;
    }

    @Override
    public ServiceResponse modifyMedecin(Medecin medecin) {
        try {
            call= Connexion.getConncetion().prepareCall("CALL modifyMedecin(?,?,?,?,?,?,?,?)");
            call.setString(1, medecin.getNom());
            call.setString(2, medecin.getAdresse());
            call.setString(3, medecin.getTelephone());
            call.setString(4, medecin.getNumero_assurance());
            call.setString(5, medecin.getMatricule());
            call.setString(6, medecin.getSpecialite());
            call.setInt(7, medecin.getId_medecin());
            call.setBoolean(8, medecin.getSpecialite()==null? false: true);
            call.execute();
            serviceResponse.setResponse(200, "Modification du medecin reussi.");
        } catch (SQLException e) {
            serviceResponse.setResponse(510, e.getMessage());
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse deleteMedecin(int id_medecin) {
        try {
            call= Connexion.getConncetion().prepareCall("CALL deleteMedecin(?)");
            call.setInt(1, id_medecin);
            call.execute();
            call.close();
            serviceResponse.setResponse(200, "Suppression reussi du medecin.");
        } catch (SQLException e) {
            serviceResponse.setResponse(510, e.getMessage());
        }
        return serviceResponse;
    }

    @Override
    public List<Medecin> getAllMedecin(Boolean isSpecialiste) throws SQLException {
        List<Medecin> medecins= new ArrayList<>();
        if (isSpecialiste) {
            query= Connexion.getConncetion().prepareStatement("SELECT * FROM medecinSpecialisteInfo");
        } else {
            query= Connexion.getConncetion().prepareStatement("SELECT * FROM medecinGeneralisteInfo");
        }
        resultSet= query.executeQuery();
        while (resultSet.next()) {
            medecins.add(Medecin.serializeResultToMedecin(resultSet,isSpecialiste));
        }
        return medecins;
    }

    @Override
    public Medecin getMedecin(int id_medecin,Boolean isSpecialiste) throws SQLException {
        if (isSpecialiste) {
            query= Connexion.getConncetion().prepareStatement("SELECT * FROM medecinSpecialisteInfo");
        } else {
            query= Connexion.getConncetion().prepareStatement("SELECT * FROM medecinGeneralisteInfo");
        }
        return Medecin.serializeResultToMedecin(query.executeQuery(),isSpecialiste);
    }
    
}
