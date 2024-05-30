module org.lia.java_lab8_client_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.lia.java_lab8_client_v2 to javafx.fxml;
    exports org.lia.java_lab8_client_v2;

    opens org.lia.java_lab8_client_v2.controller to javafx.fxml;
    exports org.lia.java_lab8_client_v2.controller;
}