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
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {
    private String fxmlPath;
    public Product selectedProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML private TextField productID;
    @FXML private TextField productName;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
    @FXML private TextField productPrice;
    @FXML private TextField productInv;
    @FXML private TextField partSearch;

    @FXML private TableColumn<Part, Double> assPartPrice;
    @FXML private TableColumn<Part, Integer> assPartStock;
    @FXML private TableColumn<Part, String> assPartName;
    @FXML private TableColumn<Part, Integer> assPartID;
    @FXML private TableView<Part> assPartView;

    @FXML private TableColumn<Part, Double> partPrice;
    @FXML private TableColumn<Part, Integer> partStock;
    @FXML private TableColumn<Part, Integer> partID;
    @FXML private TableView<Part> partsTableView;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = mainController.getProductToModify();
        associatedParts = selectedProduct.getAssociatedParts();
        assPartView.setItems(associatedParts);
        String partName = selectedProduct.getName();
        //SET FORM
        productID.setText(Integer.toString(selectedProduct.getId()));
        productName.setText(partName);
        productMin.setText(Integer.toString(selectedProduct.getMin()));
        productMax.setText(Integer.toString(selectedProduct.getMax()));
        productPrice.setText(Double.toString(selectedProduct.getPrice()));
        productInv.setText(Integer.toString(selectedProduct.getStock()));

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableView.setItems(Inventory.getAllParts());

        assPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    @FXML
    public void updateProduct(ActionEvent event) throws IOException {
       try {
           int id = selectedProduct.getId();
           String productName = selectedProduct.getName();
           int productMinValue = Integer.parseInt(productMin.getText());
           int productMaxValue = Integer.parseInt(productMax.getText());
           double productPriceValue = Double.parseDouble(productPrice.getText());
           int productInvValue = Integer.parseInt(productInv.getText());
           if (productName.isEmpty()) {
               generateError("nameError");
           } else if (productMinValue > productMaxValue) {
               generateError("minMaxError");
           } else if (productInvValue < productMinValue || productInvValue > productMaxValue) {
               generateError("invError");
           } else {
               Product addProduct = new Product(id, productName, productPriceValue, productInvValue, productMinValue, productMaxValue);
               for(Part p:  associatedParts) {
                    addProduct.addAssParts(p);
               }
               Inventory.updateProduct(selectedProduct, addProduct);
               returnToHome(event);
           }
       }catch (Exception e) {

       }
    }
    @FXML
    public void addToAssPart() {
        Part assPart = partsTableView.getSelectionModel().getSelectedItem();
        if(assPart != null) {
            generateError("confirm");
            associatedParts.add(assPart);
            assPartView.setItems(associatedParts);
            assPartView.refresh();
        } else {
            generateError("partError");
        }
    }
    @FXML
    public void removeFromAssPart() {
        Part assPart = assPartView.getSelectionModel().getSelectedItem();
        if(assPart != null) {
            generateError("confirm");
            associatedParts.remove(assPart);
            assPartView.setItems(selectedProduct.getAssociatedParts());
            assPartView.refresh();
        } else {
            generateError("assPartError");
        }
    }
    //SEARCH
    @FXML
    private void searchForPart(KeyEvent event) {
        if(partSearch.getText() != null) {
            if (!partSearch.getText().trim().isEmpty()) {
                partsTableView.setItems(searchForPart(partSearch.getText()));
            } else{
                partsTableView.setItems(Inventory.getAllParts());
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
    //ERROR HANDLING
    private void generateError(String errorType) {
        String errorHeader = "";
        String errorContent = "";
        String type = "";
        switch (errorType) {
            case "emptyError":
                errorHeader = "Cannot add product";
                errorContent = "One or more fields are empty";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "minMaxError":
                errorHeader = "Cannot add product";
                errorContent = "Minimum cannot be greater than maximum";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "invError":
                errorHeader = "Cannot add product";
                errorContent = "Inventory needs to be withing range of minimum & maximum";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "machineIDError":
                errorHeader = "Cannot add product";
                errorContent = "Part required machine ID or please change to outsourced";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "nameError":
                errorHeader = "Cannot add product";
                errorContent = "Please enter a valid name";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "partError":
                errorHeader = "Cannot associate part";
                errorContent = "Please select a part";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "assPartError":
                errorHeader = "Cannot remove associated part";
                errorContent = "Please select a part";
                type = "ERROR";
                returnError(errorHeader, errorContent, type);
                break;
            case "confirm":
                errorHeader = "Are you sure?";
                errorContent = "Please confirm";
                type = "CONFIRMATION";
                returnError(errorHeader, errorContent, type);
                break;
        }

    }
    private void returnError(String errorHeader, String errorContent, String type) {
        if(type == "CONFIRMATION") {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String errorTitle = "Error";
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorContent);
            alert.showAndWait();
        }
        if(type == "ERROR") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String errorTitle = "Error";
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorContent);
            alert.showAndWait();
        }

    }
    //HELPER METHODS
    public boolean isInt(String searchQuery) {
        try {
            Integer.parseInt(searchQuery);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    @FXML
    public void returnToHome(ActionEvent event) {
        fxmlPath = "/view/MainScene.fxml";
        switchScene(event);
    }
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alertConfirm.setTitle("Are you sure you would like to delete this ");
        alertConfirm.setContentText("Please confirm");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            fxmlPath = "/view/MainScene.fxml";
            switchScene(event);
        }
    }
    public void switchScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Page");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
