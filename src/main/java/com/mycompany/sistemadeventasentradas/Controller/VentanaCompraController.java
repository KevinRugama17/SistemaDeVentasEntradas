/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class VentanaCompraController implements Initializable {

    @FXML private Label     lblAsiento;
    @FXML private TextField txtNombre;
    @FXML private TextField txtIdCliente;
    @FXML private ComboBox<String> comboTipo;
    @FXML private Label     lblPrecioFinal;

    private final ControladorPrincipal controller;
    private final int fila;
    private final int col;
    private boolean compraRealizada = false;

    public VentanaCompraController(ControladorPrincipal controller, int fila, int col) {
        this.controller = controller;
        this.fila = fila;
        this.col  = col;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblAsiento.setText("Fila " + (char)('A' + fila) + " — Asiento " + (col + 1));

        comboTipo.getItems().addAll("General", "VIP", "Estudiante");
        comboTipo.getSelectionModel().selectFirst();

        
        actualizarPrecio();
    }

    @FXML
    private void onTipoChanged() {
        actualizarPrecio();
    }
    private void actualizarPrecio() {
        //supuestamente arreglado
    if (controller.getEventoActual() == null) return;
    String tipo = comboTipo.getValue();
    if (tipo == null) return; // ← protección necesaria
    
    double base = controller.getEventoActual().getPrecioBase();
    double precio;
    switch (tipo) {
        case "VIP": 
            precio = base * 1.50;
            break;
        case "Estudiante":
            precio = base * 0.70;
            break;
        default:
            precio = base;
            break;
    }
    lblPrecioFinal.setText("₡" + String.format("%.2f", precio));
   
       //antes 
        
   /* if (controller.getEventoActual() == null) return;
    double base = controller.getEventoActual().getPrecioBase();
    String tipo = comboTipo.getValue();
    
    double precio;
    switch (tipo) {
        case "VIP":
            precio = base * 1.50;
            break;
        case "Estudiante":
            precio = base * 0.70;
            break;
        default:
            precio = base;
            break;
    }
    lblPrecioFinal.setText("₡" + String.format("%.2f", precio));*/
}
   

    @FXML
    private void onConfirmar() {
        String nombre = txtNombre.getText().trim();
        String idCliente = txtIdCliente.getText().trim();
        String tipo = comboTipo.getValue();

       
        if (nombre.isEmpty() || idCliente.isEmpty()) {
            mostrarError("Campos incompletos", "Ingrese el nombre y el ID del cliente.");
            return;
        }

        
        String ticket = controller.comprarEntrada(nombre, idCliente, fila, col, tipo);

        if (ticket != null) {
            compraRealizada = true;
       
            TextArea area = new TextArea(ticket);
            area.setEditable(false);
            area.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13;");
            area.setPrefSize(340, 260);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Compra Exitosa!");
            alert.setHeaderText("Ticket generado y guardado en archivo.");
            alert.getDialogPane().setContent(area);
            alert.showAndWait();

            cerrarVentana();
        } else {
            mostrarError("Error", "El asiento ya fue ocupado por otro usuario.");
        }
    }

    @FXML
    private void onCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        ((Stage) txtNombre.getScene().getWindow()).close();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public boolean isCompraRealizada() { 
        return compraRealizada;
    }
}

