package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.error.ServiceResponse;
import com.example.models.Patient;

public interface PatientService {
    ServiceResponse addPatient(Patient patient);
    ServiceResponse modifyPatient(Patient patient);
    ServiceResponse deletePatient(int id_patient);
    List<Patient> getAllPatient() throws SQLException;
    Patient getPatient(int id_patient) throws SQLException;
}
