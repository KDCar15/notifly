package ni.edu.uam.notifly.controller;

import java.io.IOException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.model.PlantillaCorreo;

public class TemplateListController {
    @FXML private TextField tf_busqueda;
    @FXML private TableView<PlantillaCorreo> tv_plantillas;
    @FXML private TableColumn<PlantillaCorreo, String> tc_plantilla;
    private final Dao dao = new Dao();

    @FXML public void initialize() {
        tc_plantilla.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombre()));
        tv_plantillas.setPlaceholder(new javafx.scene.control.Label("No hay plantillas guardadas."));
        cargar();
        tv_plantillas.setOnMouseClicked(e -> { if (e.getClickCount() == 2) editar(); });
    }

    private void cargar() {
        tv_plantillas.setItems(FXCollections.observableArrayList(dao.buscarPlantillas(tf_busqueda.getText())));
    }
    @FXML public void buscar() { cargar(); }
    @FXML public void borrar_busqueda() { tf_busqueda.clear(); cargar(); }
    @FXML public void crear() { abrirDetalle(null); }
    @FXML public void editar() {
        PlantillaCorreo seleccion = tv_plantillas.getSelectionModel().getSelectedItem();
        if (seleccion == null) { aviso("Selecciona una plantilla para editar."); return; }
        abrirDetalle(seleccion);
    }
    @FXML public void borrar() {
        PlantillaCorreo seleccion = tv_plantillas.getSelectionModel().getSelectedItem();
        if (seleccion == null) { aviso("Selecciona una plantilla para eliminar."); return; }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar la plantilla «" + seleccion.getNombre() + "»?", ButtonType.CANCEL, ButtonType.OK);
        confirmacion.setHeaderText("Confirmar eliminación");
        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (dao.eliminarPlantilla(seleccion.getId())) cargar();
            else error("No se pudo eliminar la plantilla.");
        }
    }
    private void abrirDetalle(PlantillaCorreo plantilla) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/notifly/templatedetails.fxml"));
            Parent root = loader.load();
            TemplateDetailsController controller = loader.getController();
            controller.setPlantilla(plantilla);
            Stage stage = new Stage();
            stage.setTitle(plantilla == null ? "Nueva plantilla" : "Editar plantilla");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tv_plantillas.getScene().getWindow());
            stage.setScene(new Scene(root));
            ni.edu.uam.notifly.App.ajustarVentanaSecundaria(stage, 760, 680);
            stage.setOnHidden(e -> cargar());
            stage.showAndWait();
        } catch (IOException e) { e.printStackTrace(); error("No se pudo abrir el editor de plantillas."); }
    }
    @FXML public void volver() {
        try { ni.edu.uam.notifly.App.setRoot("mainscreen"); }
        catch (IOException e) { error("No se pudo volver al menú principal."); }
    }
    private void aviso(String mensaje) { mostrar(Alert.AlertType.INFORMATION, "Plantillas", mensaje); }
    private void error(String mensaje) { mostrar(Alert.AlertType.ERROR, "Error", mensaje); }
    private void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo); a.setTitle(titulo); a.setHeaderText(null); a.setContentText(mensaje); a.showAndWait();
    }
}
