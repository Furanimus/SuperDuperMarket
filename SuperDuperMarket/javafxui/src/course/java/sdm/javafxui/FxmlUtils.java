package course.java.sdm.javafxui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FxmlUtils {
    public static final String ERROR_HEADER = "An Error Occurred";

    public static void showAlert(String msg, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}
