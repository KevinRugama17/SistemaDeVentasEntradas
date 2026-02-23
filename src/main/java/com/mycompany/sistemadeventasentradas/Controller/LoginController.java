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
    private void ingresarLogin(ActionEvent event)throws IOException{
        btnIngresar.setDisable(true);
        String id = txtID.getText();
        String password = txtPassword.getText();
        boolean loginExitoso = false;
        
        try{
            validarCampos(id, password); // Se valida si lo ingresado no esta en blanco
            
            if(!gestor.existeUsuario(id)){
                throw new UsuarioNoExisteException("El usuario no existe, debe registrarse. ");
            }
        if(gestor.validarLogin(id, password)){
            if(id.equals("admin") && password.equals("2468")){
                Parent parent = FXMLLoader.load(getClass().getResource("/com/proyecto/pruebaproyecto2/Vista/Admin.fxml"));
//                La linea anterior sin comentar es donde debe ir la ventana del admin
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Administracion.");
                stage.show();
//                Lo siguiente cierra el login despues de abrir la ventana admin
                Stage stageActual = (Stage) btnIngresar.getScene().getWindow();
                stageActual.close();
                loginExitoso = true;
            }else{
                Parent parent = FXMLLoader.load(getClass().getResource("/com/proyecto/pruebaproyecto2/Vista/VentasUsuarios.fxml"));
//            La linea anterior es donde va la ventana para los usuarios
            Stage stage =  new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Entradas - Auditorio");
            stage.show();
//             Lo siguiente cierra el login despues de abrir la ventana para los usuarios
            Stage stageActual = (Stage) btnIngresar.getScene().getWindow();
            stageActual.close();
            loginExitoso = true;
            }
        }else{
            throw new CredencialesInvalidasException("ID o contrasena incorrecta. ");
        }
        }catch(CamposVaciosException e){
            mostrarError(e.getMessage());
        }catch(UsuarioNoExisteException e){
            mostrarError(e.getMessage());
        }catch(CredencialesInvalidasException e){
            mostrarError(e.getMessage());
        }catch(IOException e){
            mostrarError("Error al cargar la ventana.");
        }finally{
            if(!loginExitoso)
                btnIngresar.setDisable(false);
        }
    }
    @FXML
    public void registrarUsuario(ActionEvent event){
        String id = txtID.getText();
        String password = txtPassword.getText();
        try{
            validarCampos(id,password);
            if(id.equals("admin")){
                throw new CredencialesInvalidasException("No puedes registrarte con ese ID.");
            }
            
            boolean registrado = gestor.registrarCliente(id, password);
            
            if(registrado){
                mostrarTexto("Usuario registrado correctamente. ");
            }else{
                throw new CredencialesInvalidasException("El ID esta registrado");
            }
        }catch(CamposVaciosException e){
            mostrarError(e.getMessage());
        }catch(CredencialesInvalidasException e){
            mostrarError(e.getMessage());
        }
    }
    @FXML
    public void cancelar(ActionEvent event){
        txtID.clear();
        txtPassword.clear();
        txtFlow.getChildren().clear();
        btnIngresar.setDisable(false);
        btnRegistrarse.setDisable(false); 
    }
    private void validarCampos(String id, String password)throws CamposVaciosException{
        if (id.isEmpty() || password.isEmpty()){
            throw new CamposVaciosException("El ID o la contrasena no pueden estar vacios. ");
        }
    }
    private void mostrarTexto(String mensaje){
        Text mensajeTexto = new Text(mensaje);
        mensajeTexto.setFill(Color.GREEN);
        txtFlow.getChildren().clear();
        txtFlow.getChildren().add(mensajeTexto);
        txtID.clear();
        txtPassword.clear();
        btnRegistrarse.setDisable(false);
    }
    private void mostrarError(String mensaje){
        Text mensajeTexto = new Text(mensaje);
        mensajeTexto.setFill(Color.RED);
        txtFlow.getChildren().clear();
        txtFlow.getChildren().add(mensajeTexto);
        btnIngresar.setDisable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
