package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static Stage popUpStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage=stage;
        scene = new Scene(loadFXML("dashboard"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application de Gestion des Patients et Medecins");
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void popUpLaunch(String fxml) throws IOException{
        popUpStage = new Stage();
        Scene popUpScene= new Scene(loadFXML(fxml));
        popUpScene.setFill(Color.TRANSPARENT);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setScene(popUpScene);
        popUpStage.showAndWait();
    }

    public static void closePopUp(){
        popUpStage.close();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}