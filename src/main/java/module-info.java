module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires MaterialFX;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialicons;
    requires com.jfoenix;
    requires lombok;
    


    opens com.example to javafx.fxml;
    opens com.example.controllers to  javafx.fxml;
    opens com.example.mapping to javafx.base;
    exports com.example;
    exports com.example.controllers;
}
