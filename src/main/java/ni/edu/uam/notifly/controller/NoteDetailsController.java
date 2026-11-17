/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author charly
 */
public class NoteDetailsController {
    // Campos
    @FXML
    private TextField tf_nombresapellidos;
    @FXML
    private ComboBox cb_facultad;
    @FXML
    private TextField tf_correo;
    @FXML
    private TextField tf_celular;
    @FXML
    private TextField tf_fechahora;
    @FXML
    private ComboBox cb_area;
    @FXML
    private TextArea ta_descripcion;
    @FXML
    private TextArea ta_observaciones;
    
    // Checkboxs
    @FXML
    private CheckBox checkb1;
    @FXML
    private CheckBox checkb2;
    @FXML
    private CheckBox checkb3;
    @FXML
    private CheckBox checkb4;
    
    // Botones
    @FXML
    public void marcar_completado(){
    
    }
    @FXML
    public void guardar(){
    
    }
    @FXML
    public void cancelar(){
    
    }
}
