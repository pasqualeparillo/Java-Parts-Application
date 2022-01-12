package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class mainController implements Initializable {

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

    //VARIABLES FOR METHODS
    private static Part modifyPartIdx;
    private static Product modifyProductIdx;
    private static String fxmlPath;

    //START DELETE METHODS
    public void confirmDeletePartModal(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("Are you sure you would like to delete this ");
            alertConfirm.setContentText("Please confirm");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.removePart(partTableView.getSelectionModel().getSelectedItem());
            }
        } else if(Inventory.getAllParts().isEmpty()) {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("All parts have been deleted");
            alertConfirm.setContentText("No remaining parts to delete");
            alertConfirm.showAndWait();
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No part selected");
            alertConfirm.setContentText("Please select a part");
            alertConfirm.showAndWait();
        }
    }

    public void confirmDeleteProductModal(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct != null) {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("Are you sure you would like to delete this ");
            alertConfirm.setContentText("Please confirm");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.removeProduct(productTableView.getSelectionModel().getSelectedItem());
            }
        } else if(Inventory.getAllProducts().isEmpty()) {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("All products have been deleted");
            alertConfirm.setContentText("No remaining products to delete");
            alertConfirm.showAndWait();
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No product selected");
            alertConfirm.setContentText("Please select a product");
            alertConfirm.showAndWait();
        }
    }
    //END DELETE METHODS

    //START ADD METHODS
    @FXML
    public void addProduct(ActionEvent actionEvent) throws IOException {
        fxmlPath = "/view/AddProduct.fxml";
        switchScene(actionEvent);
    }
    @FXML
    public void addPart(ActionEvent actionEvent) throws IOException {
        fxmlPath = "/view/AddPart.fxml";
        switchScene(actionEvent);
    }
    //END ADD METHODS

    //START MODIFY METHODS
    @FXML
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        modifyProductIdx = productTableView.getSelectionModel().getSelectedItem();
        if(modifyProductIdx != null) {
            fxmlPath = "/view/ModifyProduct.fxml";
            switchScene(actionEvent);
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("You need to select a product");
            alertConfirm.showAndWait();
        }
    }
    @FXML
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        modifyPartIdx = partTableView.getSelectionModel().getSelectedItem();
        if(modifyPartIdx != null) {
            fxmlPath = "/view/ModifyPart.fxml";
            switchScene(actionEvent);
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("You need to select a part");
            alertConfirm.showAndWait();
        }

    }
    public static Product getProductToModify() {
        return modifyProductIdx;
    }
    public static Part getPartToModify() {
        return modifyPartIdx;
    }
    //END MODIFY METHODS

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
    public void switchScene(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isInt(String searchQuery) {
        try {
            Integer.parseInt(searchQuery);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    @FXML
    void resetText(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }
    @FXML
    public void exitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
