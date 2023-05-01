package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.example.App;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

public class MainController {

    @FXML
    private Button ajouter;

    /*@FXML
    private VBox box;
    @FXML
    private VBox sousbox;*/

    @FXML
    private Label date;

    @FXML
    private GridPane mainContainer;

    @FXML
    private VBox verticalMenu;

    @FXML
    private FontAwesomeIconView subIcon;

    @FXML
    private Label pageTitle;

    @FXML
    private Label medecins;

    @FXML
    private Label generaliste;

    @FXML
    private Label specialiste;

    @FXML
    private Label patients;

    @FXML
    private Label home;

    @FXML
    private Label historique;

    @FXML
    private Label utilisateur;

    @FXML
    private Label consultation;

    @FXML
    private Label fiche;

    private Node sousMenu;
    
    private Node mainMenu;

    @FXML
    private VBox rigntContainer;

    private Node node;

    @FXML
    private VBox pageMedecin;

    //ajouter un menu
    @FXML
    private void initialize(){
        setPageName("Dashboard",home);
        try {
            changeContent("pageDashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        mainMenu=mainContainer.getChildren().get(0);
        sousMenu=verticalMenu.getChildren().get(3);
        sousMenu.setVisible(false);
        verticalMenu.getChildren().remove(3);
    }

    @FXML
    private void openPopUp() throws IOException{
        App.popUpLaunch("addMedecin");
    }


    @FXML
    private void setPageName(String name,Label label){
        pageTitle.setText(name);
        home.setStyle("-fx-label-padding: 10px");medecins.setStyle("-fx-label-padding: 10px");
        patients.setStyle("-fx-label-padding: 10px");historique.setStyle("-fx-label-padding: 10px");
        fiche.setStyle("-fx-label-padding: 10px");specialiste.setStyle("-fx-label-padding: 10px;");
        generaliste.setStyle("-fx-label-padding: 10px;");utilisateur.setStyle("-fx-label-padding: 10px;");
        consultation.setStyle("-fx-label-padding: 10px;");
        label.setStyle("-fx-background-color:  rgba(255, 255, 255, 0.16); -fx-text-fill: white;"+
        "-fx-border-width:  0 0 0 5px; -fx-border-color:white");
    }

    @FXML
    private void gotoPagePatient() throws IOException{
        setPageName("Patient",patients);
        node= (Node) App.loadFXML("pagePatient");
        rigntContainer.getChildren().remove(0);
        rigntContainer.getChildren().add(0, node);
    }

    @FXML
    private void gotoPageMedecin(){
        if(!sousMenu.isVisible()){
            sousMenu.setVisible(true);
            verticalMenu.getChildren().add(3, sousMenu);
            subIcon.setIcon(FontAwesomeIcon.ANGLE_UP);
        }else{
            sousMenu.setVisible(false);
            verticalMenu.getChildren().remove(sousMenu);
            subIcon.setIcon(FontAwesomeIcon.ANGLE_DOWN);
        }
    }

    @FXML
    private void gotoPageGeneraliste() throws IOException{
        setPageName("Generaliste",generaliste);
        MedecinController.isSpecialiste=false;
        node= (Node) App.loadFXML("pageMedecin");
        rigntContainer.getChildren().remove(0);
        rigntContainer.getChildren().add(0, node);
        AddMedecinController.setspecialiste=false;
    }

    @FXML
    private void gotoPageSpecialiste() throws IOException{
        setPageName("Specialiste",specialiste);
        MedecinController.isSpecialiste=true;
        node= (Node) App.loadFXML("pageMedecin");
        rigntContainer.getChildren().remove(0);
        rigntContainer.getChildren().add(0, node);
        AddMedecinController.setspecialiste=true;
    }

    /* 
    @FXML
    private void toggle(){
        box.setVisible(!box.isVisible());
    }

    //modification d'un sous menu
    @FXML
    private void toggleSous(){
        if(sousbox.isVisible()){
            box.getChildren().remove(2);
            sousbox.setVisible(!sousbox.isVisible());
        }else{
            sousbox.setVisible(!sousbox.isVisible());
            box.getChildren().add(2, sous);
        }
    }
    @FXML
    private void change(){

    }*/
    @FXML
    private void toggleMenu(){
        if (mainContainer.getChildren().size()==2) {
            mainContainer.getChildren().remove(0); // supprime la première contrainte de colonne
        } else {
            if (mainContainer.getChildren().size()==1) {
                mainContainer.getChildren().add(0, mainMenu);
            }
        }
    }

    @FXML
    private void gotoPageFiche() throws IOException{
        setPageName("Fiche de Maladie",fiche);
        changeContent("pageFiche");
    }

    @FXML
    private void gotoDashBoard() throws IOException{
        setPageName("Dashboard",home);
        changeContent("pageDashboard");
    }

    @FXML
    private void gotoPageHistorique() throws IOException{
        setPageName("Historique",historique);
        changeContent("pageHistorique");
    }

    @FXML
    private void gotoPageConsultation() throws IOException{
        setPageName("Consultation",consultation);
        //changeContent("pageConsultation");
    }

    @FXML
    private void gotoPageUtilisateur() throws IOException{
        setPageName("Utilisateur",utilisateur);
        changeContent("pageUtilisateur");
    }

    private void changeContent(String fileName) throws IOException{
        node= (Node) App.loadFXML(fileName);
        rigntContainer.getChildren().remove(0);
        rigntContainer.getChildren().add(0, node);
    }

    @FXML
    private void logout(){

    }

    private void updateTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        //
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        String formattedDay = currentDate.format(dayFormatter);
        date.setText(formattedDay.subSequence(0, 3)+" "+formattedTime);
    }

    @FXML
    protected void exporter(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
                if(selectedDirectory == null){
                    
                }else{
                    System.out.println(selectedDirectory.getAbsolutePath());
                }
    }
}
