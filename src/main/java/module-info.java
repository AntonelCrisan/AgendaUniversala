module org.example.agendauniversala {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens org.example.agendauniversala to javafx.fxml;
    exports org.example.agendauniversala;
}