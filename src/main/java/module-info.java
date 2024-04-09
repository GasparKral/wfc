module gaspardev {
    requires javafx.controls;
    requires javafx.fxml;

    opens gaspardev to javafx.fxml;
    exports gaspardev;
}
