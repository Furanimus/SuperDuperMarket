package course.java.sdm.javafxui;
import course.java.sdm.engine.commands.*;
import course.java.sdm.engine.managers.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SDMController {

    @FXML private Button loadFileBtn;
    @FXML private Button showStoresBtn;
    @FXML private Button showProductsBtn;
    @FXML private Button makeOrderBtn;
    @FXML private Button orderHistoryBtn;
    @FXML private Button locationMapBtn;

    private Map<String, MenuItem> commandsMap = new HashMap<>();

    private EngineManagerSingleton engineManager;
    private StoreManagerSingleton storeManager;
    private OrderManagerSingleton orderManager;
    private SimpleBooleanProperty isFileLoaded;
    private Stage primaryStage;

    public SDMController() {
        isFileLoaded = new SimpleBooleanProperty(false);
        initCommandMap();
    }

    private void initCommandMap() {
        commandsMap.put("ReadFileXml", new ReadFileXml());
        commandsMap.put("ShowProductsInfo", new ShowProductsInfo());
        commandsMap.put("ShowStoreInfo", new ShowStoreInfo());
        commandsMap.put("MakePurchase", new MakePurchase());
        commandsMap.put("ShowPurchaseHistory", new ShowPurchaseHistory());
        commandsMap.put("ExitSystem", new ExitSystem());
    }

    @FXML
    public void initialize() {
        initManagers();
        bindDisablePropertyButtons();
    }

    private void initManagers() {
        engineManager = EngineManagerSingleton.getInstance();
        storeManager = StoreManagerSingleton.getInstance();
        orderManager = OrderManagerSingleton.getInstance();
    }

    @FXML
    void loadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an XML file to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        engineManager.setFilePath(fileChooser.showOpenDialog(primaryStage).getAbsolutePath());
        commandsMap.get("ReadFileXml").execute(engineManager);
        isFileLoaded.set(engineManager.getIsFileLoaded());

        //loadFileBtn.setText("Pressed");
        //isFileLoaded.set(true);
               /* FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an XML file to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
        */
    }

    private void bindDisablePropertyButtons() {
        showProductsBtn.disableProperty().bind(isFileLoaded.not());
        showStoresBtn.disableProperty().bind(isFileLoaded.not());
        showProductsBtn.disableProperty().bind(isFileLoaded.not());
        makeOrderBtn.disableProperty().bind(isFileLoaded.not());
        orderHistoryBtn.disableProperty().bind(isFileLoaded.not());
        locationMapBtn.disableProperty().bind(isFileLoaded.not());
    }

    @FXML
    void showAvailableProducts(ActionEvent event) {

    }

    @FXML
    void showAvailablleStores(ActionEvent event) {

    }

    @FXML
    void showOrderHistory(ActionEvent event) {

    }

    @FXML
    void startOrder(ActionEvent event) {

    }

    @FXML
    void showLocationMap(ActionEvent event) {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}