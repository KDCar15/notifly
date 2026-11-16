module ni.edu.uam.notifly {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.persistence;
    requires java.instrument;

    requires org.eclipse.persistence.core;
    requires org.eclipse.persistence.jpa;

    opens ni.edu.uam.notifly.entity to
            org.eclipse.persistence.core,
            org.eclipse.persistence.jpa;

    opens ni.edu.uam.notifly to javafx.fxml;
    opens ni.edu.uam.notifly.controller to javafx.fxml;

    exports ni.edu.uam.notifly;
}