module org.kappa.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.kappa.client to javafx.fxml;
    opens org.kappa.client.ui to javafx.fxml;
    exports org.kappa.client;
}