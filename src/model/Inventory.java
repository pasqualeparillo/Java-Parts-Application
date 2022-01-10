package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    public void addProduct(Product productIdx) {
        if(productIdx != null) {
            this.allProducts.add(productIdx);
        }
    }

    public boolean removeProduct(Product partSelected) {
        if(allProducts.contains(partSelected)) {
            allProducts.remove(partSelected);
            return true;
        }
        return false;
    }
    public boolean removePart(Product partSelected) {
        if(allParts.contains(partSelected)) {
            allParts.remove(partSelected);
            return true;
        }
        return false;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
