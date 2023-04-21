package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Connexion {

    private String user="loic";
    private String database="upharm";
    private String host="localhost";
    private String password="1234";
    private String url="jdbc:mysql://"+host+"/"+database;
    private static Connection instance;
    
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public synchronized static Connection getConncetion(){
        if (instance==null) {
            new Connexion();
        }
        return instance;
    }
}
