package ni.edu.uam.notifly;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {
    @FXML
    public void mostrar_sugerencias(){
        System.out.println("Cambiando a pantalla de sugerencias...");
    }
    @FXML
    public void mostrar_quejas(){
        System.out.println("Cambiando a pantalla de quejas...");
    }
    @FXML
    public void mostrar_historial(){
        System.out.println("Cambiando a pantalla de historial...");
    }
    @FXML
    public void salir(){
        System.out.println("Saliendo del programa...");
    }
    @FXML
    public void acercade(){
        System.out.println("Mostrando informacion del programa...");
    }
}
