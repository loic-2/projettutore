package com.example.mapping;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class MapButton {
    protected MFXButton supprimer;
    protected MFXButton view;
    protected MFXButton modifier;
    protected MFXButton download;
    protected HBox buttons;

    public MapButton(){
        supprimer= new MFXButton("", 20, 20);
        buttons= new HBox();
        buttons.setStyle("-fx-spacing:15px;-fx-alignment:center;");
        view= new MFXButton("", 20, 20);
        modifier= new MFXButton("", 20, 20);
        download= new MFXButton("", 20, 20);

        supprimer.setStyle("-fx-background-color: red;");
        modifier.setStyle("-fx-background-color:  #007bff;");
        view.setStyle("-fx-background-color: rgb(0,0,200);");
        download.setStyle("-fx-background-color:  rgb(0,200,0);");

        MaterialIconView delete=new MaterialIconView(MaterialIcon.DELETE, "20px");
        delete.setFill(Color.WHITE);
        MaterialIconView edit=new MaterialIconView(MaterialIcon.EDIT, "20px");
        edit.setFill(Color.WHITE);
        MaterialIconView loupe=new MaterialIconView(MaterialIcon.LOUPE, "20px");
        loupe.setFill(Color.WHITE);
        MaterialIconView downloadIcon=new MaterialIconView(MaterialIcon.FILE_DOWNLOAD, "20px");
        downloadIcon.setFill(Color.WHITE);

        supprimer.setGraphic(delete);
        modifier.setGraphic(edit);
        view.setGraphic(loupe);
        download.setGraphic(downloadIcon);


        buttons.getChildren().add(modifier);
        buttons.getChildren().add(supprimer);
        buttons.getChildren().add(view);
        buttons.getChildren().add(download);
        buttons.setCenterShape(true);
    }
}
