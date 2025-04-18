module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires langchain4j.open.ai;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.app to javafx.fxml;
    exports com.example.app;
}