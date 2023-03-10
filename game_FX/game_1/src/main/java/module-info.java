module com.example.game_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.game_1 to javafx.fxml;
    exports com.example.game_1;
}