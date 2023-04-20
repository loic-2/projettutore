module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
   
    


    opens com.example to javafx.fxml;
    opens com.example.controllers to  javafx.fxml;
    exports com.example;
    exports com.example.controllers;
}
