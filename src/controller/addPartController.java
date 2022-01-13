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

/**
 * Add part controller used to create a new part
 */
public class addPartController {
    /**
     * generate a new ID.
     */
    int idx = Inventory.generatePartID();
    /**
     * set FXML fields for use in methods
     */
    @FXML private TextField addPartName;
    @FXML private TextField addPartInv;
    @FXML private TextField addPartCost;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartChangeable;

    @FXML private Label labelChange;
    @FXML private RadioButton inHouse;

    private String fxmlPath;

    /**
     * toggles radio buttons if outsourced is checked
     */
    @FXML
    void outSourcedToggle() {
        labelChange.setText("Company Name");
    }
    /**
     * toggles radio buttons if inhouse is checked
     */
    @FXML
    void inHouseToggle() {
        labelChange.setText("Machine ID");
    }


    /**
     * RUNTIME ERROR before I was setting the values above the try causing the program to behave unpredictably & return an error
     * FUTURE ENHANCEMENT I believe there must be a better way to handle this without nested try/catch & if statements. Should revisit in the future
     * used to save a part, checks that min !> max & partmin < partmax
     * @param event - action event triggering a save on click
     */
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
    /**
     * Error handling method returning a modal from switch statement cases
     * @param errorType - the error type you would like to set e.g. "emptyError"
     */
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
    /**
     * Used to generate an error
     * @param errorHeader - the header text you would like to set
     * @param errorContent - to error content you would like to set
     */
    private void returnError(String errorHeader, String errorContent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorTitle = "Error";
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorHeader);
        alert.setContentText(errorContent);
        alert.showAndWait();
    }
    /**
     * Helper function used to send program back to home scene
     * @param event -  an event handler passed to change scene
     */
    @FXML
    public void returnToHome(ActionEvent event) {
        fxmlPath = "/view/MainScene.fxml";
        switchScene(event);
    }
    /**
     * Helper function used pop up a cancel modal and send back to home screen
     * @param event - an event handler passed to change scene
     */
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
    /**
     * Helper function used in programs to close a scene and open another
     * @param event - an event handler passed to change scene
     */
    public void switchScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Application");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
