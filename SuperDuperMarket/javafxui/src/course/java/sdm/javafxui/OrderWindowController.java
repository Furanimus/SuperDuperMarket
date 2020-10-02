package course.java.sdm.javafxui;
import course.java.sdm.engine.entities.Customer;
import course.java.sdm.engine.entities.Order;
import course.java.sdm.engine.entities.Store;
import course.java.sdm.engine.managers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderWindowController {

    @FXML private Button proceedToShopBtn;
    @FXML private ComboBox<Customer> chooseCustomerComboBox;
    @FXML private ListView<Store> storesListView;
    @FXML private DatePicker orderDatePicker;
    @FXML private CheckBox dynamicOrderCheckBox;
    @FXML private Button goToMainPageBtn;

    EngineManagerSingleton engineManager;
    StoreManagerSingleton storeManager;
    CustomerManagerSingleton customerManager;

    @FXML
    public void initialize() {
        initManagers();
        initControls();
    }

    private void initControls() {
        initComboBox();
        initListView();
    }

    private void initListView() {
        storesListView.disableProperty().bind(dynamicOrderCheckBox.selectedProperty());
        storesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for (Store store : storeManager.getStoresList()) {
            storesListView.getItems().addAll(store);
        }
    }

    private void initComboBox() {
        List<Customer> customers = new ArrayList<>(customerManager.getCustomerList());
        chooseCustomerComboBox.getItems().setAll(customers);
    }

    private void initManagers() {
        engineManager = EngineManagerSingleton.getInstance();
        storeManager = StoreManagerSingleton.getInstance();
        customerManager = CustomerManagerSingleton.getInstance();
    }

    @FXML
    void proceedToShopBtn_Clicked(ActionEvent event) {
        try {
            validateFields();
            if (dynamicOrderCheckBox.isSelected()) {

            } else {
                Order order = new Order(chooseCustomerComboBox.getValue().getId()
                        , storesListView.getSelectionModel().getSelectedItem()
                        , orderDatePicker.getValue());
                beginOrder(order);

            }
            //continue
        }
        catch (Exception e) {
            //error message box
            System.out.println(e.getMessage());

        }
    }

    private void beginOrder(Order order) throws IOException {
        Stage orderWindow = new Stage();
        orderWindow.initModality(Modality.APPLICATION_MODAL);
        GridPane rootContainer = FXMLLoader.load(getClass().getResource("PlaceOrderWindow.fxml"));
        orderWindow.setScene(new Scene(rootContainer));
        orderWindow.show();
    }

    private boolean validateFields() {
        boolean result = false;
        result = chooseCustomerComboBox.getSelectionModel().isEmpty();
        result = result && (!storesListView.getSelectionModel().getSelectedItems().isEmpty());

        return result;
    }

    @FXML
    void goToMainPageBtn_Clicked(ActionEvent event) throws IOException {
        Parent orderWidowParent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene orderWindowScene = new Scene(orderWidowParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(orderWindowScene);
        stage.show();
    }
}
