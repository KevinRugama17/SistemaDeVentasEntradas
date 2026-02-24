package com.mycompany.sistemadeventasentradas.Controller;
import com.mycompany.sistemadeventasentradas.Model.Entrada;
import com.mycompany.sistemadeventasentradas.Model.Evento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    @FXML private TabPane          tabPane;
    @FXML private Tab              tabMapa;
    @FXML private Tab              tabAdmin;

    @FXML private ComboBox<String> comboEventoGlobal;
    @FXML private Label            lblEventoNombre;
    @FXML private Label            lblEventoFecha;
    @FXML private Label            lblEventoPrecio;
    @FXML private Label            lblRecaudado;
    @FXML private Label            lblStatus;

    @FXML private GridPane         gridAsientos;
    @FXML private ListView<String> listEventos;
    @FXML private TextArea         areaReporte;
    
    private final ControladorPrincipal controller;
    private boolean esAdmin = false;
    private Evento evento; // Esta variable es una prueba.
    private Button[][] botonesAsientos;
    
    public MainViewController(ControladorPrincipal controller) {
        this.controller = controller;
    }
//     * Debe llamarse justo después de loader.load() y antes de stage.show().
    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
        aplicarRol();
    }
    // Nota: aplicarRol() se llama desde setEsAdmin() después de initialize()
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonesAsientos = new Button[Evento.FILAS][Evento.COLUMNAS]; // Matriz de los asientos
        construirMapaAsientos();
        actualizarCombosYLista();
        refrescarInfoEvento();
    }
    //  El siguiente metodo remueve la ventana del admin en el caso de no ser el quien ingrese.
    private void aplicarRol() {
        if (!esAdmin) {
            tabPane.getTabs().remove(tabAdmin);
        }
    }

//    private void construirMapaAsientos() {
//        for (int col = 0; col < Evento.COLUMNAS; col++) {
//            Label lbl = new Label(String.valueOf(col + 1));
//            lbl.setAlignment(Pos.CENTER);
//            lbl.setPrefSize(54, 24);
//            lbl.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 11px;");
//            gridAsientos.add(lbl, col + 1, 0);
//        }
//        
//        for (int fila = 0; fila < Evento.FILAS; fila++) {
//            Label lblFila = new Label(String.valueOf((char) ('A' + fila)));
//            lblFila.setAlignment(Pos.CENTER);
//            lblFila.setPrefSize(28, 48);
//            lblFila.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 11px;");
//            gridAsientos.add(lblFila, 0, fila + 1);
//
//            for (int col = 0; col < Evento.COLUMNAS; col++) {
//                Button btn = crearBotonAsiento(fila, col);
//                botonesAsientos[fila][col] = btn;
//                gridAsientos.add(btn, col + 1, fila + 1);
//            }
//        }
//    }
    private void construirMapaAsientos(){
        for (int i = 0; i < Evento.COLUMNAS; i++) {
            Label lbl = new Label(String.valueOf(i + 1));
            lbl.getStyleClass().add("label-columna");
            gridAsientos.add(lbl, i +1, 0);
        }
        for (int fila = 0; fila < Evento.FILAS; fila++) {
            Label lblFila = new Label(String.valueOf((char)('A' + fila)));
            lblFila.getStyleClass().add("label-fila");
            gridAsientos.add(lblFila, 0, fila + 1);
            for (int col = 0; col < Evento.COLUMNAS; col++) {
                Button btn = crearBotonAsiento(fila, col);
                btn.getStyleClass().add("asiento-btn");
                botonesAsientos[fila][col] = btn;
                gridAsientos.add(btn, col + 1, fila + 1);
            }
        }
    }
    private Button crearBotonAsiento(int fila, int col){
        Button btn = new Button();
        btn.setPrefSize(54,48);
        aplicarEstiloAsiento(btn, "libre"); // Antes era "libre", pero lo cambie
        btn.setTooltip(new Tooltip("Fila" + (char) ('A' + fila) + "- Asiento "+(col + 1)));
        btn.setOnAction(e -> onAsientoClick(fila, col));
        return btn;
    }
    private void aplicarEstiloAsiento(Button btn, String estado) {
        switch (estado) {
            case "libre":
            case "ocupado":
            case "disabled":
            default:
                btn.setStyle(
                    "" +
                    "-fx-background-radius: 6;" +
                    "" +
                    ""
                );
                btn.setText("");
                break;
        }
    }
//    public enum EstadoAsiento{
//        LIBRE, OCUPADO, DISABLED
//    }
//    private void aplicarEstiloAsiento(Button btn, EstadoAsiento estado){
//        btn.getStyleClass().removeAll("asiento-libre","asiento-ocupado","asiento-disabled");
//         switch(estado){
//             case LIBRE:
//                 btn.getStyleClass().add("asiento-libre");
//                 btn.setText("");
//                 btn.setDisable(false);
//                 break;
//             case OCUPADO:
//                 btn.getStyleClass().add("asiento-ocupado");
//                 btn.setText(" X ");
//                 btn.setDisable(false);
//                 break;
//             case DISABLED:
//                 btn.getStyleClass().add("asiento-disabled");
//                 btn.setText("");
//                 btn.setDisable(false);
//                 break;
//         }
//    }

    private void onAsientoClick(int fila, int col) {
        Evento evento = controller.getEventoActual();

        if (evento == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin evento", "Seleccione un evento y haga clic en 'Cargar'.");
            return;
        }
        if (!evento.isAsientoLibre(fila, col)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Asiento ocupado", "Este asiento ya fue reservado.");
            return;
        }
        abrirDialogoCompra(fila, col);
    }
    private void abrirDialogoCompra(int fila, int col) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/sistemadeventasentradas/FXML/VentanaCompra.fxml"));

            VentanaCompraController dialogCtrl = new VentanaCompraController(controller, fila, col);
            loader.setController(dialogCtrl);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Comprar Entrada — " + (char) ('A' + fila) + (col + 1));
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.showAndWait();

            if (dialogCtrl.isCompraRealizada()) {
                refrescarMapa();
                refrescarInfoEvento();
                setStatus("Compra registrada. Ticket guardado en archivo.", true);
            }
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de compra.");
            e.printStackTrace();
        }
    }
    @FXML
    private void onCerrarSesion() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cerrar Sesion");
        confirm.setHeaderText("Desea cerrar secion?");
        confirm.setContentText("Se guardaran los datos antes de salir");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.guardarTodo();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/sistemadeventasentradas/FXML/Login.fxml"));
                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(loader.load()));
                loginStage.setTitle("Sistema de Ventas de Entradas - Auditorio");
                loginStage.setResizable(false);
                loginStage.show();
                Stage actual = (Stage) tabPane.getScene().getWindow();
                actual.close();
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo volver al login.");
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void onCargarEvento() {
        int idx = comboEventoGlobal.getSelectionModel().getSelectedIndex();
        if (idx < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin seleccion","Seleccione un evento del listado.");
            return;
        }
        controller.seleccionarEvento(idx);
        listEventos.getSelectionModel().select(idx);
        refrescarMapa();
        refrescarInfoEvento();
        setStatus("Evento cargado: " + controller.getEventoActual().getNombre(), false);
    }

//    Acciones de administracion
    @FXML
    private void onCrearEvento() {
        abrirDialogoEvento(null, -1);
    }
    @FXML
    private void onEditarEvento() {
        int idx = listEventos.getSelectionModel().getSelectedIndex();
        if (idx < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin seleccion","Seleccione un evento de la lista para editar.");
            return;
        }
        abrirDialogoEvento(controller.getEventos().get(idx), idx);
    }
    @FXML
    private void onEliminarEvento() {
        int idx = listEventos.getSelectionModel().getSelectedIndex();
        if (idx < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin seleccion","Seleccione un vento de la lista. ");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Eliminacion");
        confirm.setHeaderText("Eliminar el evento seleccionado?");
        confirm.setContentText("Esta accion no se puede deshacer");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.eliminarEvento(idx);
            actualizarCombosYLista();
            refrescarMapa();
            refrescarInfoEvento();
            setStatus("Evento eliminado.", false);
        }
    }
    @FXML
    private void onReiniciarSala() {
        if (controller.getEventoActual() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin evento activo", "Cargue un evento primero.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar reinicio");
        confirm.setHeaderText("Reiniciar todos los asientos?");
        confirm.setContentText("Se eliminarán todas las ventas del evento actual.");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.reiniciarSala();
            refrescarMapa();
            refrescarInfoEvento();
            areaReporte.clear();
            setStatus("Sala reiniciada", false);
        }
    }
    @FXML
    private void onBuscarReserva() {
        ChoiceDialog<String> choice = new ChoiceDialog<>("Por ID de Cliente", "Por ID de Cliente", "Por ID de Reserva");
        choice.setTitle("Buscar Reserva");
        choice.setHeaderText("¿Cómo desea buscar?");
        Optional<String> criterio = choice.showAndWait();
        if (criterio.isEmpty()) return;

        TextInputDialog input = new TextInputDialog();
        input.setTitle("Buscar Reserva");
        input.setHeaderText(criterio.get().equals("Por ID de Cliente")
            ? "Ingrese el ID del cliente:"
            : "Ingrese el ID de la reserva:");
        input.setContentText("ID:");
        Optional<String> valor = input.showAndWait();

        if (valor.isEmpty() || valor.get().isBlank()) return;

        Entrada resultado = criterio.get().equals("Por ID de Cliente")
            ? controller.buscarPorIdCliente(valor.get())
            : controller.buscarPorIdReserva(valor.get());

        if (resultado == null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sin resultados","No se encontró ninguna reserva con ese ID.");
        } else {
            mostrarTicket("Reserva encontrada", resultado.generarTicket());
        }
    }

    @FXML
    private void onGenerarReporte() {
        areaReporte.setText(controller.getReporteEvento());
    }

    private void abrirDialogoEvento(Evento eventoEditar, int indice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/sistemadeventasentradas/FXML/VentanaEvento.fxml"));
            VentanaEventoController dialogCtrl = new VentanaEventoController(controller, eventoEditar, indice);
            loader.setController(dialogCtrl);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(eventoEditar == null ? "Crear Evento" : "Editar Evento");
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.showAndWait();

            if (dialogCtrl.isGuardado()) {
                actualizarCombosYLista();
                refrescarMapa();
                refrescarInfoEvento();
                setStatus("Evento guardado correctamente.", false);
            }

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error","No se pudo abrir el diálogo de evento.");
            e.printStackTrace();
        }
    }

    public void refrescarMapa() {
        Evento evento = controller.getEventoActual();
        for (int fila = 0; fila < Evento.FILAS; fila++) {
            for (int col = 0; col < Evento.COLUMNAS; col++) {
                Button btn = botonesAsientos[fila][col];
                if (evento == null) {
                    aplicarEstiloAsiento(btn, "disabled");
                } else if (evento.isAsientoLibre(fila, col)) {
                    aplicarEstiloAsiento(btn, "libre");
                } else {
                    aplicarEstiloAsiento(btn, "ocupado");
                }
            }
        }
    }
//    El metodo de abajo es una posible refactorizacion del de arriba
//    public void refrescarMapa(){
//        for (int fila = 0; fila < Evento.FILAS; fila++) {
//            for (int col = 0; col < Evento.COLUMNAS; col++) {
//                EstadoAsiento estado = evento.getEstadoAsiento(fila, col);
//                Button btn = botonesAsientos[fila][col];
//                aplicarEstiloAsiento(btn, estado);
//            }
//        }
//    }

    public void actualizarCombosYLista() {
        comboEventoGlobal.getItems().clear();
        listEventos.getItems().clear();

        for (Evento e : controller.getEventos()) {
            String texto = e.getNombre() + "  (" + e.getFecha() + ")";
            comboEventoGlobal.getItems().add(texto);
            listEventos.getItems().add(texto);
        }

        if (controller.getEventoActual() != null) {
            int idx = controller.getEventos().indexOf(controller.getEventoActual());
            comboEventoGlobal.getSelectionModel().select(idx);
            listEventos.getSelectionModel().select(idx);
        }
        refrescarMapa();
    }

    private void refrescarInfoEvento() {
        Evento e = controller.getEventoActual();
        if (e != null) {
            lblEventoNombre.setText(e.getNombre());
            lblEventoFecha.setText(e.getFecha());
            lblEventoPrecio.setText(String.format("%.2f", e.getPrecioBase()));
            lblRecaudado.setText("Recaudado: " + String.format("%.2f", controller.getTotalRecaudado()));
        } else {
            lblEventoNombre.setText("—");
            lblEventoFecha.setText("—");
            lblEventoPrecio.setText("—");
            lblRecaudado.setText("");
        }
    }

    private void setStatus(String mensaje, boolean exito) {
        lblStatus.setText(mensaje);
        lblStatus.setStyle(exito
            ? "-fx-text-fill: #34c759; -fx-font-size: 11px; -fx-padding: 4 10 4 10; -fx-background-color: rgba(0,0,0,0.2); -fx-background-radius: 4;"
            : "-fx-text-fill: rgba(255,255,255,0.6); -fx-font-size: 11px; -fx-padding: 4 10 4 10; -fx-background-color: rgba(0,0,0,0.2); -fx-background-radius: 4;"
        );
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarTicket(String titulo, String contenido) {
        TextArea area = new TextArea(contenido);
        area.setEditable(false);
        area.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13;");
        area.setPrefSize(340, 280);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }
}