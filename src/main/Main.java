package main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * Main view for the program
 */
public class Main extends Application {
    /**
     * starts the program & renders the stage
     */
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            populateTestData();
            root = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Inventory Application");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Generates dummy data
     */
    private static void populateTestData() {
        Product product1 = new Product(1,"Product1",38.1,6,2,100);
        int idx = Inventory.generatePartID();
        InHousePart Part1 = new InHousePart(idx,"Example2",12.90,2,2,200, 0);
        int idxTwo = Inventory.generatePartID();
        InHousePart Part2 = new InHousePart(idxTwo, "Example3", 12.99, 10, 1, 20, 1);
        int idxThree = Inventory.generatePartID();
        OutSourcedPart Part3 = new OutSourcedPart(idxThree, "Example4", 3.99, 20, 1, 1, "test");
        Inventory.addPart(Part1);
        Inventory.addPart(Part2);
        Inventory.addPart(Part3);
        Inventory.addProduct(product1);
    }
}