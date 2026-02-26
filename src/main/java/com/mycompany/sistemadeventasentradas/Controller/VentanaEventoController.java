/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Controller;

import com.mycompany.sistemadeventasentradas.Model.Evento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class VentanaEventoController implements Initializable {
  @FXML private Label     lblTituloDialog;
    @FXML private TextField txtNombre;
    @FXML private TextField txtFecha;
    @FXML private TextField txtPrecio;

    private final ControladorPrincipal controller;
    private final Evento eventoEditar;  // null = crear
    private final int indice;           // -1 = crear
    private boolean guardado = false;

    public VentanaEventoController(ControladorPrincipal controller, Evento eventoEditar, int indice) {
        this.controller   = controller;
        this.eventoEditar = eventoEditar;
        this.indice       = indice;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (eventoEditar != null) {
            // Pre-llena los campos
            lblTituloDialog.setText("Editar Evento");
            txtNombre.setText(eventoEditar.getNombre());
            txtFecha.setText(eventoEditar.getFecha());
            txtPrecio.setText(String.valueOf(eventoEditar.getPrecioBase()));
        } else {
            lblTituloDialog.setText("Crear Evento");
        }
    }

    @FXML
    private void onGuardar() {
        String nombre = txtNombre.getText().trim();
        String fecha  = txtFecha.getText().trim();
        String precioTexto = txtPrecio.getText().trim();

        // Validación: campos vacíos
        if (nombre.isEmpty() || fecha.isEmpty() || precioTexto.isEmpty()) {
            mostrarError("Campos requeridos", "Todos los campos son obligatorios.");
            return;
        }

        // Validación: precio numérico (manejo de excepción)
        double precio;
        try {
            precio = Double.parseDouble(precioTexto);
            if (precio <= 0) throw new NumberFormatException("Debe ser mayor a 0");
        } catch (NumberFormatException e) {
            mostrarError("Precio invalido",
                "El precio debe ser un numero valido mayor a 0.\n" +
                "Ejemplo: 15000 o 12500.50");
            return;
        }

        // Guardar
        if (eventoEditar == null) {
            controller.crearEvento(nombre, fecha, precio);
        } else {
            controller.editarEvento(indice, nombre, fecha, precio);
        }

        guardado = true;
        cerrarVentana();
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

    public boolean isGuardado() { return guardado; }
}

