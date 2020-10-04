package course.java.sdm.javafxui;

import course.java.sdm.javafxui.windows.SDMMainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.net.URL;

public class FXMLRunner extends Application {
   public static final String FXML_PATH = "/course/java/sdm/javafxui/";
   public static final String MAIN_WINDOW_FXML = "windows/MainWindow.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(FXML_PATH + MAIN_WINDOW_FXML);
        loader.setLocation(mainFXML);
        ScrollPane root = loader.load();
        primaryStage.setTitle("Super Duper Market");
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(200);
        SDMMainWindowController sdmMainWindowController = loader.getController();
        sdmMainWindowController.setPrimaryStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}