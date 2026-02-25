package com.mycompany.sistemadeventasentradas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

public class App extends Application {

    private static Scene scene;
    
@Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema de Ventas de Entradas - Auditorio");
        Image icon = new Image("/com/mycompany/sistemadeventasentradas/FXML/LogoUCR.jpg");
        stage.getIcons().add(icon);
        stage.show();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/sistemadeventasentradas/FXML/Login.fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch(args);
    }
}