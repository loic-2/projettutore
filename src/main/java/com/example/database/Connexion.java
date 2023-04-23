package com.example.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.error.DatabaseResponse;
import com.example.models.Medecin;
import com.example.models.Patient;

public final class Connexion {

    private String user="loic";
    private String database="projetTutore";
    private String host="localhost";
    private String password="1234";
    private String url="jdbc:mysql://"+host+"/"+database;
    private static Connection instance;
    private static CallableStatement call;
    
    private Connexion(){

        try {
            instance= DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return
     */
    public synchronized static Connection getConncetion(){
        if (instance==null) {
            new Connexion();
        }
        return instance;
    }

    /**
     * @param id
     * @return
     */
    public static DatabaseResponse executeProcessDeleteMedecin(int id){
        DatabaseResponse databaseResponse= new DatabaseResponse();
        try {
            call= Connexion.getConncetion().prepareCall("CALL deleteMedecin(?)");
            call.setInt(1, id);
            call.execute();
            call.close();
            databaseResponse.setResponse(200, "Suppression reussi du medecin.");
        } catch (SQLException e1) {
            databaseResponse.setResponse(510, e1.getMessage());
        }
        return databaseResponse;
    }

    /**
     * @param medecin
     * @return
     */
    public static DatabaseResponse executeProcessAddMedecin(Medecin medecin){
        DatabaseResponse databaseResponse= new DatabaseResponse();
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
                databaseResponse.setResponse(200, "Insertion du medecin reussi");
            } catch (SQLException e) {
                databaseResponse.setResponse(510, e.getMessage());
            }
        return databaseResponse;
    }

    /**
     * @param patient
     * @return
     */
    public static DatabaseResponse executeProcessAddPatient(Patient patient){
        DatabaseResponse databaseResponse= new DatabaseResponse();
        try {
            call= getConncetion().prepareCall("CALL addPatient(?,?,?,?,?,?,?,?,?)");
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
            databaseResponse.setResponse(200, "Enregistrement reussi du patient.");
        } catch (SQLException e) {
            databaseResponse.setResponse(510, e.getMessage());
        }
        return databaseResponse;
    }

    public static DatabaseResponse executeProcessDeletePatient(int id_patient){
        DatabaseResponse databaseResponse= new DatabaseResponse();
        try {
            call= getConncetion().prepareCall("CALL deletePatient(?)");
            call.setInt(1, id_patient);
            call.execute();
            databaseResponse.setResponse(200, "Suppression reussi du patient.");
        } catch (SQLException e) {
            databaseResponse.setResponse(510, e.getMessage());
        }
        return databaseResponse;
    }

    public static DatabaseResponse executeProcessModifyPatient(Patient patient){
        DatabaseResponse databaseResponse= new DatabaseResponse();
        try {
            call=getConncetion().prepareCall("CALL modifyPatient(?,?,?,?,?,?,?,?,?,?)");
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
            databaseResponse.setResponse(200, "Modification du patient reussi");
        } catch (SQLException e) {
            databaseResponse.setResponse(510, e.getMessage());
        }
        return databaseResponse;
    }

    public DatabaseResponse executeProcessModifyMedecin(Medecin medecin){
        DatabaseResponse databaseResponse= new DatabaseResponse();
        try {
            call= getConncetion().prepareCall("CALL modifyMedecin(?,?,?,?,?,?,?,?)");
            call.setString(1, medecin.getNom());
            call.setString(2, medecin.getAdresse());
            call.setString(3, medecin.getTelephone());
            call.setString(4, medecin.getNumero_assurance());
            call.setString(5, medecin.getMatricule());
            call.setString(6, medecin.getSpecialite());
            call.setInt(7, medecin.getId_medecin());
            call.setBoolean(8, medecin.getSpecialite()==null? false: true);
            call.execute();
            databaseResponse.setResponse(200, "Modification du medecin reussi.");
        } catch (SQLException e) {
            databaseResponse.setResponse(510, e.getMessage());
        }
        return databaseResponse;
    }
}
