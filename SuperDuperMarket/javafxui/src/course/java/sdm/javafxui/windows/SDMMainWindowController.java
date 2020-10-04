package course.java.sdm.javafxui.windows;
import course.java.sdm.engine.commands.*;
import course.java.sdm.engine.commands.MenuItem;
import course.java.sdm.engine.entities.*;
import course.java.sdm.engine.managers.*;
import course.java.sdm.javafxui.components.SingleOrderStoreInfoController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SDMMainWindowController {
    //FXML Fields
    @FXML private Button loadFileBtn;
    @FXML private Button showStoresBtn;
    @FXML private Button showProductsBtn;
    @FXML private Button makeOrderBtn;
    @FXML private Button showCustomersBtn;
    @FXML private Button orderHistoryBtn;
    @FXML private Button locationMapBtn;
    @FXML private Accordion accordionInfo;

    @FXML private TabPane mainTabView;
    @FXML private Tab productsTab;
    @FXML private Tab customersTab;
    @FXML private Tab orderHistoryTab;
    @FXML private Tab storesInfoTab;

    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> customerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> customerLocationCol;
    @FXML private TableColumn<Customer, Integer> customerOrdersMadeCol;
    @FXML private TableColumn<Customer, Double> customerAvgOrderNoDeliveryCol;
    @FXML private TableColumn<Customer, Double> customerAvgDeliveryPriceCol;

    //Product TableView
    @FXML private TableView<SmartProduct> productsTableView;
    @FXML private TableColumn<SmartProduct, Integer> productIdCol;
    @FXML private TableColumn<SmartProduct, String> productNameCol;
    @FXML private TableColumn<SmartProduct, String> productPurchaseCategoryCol;
    @FXML private TableColumn<SmartProduct, Double> productAvgPriceCol;
    @FXML private TableColumn<SmartProduct, Integer> productSoldInNumOfStoresCol;
    @FXML private TableColumn<SmartProduct, Double> productTimesSoldCol;

    //Managers
    private EngineManagerSingleton engineManager;
    private StoreManagerSingleton storeManager;
    private OrderManagerSingleton orderManager;
    private CustomerManagerSingleton customerManager;

    //Properties
    private SimpleBooleanProperty isFileLoaded;
    private SimpleStringProperty edan;

    private final Map<String, MenuItem> commandsMap = new HashMap<>();
    private Stage primaryStage;

    public SDMMainWindowController() {
        isFileLoaded = new SimpleBooleanProperty(false);
        customerTableView = new TableView<>();
        initCommandMap();
    }

    private void initCommandMap() {
        commandsMap.put("ReadFileXml", new ReadFileXml());
        commandsMap.put("ShowProductsInfo", new ShowProductsInfo());
        commandsMap.put("ShowStoreInfo", new ShowStoreInfo());
        commandsMap.put("MakePurchase", new MakePurchase());
        commandsMap.put("ShowCustomerInfo", new ShowCustomerInfo());
        commandsMap.put("ShowPurchaseHistory", new ShowPurchaseHistory());
        commandsMap.put("ExitSystem", new ExitSystem());
    }

    @FXML
    public void initialize() {
        initManagers();
        isFileLoaded.setValue(engineManager.getIsFileLoaded());
        mainTabView.getSelectionModel().getSelectedItem().getOnSelectionChanged().handle(null);
        mainTabView.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        bindDisablePropertyButtons();
        //bindStringProperties();
    }

    private void initManagers() {
        engineManager = EngineManagerSingleton.getInstance();
        storeManager = StoreManagerSingleton.getInstance();
        orderManager = OrderManagerSingleton.getInstance();
        customerManager = CustomerManagerSingleton.getInstance();
    }

    @FXML
    private void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an XML file to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        engineManager.setFilePath(fileChooser.showOpenDialog(primaryStage).getAbsolutePath());
        String msg = (String) commandsMap.get("ReadFileXml").execute(engineManager);
        if (engineManager.getIsLastFileLoadedSuccessfully()) {
            clearTableViews();
            isFileLoaded.set(engineManager.getIsFileLoaded());
            engineManager.updateSmartProductsStatistics();
            mainTabView.getSelectionModel().getSelectedItem().getOnSelectionChanged().handle(null);
        }

        showLoadResult(msg);
    }

    private void clearTableViews() {
        customerTableView.getItems().clear();
        productsTableView.getItems().clear();
    }

    private void showLoadResult(String msg) {
        Alert msgBox = new Alert(Alert.AlertType.INFORMATION, msg);
        msgBox.setTitle("Loading File Result");
        msgBox.showAndWait();
    }

    private void bindDisablePropertyButtons() {
        showProductsBtn.disableProperty().bind(isFileLoaded.not());
        showStoresBtn.disableProperty().bind(isFileLoaded.not());
        showProductsBtn.disableProperty().bind(isFileLoaded.not());
        showCustomersBtn.disableProperty().bind(isFileLoaded.not());
        makeOrderBtn.disableProperty().bind(isFileLoaded.not());
        orderHistoryBtn.disableProperty().bind(isFileLoaded.not());
        locationMapBtn.disableProperty().bind(isFileLoaded.not());
        mainTabView.disableProperty().bind(isFileLoaded.not());
    }

    @FXML //TODO it's temporary - fix null controller
    void showAvailableProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("SingleOrderStoreInfo");
        loader.setLocation(url);
        SingleOrderStoreInfoController controller = loader.getController();
        controller.setStore(StoreManagerSingleton.getInstance().getVendor(1));
        GridPane root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private TitledPane createInfoTile(String title) {
        return new TitledPane(title, new ScrollPane());
    }

    @FXML
    void showAvailableStores(ActionEvent event) {

    }

    @FXML
    void showOrderHistory(ActionEvent event) {

    }

    @FXML
    void showRegisteredUsers(ActionEvent event) {

    }

    @FXML
    void startOrder(ActionEvent event) {

    }

    @FXML
    void showLocationMap(ActionEvent event) {

    }

    @FXML
    void showOrderScreenBtn_Clicked(ActionEvent event) throws IOException {
        Parent orderWidowParent = FXMLLoader.load(getClass().getResource("RootOrderWindow.fxml"));
        Scene orderWindowScene = new Scene(orderWidowParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(orderWindowScene);
        stage.show();
    }

    @FXML
    void orderHistoryTab_Changed(Event event) {
        if (orderHistoryTab.isSelected()) {
            System.out.println("Bye Edan history");
        }
    }

    @FXML
    void productsTab_Changed(Event event) {
        if(engineManager != null && productsTab.isSelected()) {
            ObservableList<SmartProduct> products = FXCollections.observableArrayList();
            productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productPurchaseCategoryCol.setCellValueFactory(new PropertyValueFactory<>("purchaseCategory"));
            productAvgPriceCol.setCellValueFactory(new PropertyValueFactory<>("avgPrice"));
            productSoldInNumOfStoresCol.setCellValueFactory(new PropertyValueFactory<>("numberOfStoresSoldIn"));
            productTimesSoldCol.setCellValueFactory(new PropertyValueFactory<>("timesSold"));

            productsTableView.setItems(products);
            products.addAll(engineManager.getSmartProductsList());

            //productIdCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            System.out.println("Hi Edan");

        }
    }

    @FXML
    void customersTab_Changed(Event event) {
        if(customersTab.isSelected()) {
            ObservableList<Customer> customers = FXCollections.observableArrayList();
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerLocationCol.setCellValueFactory(new PropertyValueFactory<>("locationStr"));
            customerOrdersMadeCol.setCellValueFactory(new PropertyValueFactory<>("numOfOrders"));
            customerAvgOrderNoDeliveryCol.setCellValueFactory(new PropertyValueFactory<>("avgOrderPriceWithoutDelivery"));
            customerAvgDeliveryPriceCol.setCellValueFactory(new PropertyValueFactory<>("avgDeliveryPrice"));

            customerTableView.setItems(customers);
            customers.addAll(customerManager.getCustomerList());
        }


//        createObservableList(engineManager.getCustomerManager().getCustomers);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}