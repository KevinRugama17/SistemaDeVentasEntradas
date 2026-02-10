module com.mycompany.sistemadeventasentradas {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.sistemadeventasentradas to javafx.fxml;
    exports com.mycompany.sistemadeventasentradas;
}
