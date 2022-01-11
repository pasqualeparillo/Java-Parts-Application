package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import org.w3c.dom.Text;

import javax.naming.Name;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class mainController implements Initializable {

    @FXML private Button deleteButton;

    //PARTS TABLE
    @FXML private TextField partSearch;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    //PRODUCT TABLE
    @FXML private TextField productSearch;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    @FXML
    //START DELETE METHODS
    public void confirmDeletePartModal(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("Are you sure you would like to delete this ");
            alertConfirm.showAndWait();
            Inventory.removePart(partTableView.getSelectionModel().getSelectedItem());
        } else if(Inventory.getAllParts().isEmpty()) {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("All parts have been deleted");
            alertConfirm.showAndWait();
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No part selected");
            alertConfirm.showAndWait();
        }
    }
    public void confirmDeleteProductModal(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct != null) {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("Are you sure you would like to delete this ");
            alertConfirm.showAndWait();
            Inventory.removeProduct(productTableView.getSelectionModel().getSelectedItem());
        } else if(Inventory.getAllProducts().isEmpty()) {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("All products have been deleted");
            alertConfirm.showAndWait();
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No product selected");
            alertConfirm.showAndWait();
        }
    }
    //START SEARCH METHODS
    @FXML
    private void searchForPart(KeyEvent event) {
        if(partSearch.getText() != null) {
            if (!partSearch.getText().trim().isEmpty()) {
                partTableView.setItems(searchForPart(partSearch.getText()));
            } else{
                partTableView.setItems(Inventory.getAllParts());
            }
        }
    }

    private ObservableList<Part> searchForPart(String search) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        try {
            int searchedInt = Integer.parseInt(search);
            if(isInt(search)) {
                foundParts = Inventory.lookupPartID(searchedInt);
            }
        }
        catch  (NumberFormatException e) {
            foundParts =  Inventory.lookupPartName(search);
        }
        return foundParts;
    }
    @FXML
    private void searchForProduct(KeyEvent event) {
        if(productSearch.getText() != null) {
            if (!productSearch.getText().trim().isEmpty()) {
                productTableView.setItems(searchForProduct(productSearch.getText()));
            } else{
                productTableView.setItems(Inventory.getAllProducts());
            }
        }
    }

    private ObservableList<Product> searchForProduct(String search) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        try {
            int searchedInt = Integer.parseInt(search);
            if(isInt(search)) {
                foundProducts = Inventory.lookupProductID(searchedInt);
            }
        }
        catch  (NumberFormatException e) {
            foundProducts =  Inventory.lookupProductName(search);
        }
        return foundProducts;
    }
    //END SEARCH METHODS
    @FXML
    void resetText(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    //HELPER FUNCTIONS
    public boolean isInt(String search) {
        try {
            Integer.parseInt(search);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
