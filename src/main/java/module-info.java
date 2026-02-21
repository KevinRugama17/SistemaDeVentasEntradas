module com.mycompany.sistemadeventasentradas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.sistemadeventasentradas to javafx.fxml;
    opens com.mycompany.sistemadeventasentradas.Controller to javafx.fxml;
    opens com.mycompany.sistemadeventasentradas.Model to javafx.fxml;
    exports com.mycompany.sistemadeventasentradas;
}
