module gaspardev {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens gaspardev to javafx.fxml;

    exports gaspardev;
}
