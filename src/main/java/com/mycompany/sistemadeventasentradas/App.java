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
//      Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/sistemadeventasentradas/FXML/Login.fxml"));
//       stage.setTitle("Sistema de Venta de Entradas — Auditorio");
//        stage.setScene(new Scene(root, 600, 400));
//        stage.show();
        scene = new Scene(loadFXML("Login"));
//        scene.getStylesheets().add(App.class.getResource("/gr/proyprogramadoiii/Vista/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema de Ventas de Entradas - Auditorio");
        Image icon = new Image("/com/mycompany/sistemadeventasentradas/FXML/LogoUCR.jpg");
        stage.getIcons().add(icon);
        stage.show();
    }
    

    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        return fxmlLoader.load();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/sistemadeventasentradas/FXML/Login.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}