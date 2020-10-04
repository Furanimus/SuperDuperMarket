package course.java.sdm.javafxui.components;

import course.java.sdm.engine.entities.Order;
import course.java.sdm.engine.entities.Store;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleOrderStoreInfoController {

    @FXML private Label idInfo;
    @FXML private Label nameInfo;
    @FXML private Label ppkInfo;
    @FXML private Label distanceFromCustomerInfo;
    @FXML private Label deliveryCostInfo;

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty ppk;
    private SimpleStringProperty distanceFromCustomer;
    private SimpleStringProperty deliveryCost;

    Order order;
    Store store;

    public void setStore(Store store) {
        this.store = store;
        updateProperties();
    }

    private void updateProperties() {
        id.set(String.format("%,d", store.getId()));
        name.set(store.getName());
        ppk.set(String.format("%,d", store.getPPK()));
        distanceFromCustomer.set(String.format("%,.3f", order.getTargetLocation().measureDistance(store.getLocation())));
        deliveryCost.set(String.format("%,.3f", order.getDeliveryCost()));
    }

    public SingleOrderStoreInfoController() {
        id = new SimpleStringProperty();
        name = new SimpleStringProperty();
        ppk = new SimpleStringProperty();
        distanceFromCustomer = new SimpleStringProperty();
        deliveryCost = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        idInfo.textProperty().bind(Bindings.format("%,d", id));
        nameInfo.textProperty().bind(Bindings.format("%,s", name));
        ppkInfo.textProperty().bind(Bindings.format("%,d", ppk));
        distanceFromCustomerInfo.textProperty().bind(Bindings.format("%,.3f", distanceFromCustomer));
        deliveryCostInfo.textProperty().bind(Bindings.format("%,.3f", deliveryCost));



//        totalWordsLabel.textProperty().bind(Bindings.format("%,d", totalWords));
//        totalLinesLabel.textProperty().bind(Bindings.format("%,d", totalLines));
//        distinctWordsSoFar.textProperty().bind(Bindings.format("%,d", totalDistinctWords));
//        totalCurrentProcessedWords.textProperty().bind(Bindings.format("%,d", totalProcessedWords));
//        selectedFileName.textProperty().bind(selectedFileProperty);
//        collectMetadataButton.disableProperty().bind(isFileSelected.not());
//        calculateHistogramButton.disableProperty().bind(isMetadataCollected.not());
    }


}
