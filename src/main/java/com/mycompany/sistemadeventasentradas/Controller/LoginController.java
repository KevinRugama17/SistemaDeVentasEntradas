package com.mycompany.sistemadeventasentradas.Controller;

import com.mycompany.sistemadeventasentradas.Model.GestorCliente;
import com.mycompany.sistemadeventasentradas.Exception.CamposVaciosException;
import com.mycompany.sistemadeventasentradas.Exception.CredencialesInvalidasException;
import com.mycompany.sistemadeventasentradas.Exception.UsuarioNoExisteException;

import java.net.URL;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController implements Initializable{
    @FXML private TextField txtID; 
    @FXML private PasswordField txtPassword;
    @FXML private Button btnIngresar;
    @FXML private Button btnRegistrarse;
    @FXML private TextFlow txtFlow;
    
    private GestorCliente gestor = new GestorCliente();
    @FXML
    private void ingresarLogin(ActionEvent event) {
        btnIngresar.setDisable(true);
        String id = txtID.getText();
        String password = txtPassword.getText();
        boolean loginExitoso = false;
        
        try {
            validarCampos(id, password);

            if (!gestor.existeUsuario(id)) {
                throw new UsuarioNoExisteException("El usuario no existe, debe registrarse.");
            }
            if (gestor.validarLogin(id, password)) {
                boolean esAdmin = id.equals("admin");
                // Crear controlador de lógica
                ControladorPrincipal controladorPrincipal = new ControladorPrincipal();

                // Cargar MainView con su controlador
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/sistemadeventasentradas/FXML/MainView.fxml"));
                MainViewController mvc = new MainViewController(controladorPrincipal);
                loader.setController(mvc);

                Parent parent = loader.load();

                // Aplicar rol DESPUÉS de load() y ANTES de show()
                mvc.setEsAdmin(esAdmin);

                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.setTitle(esAdmin
                    ? "Administracion - Auditorio UCR"
                    : "Entradas - Auditorio UCR");
                stage.setMinWidth(900);
                stage.setMinHeight(620);

                // Guardar datos al cerrar
                stage.setOnCloseRequest(e -> controladorPrincipal.guardarTodo());
                stage.show();

                // Cerrar el login
                Stage stageActual = (Stage) btnIngresar.getScene().getWindow();
                stageActual.close();
                loginExitoso = true;
            } else {
                throw new CredencialesInvalidasException("ID o contraseña incorrecta.");
            }

        } catch (CamposVaciosException e) {
            mostrarError(e.getMessage());
        } catch (UsuarioNoExisteException e) {
            mostrarError(e.getMessage());
        } catch (CredencialesInvalidasException e) {
            mostrarError(e.getMessage());
        } catch (IOException e) {
            mostrarError("Error al cargar la ventana: ");
            e.printStackTrace(); 
        } finally {
            if (!loginExitoso) btnIngresar.setDisable(false);
        }
    }

    @FXML
    public void registrarUsuario(ActionEvent event) {
        String id       = txtID.getText();
        String password = txtPassword.getText();
        try {
            validarCampos(id, password);
            if (id.equals("admin")) {
                throw new CredencialesInvalidasException("No puedes registrarte con ese ID.");
            }
            boolean registrado = gestor.registrarCliente(id, password);
            if (registrado) {
                mostrarTexto("Usuario registrado correctamente.");
            } else {
                throw new CredencialesInvalidasException("El ID ya está registrado.");
            }
        } catch (CamposVaciosException e) {
            mostrarError(e.getMessage());
        } catch (CredencialesInvalidasException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        txtID.clear();
        txtPassword.clear();
        txtFlow.getChildren().clear();
        btnIngresar.setDisable(false);
        btnRegistrarse.setDisable(false);
    }

    private void validarCampos(String id, String password) throws CamposVaciosException {
        if (id.isEmpty() || password.isEmpty()) {
            throw new CamposVaciosException("El ID o la contraseña no pueden estar vacios.");
        }
    }

    private void mostrarTexto(String mensaje) {
        Text t = new Text(mensaje);
        t.setFill(Color.GREEN);
        txtFlow.getChildren().clear();
        txtFlow.getChildren().add(t);
        txtID.clear();
        txtPassword.clear();
    }

    private void mostrarError(String mensaje) {
        Text t = new Text(mensaje);
        t.setFill(Color.RED);
        txtFlow.getChildren().clear();
        txtFlow.getChildren().add(t);
        btnIngresar.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        //  TODO
    }
}
