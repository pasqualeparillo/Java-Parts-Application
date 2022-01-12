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
import model.OutSourcedPart;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyPartController implements Initializable {
    //used to pass data between controllers
    public Part selectedPart;

    private String fxmlPath;

    @FXML private TextField addPartID;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInv;
    @FXML private TextField addPartCost;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMax;
    @FXML private RadioButton outSourced;
    @FXML private RadioButton inHouse;
    @FXML private TextField addPartChangeable;
    @FXML private Label labelChange;

    @FXML
    void outSourcedToggle() {
        labelChange.setText("Company Name");
        addPartChangeable.clear();
    }
    @FXML
    void inHouseToggle() {
        labelChange.setText("Machine ID");
        addPartChangeable.clear();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = mainController.getPartToModify();
        addPartID.getText();
        String partName = selectedPart.getName();
        //SET FORM
        addPartID.setText(Integer.toString(selectedPart.getId()));
        addPartName.setText(partName);
        addPartMin.setText(Integer.toString(selectedPart.getMin()));
        addPartMax.setText(Integer.toString(selectedPart.getMax()));
        addPartCost.setText(Double.toString(selectedPart.getPrice()));
        addPartInv.setText(Integer.toString(selectedPart.getStock()));

        if (selectedPart instanceof InHousePart) {
            inHouse.setSelected(true);
            labelChange.setText("Machine ID");
            addPartChangeable.setText(Integer.toString(((InHousePart) selectedPart).getMachineID()));
        }
        if (selectedPart instanceof OutSourcedPart) {
            outSourced.setSelected(true);
            String companyName = String.valueOf(((OutSourcedPart) selectedPart).getCompanyName());
            labelChange.setText("Company Name");
            addPartChangeable.setText(companyName);
        }
    }

    @FXML
    public void savePart(ActionEvent event){
        int id = selectedPart.getId();
        String partName = selectedPart.getName();
        int partMin = Integer.parseInt(addPartMin.getText());
        int partMax = Integer.parseInt(addPartMax.getText());
        double partPrice = Double.parseDouble(addPartCost.getText());
        int partInv = Integer.parseInt(addPartInv.getText());

        try {
            if (partName.isEmpty()) {
                generateError("nameError");
            } else if (partMin > partMax) {
                generateError("minMaxError");
            } else if (partInv < partMin || partInv > partMax) {
                generateError("invError");
            } else {
                if (inHouse.isSelected()) {
                    //generate in-house part
                    //was going to do if else but this handles if it's a string without unnecessary chaining.
                    try {
                        int partMachineID = Integer.parseInt(addPartChangeable.getText());
                        InHousePart inHousePart = new InHousePart(id, partName, partPrice, partInv, partMin, partMax, partMachineID);
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
            case "emptyError" -> {
                errorHeader = "Cannot add product";
                errorContent = "One or more fields are empty";
                returnError(errorHeader, errorContent);
            }
            case "minMaxError" -> {
                errorHeader = "Cannot add product";
                errorContent = "Minimum cannot be greater than maximum";
                returnError(errorHeader, errorContent);
            }
            case "invError" -> {
                errorHeader = "Cannot add product";
                errorContent = "Inventory needs to be withing range of minimum & maximum";
                returnError(errorHeader, errorContent);
            }
            case "machineIDError" -> {
                errorHeader = "Cannot add product";
                errorContent = "Part required machine ID or please change to outsourced";
                returnError(errorHeader, errorContent);
            }
            case "nameError" -> {
                errorHeader = "Cannot add product";
                errorContent = "Please enter a valid name";
                returnError(errorHeader, errorContent);
            }
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
    //HELPER METHODS
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
