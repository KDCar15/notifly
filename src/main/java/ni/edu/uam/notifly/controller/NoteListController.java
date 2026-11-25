/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author charly
 */
public class NoteListController {
    @FXML public void volver() { navegarInicio(); }
    private void navegarInicio() { try { ni.edu.uam.notifly.App.setRoot("mainscreen"); } catch (java.io.IOException e) { throw new IllegalStateException(e); } }
    // FXML
    @FXML
    private ComboBox cb_tipo;
    @FXML
    private ComboBox cb_categoria;
    
    // Tabla
    @FXML
    private TableView tv_resultados;
    @FXML
    private TableColumn tc_id;
    @FXML
    private TableColumn tc_tipo;
    @FXML
    private TableColumn tc_fecha;
    @FXML
    private TableColumn tc_categoria;
    @FXML
    private TableColumn tc_resumen;
    
    // Funciones
    @FXML
    public void seleccionar_fecha_inicio(){
    
    }
    @FXML
    public void seleccionar_fecha_fin(){
    
    }
    @FXML
    public void buscar(){
    
    }
    @FXML
    public void limpiar(){
    
    }
}
