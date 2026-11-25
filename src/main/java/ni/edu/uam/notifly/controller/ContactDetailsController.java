/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.controller;

/**
 *
 * @author charly
 */
public class ContactDetailsController {
    @javafx.fxml.FXML public void volver() { try { ni.edu.uam.notifly.App.setRoot("mainscreen"); } catch (java.io.IOException e) { throw new IllegalStateException(e); } }
    @javafx.fxml.FXML public void buscar() { }
    @javafx.fxml.FXML public void borrar_busqueda() { }
    @javafx.fxml.FXML public void crear() { }
    @javafx.fxml.FXML public void editar() { }
    @javafx.fxml.FXML public void eliminar() { }
}
