package ni.edu.uam.notifly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.util.List;
import ni.edu.uam.notifly.dao.Dao;
import ni.edu.uam.notifly.entity.EstadosNota;
import ni.edu.uam.notifly.entity.Notas;

/**
 * JavaFX App
 */
public class  App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainscreen"), 720, 760);
        stage.setScene(scene);
        stage.setTitle("Notifly");
        stage.setResizable(true);
        stage.setMinWidth(620);
        stage.setMinHeight(640);
        stage.centerOnScreen();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        ajustarVentana(fxml);
    }

    private static void ajustarVentana(String pantalla) {
        if (!(scene.getWindow() instanceof Stage stage)) return;
        double width;
        double height;
        switch (pantalla) {
            case "notelist" -> { width = 1200; height = 850; }
            case "notehistory" -> { width = 1280; height = 900; }
            case "reports" -> { width = 1000; height = 850; }
            case "contactlist" -> { width = 1120; height = 820; }
            case "templatelist" -> { width = 820; height = 760; }
            case "dashboard" -> { width = 1180; height = 850; }
            case "mainscreen" -> { width = 720; height = 760; }
            default -> { width = 1100; height = 820; }
        }
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double availableWidth = Math.max(600, bounds.getWidth() - 48);
        double availableHeight = Math.max(560, bounds.getHeight() - 72);
        width = Math.min(width, availableWidth);
        height = Math.min(height, availableHeight);
        stage.setResizable(true);
        stage.setMinWidth(Math.min(760, width));
        stage.setMinHeight(Math.min(620, height));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }

    /** Opens secondary administrator windows at useful sizes without exceeding the screen. */
    public static void ajustarVentanaSecundaria(Stage stage, double width, double height) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        width = Math.min(width, Math.max(600, bounds.getWidth() - 48));
        height = Math.min(height, Math.max(520, bounds.getHeight() - 72));
        stage.setResizable(true);
        stage.setMinWidth(Math.min(680, width));
        stage.setMinHeight(Math.min(560, height));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
