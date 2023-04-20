package com.example.controllers;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class SplashScreenController {

    @FXML
    private ProgressBar progressBar;

    public void initialize() {
        // Ajoutez ici vos actions à effectuer pendant le chargement de l'application
        // Par exemple, initialiser des variables, charger des fichiers, etc.

        // Simulation de l'avancement du chargement de l'application
        simulateLoading();
    }

    private void simulateLoading() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                double progress = i / 100.0;
                Platform.runLater(() -> progressBar.setProgress(progress));

                // Simulez le chargement de votre application ici
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Fermez la fenêtre de splashscreen et ouvrez votre fenêtre principale
            Platform.runLater(() -> {
                Stage stage = (Stage) progressBar.getScene().getWindow();
                stage.close();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }
}
