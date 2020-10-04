package course.java.sdm.javafxui.windows;

import course.java.sdm.engine.entities.Order;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.managers.EngineManagerSingleton;
import course.java.sdm.engine.managers.OrderManagerSingleton;
import course.java.sdm.javafxui.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;


public class PlaceOrderWindowController {
    Stage window;

    private ArrayList<Product> products;
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> idCol;
    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, String> purchaseCategoryCol;
    @FXML private TableColumn<Product, Integer> priceCol;
    @FXML private Spinner<Double> amountSpinner;
    @FXML private Button addSelectedItemBtn;

    private boolean isDynamic;
    //private final HashMap<Integer, Double> selectedIdsToAmounts = new HashMap<>();
    private Order currentOrder;

    public PlaceOrderWindowController() {
        products = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        initControls();
    }

    private void initControls() {
        initSpinner();
    }

    private void initSpinner() {
        SpinnerValueFactory<Double> amountValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 1 , 0.5);
        amountSpinner.setValueFactory(amountValueFactory);
        TextFormatter formatter = new TextFormatter(amountValueFactory.getConverter(), amountValueFactory.getValue());
        amountSpinner.getEditor().setTextFormatter(formatter);
        // bidi-bind the values
        amountValueFactory.valueProperty().bindBidirectional(formatter.valueProperty());
    }

    public void placeOrderBtn_Clicked(ActionEvent event) {
        try{
            checkForSales();
            OrderManagerSingleton.getInstance().addOrder(currentOrder);
            FxmlUtils.showAlert("Order was registered successfully", "Hooray!");
            reviewOrder();
            closeWindow();
            window.close();
        } catch (Exception e) {
            FxmlUtils.showAlert(e.getMessage(), FxmlUtils.ERROR_HEADER);
        }
    }

    private void closeWindow() {
        window = (Stage) addSelectedItemBtn.getScene().getWindow();
        window.close();
    }

    private void reviewOrder() {
    }

    private void checkForSales() {
    }

    public void addSelectedItemBtn_Clicked(ActionEvent event) {
        try {
            if (amountSpinner.getValue() == null) {
                throw new NullPointerException("Please enter an amount to add");
            }
            if(productsTableView.getSelectionModel().isEmpty()) {
                throw new NullPointerException("Please choose an Item to add");
            }
            Product product = productsTableView.getSelectionModel().getSelectedItem();
            if(product.getPurchaseCategory().equals("Quantity")) {
                if (amountSpinner.getValue() != amountSpinner.getValue().intValue()) {
                    throw new Exception("tried to add a non-integer number for a Quantity product.");
                }
            }
            double amount = amountSpinner.getValue();
            int productId = product.getId();
            currentOrder.addProduct(productId, amount);
            FxmlUtils.showAlert("Added " + amount + " " + product.getName() + "to your order", "Product was added Successfully!");
        } catch (NullPointerException e) {
            FxmlUtils.showAlert(e.getMessage(), FxmlUtils.ERROR_HEADER);
        }
        catch (Exception e) {
            FxmlUtils.showAlert(e.getMessage(), FxmlUtils.ERROR_HEADER);
        }
        System.out.println();
    }

    public void initialSetup(Order order) {
        setOrder(order);
        setIsDynamic(order.getIsDynamic());
        if(isDynamic) {
            setProducts(EngineManagerSingleton.getInstance().getProductsList());
        } else {
            setProducts(order.getWhereFrom().get(0).getProductsList());
        }
    }

    public void setProducts(ArrayList<Product> productsList) {
        products = productsList;
        bindToTable();
    }

    private void bindToTable() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchaseCategoryCol.setCellValueFactory(new PropertyValueFactory<>("purchaseCategory"));
        productsTableView.setItems(products);
        if (isDynamic) {
            productsTableView.getColumns().remove(3);
            products.addAll(EngineManagerSingleton.getInstance().getSmartProductsList());
        } else {
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            products.addAll(this.products);
        }

    }

    public void setOrder(Order order) {
        currentOrder = order;
    }

    public void setIsDynamic(boolean isDynamic) {
        this.isDynamic = isDynamic;
    }
}