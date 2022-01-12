package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {


    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(mainController.getProductToModify());
    }

}
