package ni.edu.uam.notifly.controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainScreenController {
    @FXML
    public void mostrar_notas(){
        System.out.println("Cambiando a pantalla de notas...");
    }
    @FXML
    public void mostrar_plantillas(){
        System.out.println("Cambiando a pantalla de plantillas de correo...");
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
