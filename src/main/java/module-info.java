module ni.edu.uam.notifly {
    requires javafx.controls;
    requires javafx.fxml;

    opens ni.edu.uam.notifly to javafx.fxml;
    exports ni.edu.uam.notifly;
}
