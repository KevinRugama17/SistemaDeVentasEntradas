package com.mycompany.sistemadeventasentradas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

   /* @Override
    public void start(Stage primaryStage) throws IOException {
        Crear el controlador de lógica compartido entre todas las vistas
       AuditorioController controller = new AuditorioController();

        // Cargar el FXML principal
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("com/mycompany/FXML/Login.fxml")
        );

        // El controlador de vista necesita el controlador de lógica
       MainViewController viewController = new MainViewController(controller);
        loader.setController(viewController);

        Scene scene = new Scene(loader.load(), 1100, 720);
        scene.getStylesheets().add(
           getClass().getResource("/com/auditorio/css/styles.css").toExternalForm()
        );

        primaryStage.setTitle("🎭 Sistema de Venta de Entradas — Auditorio");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(620);

        // Guardar datos al cerrar
       primaryStage.setOnCloseRequest(e -> controller.guardarTodo());

      primaryStage.show();
       
    }*/

    
@Override
    public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/sistemadeventasentradas/FXML/Login.fxml"));
       stage.setTitle("Sistema de Venta de Entradas — Auditorio");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    
    }
    

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}