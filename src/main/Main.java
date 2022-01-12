package main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            populateTestData();
            root = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
    private static void populateTestData() {
        Product product1 = new Product(1,"Car",1776.73,6,2,999);
        int idx = Inventory.generatePartID();
        InHousePart Part1 = new InHousePart(idx,"Car",1776.73,6,2,999, 0);
        int idxTwo = Inventory.generatePartID();
        InHousePart Part2 = new InHousePart(idxTwo, "Brakes", 12.99, 10, 1, 20, 1);
        int idxThree = Inventory.generatePartID();
        OutSourcedPart Part3 = new OutSourcedPart(idxThree, "Tire", 3.99, 15, 1, 1, "test");
        Inventory.addPart(Part1);
        Inventory.addPart(Part2);
        Inventory.addPart(Part3);
        Inventory.addProduct(product1);
    }
}