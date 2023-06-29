module org.kappa.client {
    requires slf4j.api;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires Java.WebSocket;

    opens org.kappa.client to javafx.fxml;
    opens org.kappa.client.ui to javafx.fxml;

    exports org.kappa.client;
    exports org.kappa.client.component;
    exports org.kappa.client.entity;
    exports org.kappa.client.event;
    exports org.kappa.client.game;
    exports org.kappa.client.ui;
    exports org.kappa.client.utils;
}