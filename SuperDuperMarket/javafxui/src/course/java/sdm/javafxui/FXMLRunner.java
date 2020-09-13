package course.java.sdm.javafxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class FXMLRunner extends Application {

   public final String FXML_PATH = "/course/java/sdm/javafxui/MainWindow.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource(FXML_PATH);
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();

        primaryStage.setTitle("Live Example");

        SDMController sdmController = loader.getController();
        sdmController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}