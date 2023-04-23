package com.example.services.implementation;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.Connexion;
import com.example.error.ServiceResponse;
import com.example.models.Patient;
import com.example.services.PatientService;

public class PatientServiceImpl implements PatientService{

    private CallableStatement call;
    private PreparedStatement query;
    ResultSet resultSet;
    ServiceResponse serviceResponse;


    public PatientServiceImpl() {
        this.serviceResponse = new ServiceResponse();
    }


    @Override
    public ServiceResponse addPatient(Patient patient){
        try {
            call= Connexion.getConncetion().prepareCall("CALL addPatient(?,?,?,?,?,?,?,?,?)");
            call.setString(1, patient.getNom());
            call.setString(2, patient.getTelephone());
            call.setString(3, patient.getAdresse());
            call.setString(4, patient.getNumero_assurance());
            call.setString(5, patient.getGroupeSanguin());
            call.setString(6, patient.getGroupeRhesus());
            call.setDouble(7, patient.getTaille());
            call.setDouble(8, patient.getPoids());
            call.setString(9, patient.getAntecedent());
            call.execute();
            serviceResponse.setResponse(200, "Enregistrement reussi du patient.");
        } catch (SQLException e) {
            serviceResponse.setResponse(510, e.getMessage());
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse modifyPatient(Patient patient){
        try {
            call=Connexion.getConncetion().prepareCall("CALL modifyPatient(?,?,?,?,?,?,?,?,?,?)");
            call.setString(1, patient.getNom());
            call.setString(2, patient.getAdresse());
            call.setString(3, patient.getTelephone());
            call.setString(4, patient.getNumero_assurance());
            call.setString(5, patient.getGroupeSanguin());
            call.setString(6, patient.getGroupeRhesus());
            call.setString(7, patient.getAntecedent());
            call.setInt(8, patient.getId_patient());
            call.setDouble(9, patient.getTaille());
            call.setDouble(10, patient.getPoids());
            call.execute();
            serviceResponse.setResponse(200, "Modification du patient reussi");
        } catch (SQLException e) {
            serviceResponse.setResponse(510, e.getMessage());
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse deletePatient(int id_patient) {
        try {
            call= Connexion.getConncetion().prepareCall("CALL deletePatient(?)");
            call.setInt(1, id_patient);
            call.execute();
            serviceResponse.setResponse(200, "Suppression reussi du patient.");
        } catch (SQLException e) {
            serviceResponse.setResponse(510, e.getMessage());
        }
        return serviceResponse;
    }

    @Override
    public List<Patient> getAllPatient() throws SQLException {
        List<Patient> patients= new ArrayList<>();
        query= Connexion.getConncetion().prepareStatement("SELECT * FROM patientInfo");
        resultSet= query.executeQuery();
        while (resultSet.next()) {
            patients.add(Patient.serializeResultToPatient(resultSet));
        }
        return patients;
    }

    @Override
    public Patient getPatient(int id_patient) throws SQLException {
        query= Connexion.getConncetion().prepareStatement("SELECT * FROM patientInfo WHERE id_patient=?");
        query.setInt(1, id_patient);
        resultSet= query.executeQuery();
        return Patient.serializeResultToPatient(resultSet);
    }
}
