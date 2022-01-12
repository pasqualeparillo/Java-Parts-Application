package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.Part;
import model.OutSourcedPart;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addPartController {
    int idx = Inventory.generatePartID();
    @FXML private TextField addPartName;
    @FXML private TextField addPartInv;
    @FXML private TextField addPartCost;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartChangeable;

    @FXML private Label labelChange;
    @FXML private RadioButton inHouse;

    private String fxmlPath;

    @FXML
    void outSourcedToggle() {
        labelChange.setText("Company Name");
    }
    @FXML
    void inHouseToggle() {
        labelChange.setText("Machine ID");
    }
    @FXML
    void savePart(ActionEvent event) {
        try {
        String partName = addPartName.getText();
        int id = 0;
        double partPrice = Double.parseDouble(addPartCost.getText());
        int partMin = Integer.parseInt(addPartMin.getText());
        int partMax = Integer.parseInt(addPartMax.getText());
        int partInv = Integer.parseInt(addPartInv.getText());
            if(partName.isEmpty()) {
                generateError("nameError");
            } else if(partMin > partMax) {
                generateError("minMaxError");
            } else if(partInv < partMin || partInv > partMax) {
                generateError("invError");
            } else {
                if(inHouse.isSelected()) {
                    //generate in-house part
                    //was going to do if else but this handles if it's a string without unnecessary chaining.
                    try {
                        int partMachineID = Integer.parseInt(addPartChangeable.getText());
                        InHousePart inHousePart = new InHousePart(id, partName, partPrice, partInv, partMin, partMax, partMachineID);
                        inHousePart.setId(idx);
                        Inventory.addPart(inHousePart);
                        returnToHome(event);
                    } catch (Exception e) {
                        generateError("machineIDError");
                    }
                } else {
                    //generate outsourced part
                    String partCompanyName = addPartChangeable.getText();
                    OutSourcedPart outSourcedPart = new OutSourcedPart(id, partName, partPrice, partInv, partMin, partMax, partCompanyName);
                    outSourcedPart.setId(Inventory.generatePartID());
                    Inventory.addPart(outSourcedPart);
                    returnToHome(event);
                }
            }
        } catch (Exception e) {
            generateError("emptyError");
        }

    }
    //ERROR HANDLING
    private void generateError(String errorType) {
        String errorHeader = "";
        String errorContent = "";
        switch (errorType) {
            case "emptyError":
                errorHeader = "Cannot add product";
                errorContent = "One or more fields are empty";
                returnError(errorHeader, errorContent);
                break;
            case "minMaxError":
                errorHeader = "Cannot add product";
                errorContent = "Minimum cannot be greater than maximum";
                returnError(errorHeader, errorContent);
                break;
            case "invError":
                errorHeader = "Cannot add product";
                errorContent = "Inventory needs to be withing range of minimum & maximum";
                returnError(errorHeader, errorContent);
                break;
            case "machineIDError":
                errorHeader = "Cannot add product";
                errorContent = "Part required machine ID or please change to outsourced";
                returnError(errorHeader, errorContent);
                break;
            case "nameError":
                errorHeader = "Cannot add product";
                errorContent = "Please enter a valid name";
                returnError(errorHeader, errorContent);
                break;
        }

    }
    private void returnError(String errorHeader, String errorContent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorTitle = "Error";
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorHeader);
        alert.setContentText(errorContent);
        alert.showAndWait();
    }
    //HELPER FUNCTION
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

}
