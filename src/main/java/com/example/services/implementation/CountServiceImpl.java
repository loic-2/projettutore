package com.example.services.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.database.Connexion;
import com.example.services.CountService;

public class CountServiceImpl implements CountService{

    @Override
    public int countRowDataFromDatabaseTable(String table) {
        try {
            Statement ps= Connexion.getConncetion().createStatement();
            ResultSet rs=ps.executeQuery("SELECT COUNT(*) FROM "+table);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}
