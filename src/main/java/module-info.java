module ni.edu.uam.notifly {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;

    opens ni.edu.uam.notifly to javafx.fxml;
    exports ni.edu.uam.notifly;
}
