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

/**
 * Main controller used to render the initial screen & render the initial parts/products
 */
public class mainController implements Initializable {
    /**
     * Set the FXML tables & fields
     */
    @FXML private TextField partSearch;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField productSearch;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    private static Part modifyPartIdx;
    private static Product modifyProductIdx;
    private static String fxmlPath;
    /**
     * Confirms the user would like to delete the selected Part, checks if they have a Part selected
     * @param event - triggered from on click button event
     */
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
    //
    /**
     * FUTURE ENHANCEMENT I believe the way I'm handling errors can be done much better. Maybe extract it to its own class
     * Confirms the user would like to delete the selected Product, checks if they have a Product selected
     * @param event - triggered from on click button event
     */
    public void confirmDeleteProductModal(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct != null && selectedProduct.getAssociatedParts().isEmpty()) {
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
        } else if(selectedProduct.getAssociatedParts().isEmpty() == false) {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("Cannot delete product");
            alertConfirm.setContentText("Please remove associated parts");
            alertConfirm.showAndWait();
        }else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alertConfirm.setTitle("No product selected");
            alertConfirm.setContentText("Please select a product");
            alertConfirm.showAndWait();
        }
    }
    /**
     * Switch's user to the add Product page
     * @param actionEvent - triggered from on click button event
     */
    @FXML
    public void addProduct(ActionEvent actionEvent) throws IOException {
        fxmlPath = "/view/AddProduct.fxml";
        switchScene(actionEvent, "Add Product");
    }
    /**
     * Switch's user to the add Part page
     * @param actionEvent - triggered from on click button event
     */
    @FXML
    public void addPart(ActionEvent actionEvent) throws IOException {
        fxmlPath = "/view/AddPart.fxml";
        switchScene(actionEvent, "Add Part");
    }
    /**
     * Switch's user to the modify Product page
     * @param actionEvent - triggered from on click button event
     */
    @FXML
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        modifyProductIdx = productTableView.getSelectionModel().getSelectedItem();
        if(modifyProductIdx != null) {
            fxmlPath = "/view/ModifyProduct.fxml";
            switchScene(actionEvent, "Modify Product");
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("You need to select a product");
            alertConfirm.showAndWait();
        }
    }
    /**
     * Switch's user to the modify Part page
     * @param actionEvent - triggered from on click button event
     */
    @FXML
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        modifyPartIdx = partTableView.getSelectionModel().getSelectedItem();
        if(modifyPartIdx != null) {
            fxmlPath = "/view/ModifyPart.fxml";
            switchScene(actionEvent, "Modify Part");
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alertConfirm.setTitle("You need to select a part");
            alertConfirm.showAndWait();
        }

    }
    /**
     * Gets & returns the Product you want ot modify
     */
    public static Product getProductToModify() {
        return modifyProductIdx;
    }
    /**
     * Gets & returns the Part you want ot modify
     */
    public static Part getPartToModify() {
        return modifyPartIdx;
    }

    /**
     * Search method - if search is not null search for a part || if it is null return all parts calls searchForPart & pass's value
     * @param event - triggered via a keypress event.
     */
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
    /**
     * Search method - checks if search value is an int or string -> calls search methods for either depending. Returns result
     * @param search - string or integer you would like to search for
     */
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
    /**
     * Search method - if search is not null search for a Product || if it is null return all Product's calls searchForPart & pass's value
     * @param event - triggered via a keypress event.
     */
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
    /**
     * Search method - checks if search value is an int or string -> calls search methods for either depending. Returns result
     * @param search - string or integer you would like to search for
     */
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
    /**
     * Sets & renders the initial list views
     * @param url
     * @param resourceBundle
     */
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
    /**
     * Helper function used in programs to close a scene and open another
     * @param event - an event handler passed to change scene
     */
    public void switchScene(ActionEvent event, String title)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Helper function used to check if a value is an integer or not
     * @param searchQuery - an event handler passed to change scene
     */
    public boolean isInt(String searchQuery) {
        try {
            Integer.parseInt(searchQuery);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    /**
     * Helper function used to clear the default text that was left in a search box
     * @param event - an event handler passed clear the text on click
     */
    @FXML
    void resetText(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }
    /**
     * Helper function used to exit the program
     * @param actionEvent - an event handler passed
     */
    @FXML
    public void exitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
