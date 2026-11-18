/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author charly
 */
public class NoteHistoryController implements Initializable{
    @FXML
    private ComboBox cb_tipo;
    @FXML
    private ComboBox cb_estado;
    @FXML
    private ComboBox cb_area;
    @FXML
    private ComboBox cb_categoria;
    @FXML
    private ComboBox cb_facultad;
    
    // Tabla
    @FXML
    private TableView tv_resultados;
    @FXML
    private TableColumn tc_acceso;
    @FXML
    private TableColumn tc_id;
    @FXML
    private TableColumn tc_tipo;
    @FXML
    private TableColumn tc_facultad;
    @FXML
    private TableColumn tc_categoria;
    @FXML
    private TableColumn tc_area;
    @FXML
    private TableColumn tc_estado;
    
    // Metodos
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
    @FXML
    public void visualizar(){
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
