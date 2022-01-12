package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int PartID = 0;
    private static int ProductID = 0;

    public static void addPart(Part partIdx) {
        if(partIdx != null) {
            allParts.add(partIdx);
        }
    }
    public static void addProduct(Product productIdx) {
        if(productIdx != null) {
            allProducts.add(productIdx);
        }
    }
    public static boolean removeProduct(Product partSelected) {
        if(allProducts.contains(partSelected)) {
            allProducts.remove(partSelected);
            return true;
        }
        return false;
    }
    public static boolean removePart(Part partSelected) {
        if(allParts.contains(partSelected)) {
            allParts.remove(partSelected);
            return true;
        }
        return false;
    }
    public static ObservableList<Part>  lookupPartID(int PartID) {
        ObservableList partsResult = FXCollections.observableArrayList();
        if(!allParts.isEmpty()) {
          for(Part p: getAllParts()) {
              if(p.getId() == PartID) {
                  partsResult.add(p);
              }
          }
          return partsResult;
        }
        return null;
    }
    public static ObservableList<Part> lookupPartName(String partValue) {
        ObservableList partsResult = FXCollections.observableArrayList();
        if(!allParts.isEmpty()) {
            for(Part p: getAllParts()) {
                if(p.getName().toLowerCase(Locale.ROOT).contains(partValue.toLowerCase(Locale.ROOT))) {
                    partsResult.add(p);
                };
            }
            return partsResult;
        }
        return null;
    }
    public static ObservableList<Product>  lookupProductID(int productIdx) {
        ObservableList productResult = FXCollections.observableArrayList();
        if(!allProducts.isEmpty()) {
            for(Product p: getAllProducts()) {
                if(p.getId() == productIdx) {
                    productResult.add(p);
                }
            }
            return productResult;
        }
        return null;
    }
    public static ObservableList<Product> lookupProductName(String productValue) {
        ObservableList productResult = FXCollections.observableArrayList();
        if(!allProducts.isEmpty()) {
            for(Product p: getAllProducts()) {
                if(p.getName().toLowerCase(Locale.ROOT).contains(productValue.toLowerCase(Locale.ROOT))) {
                    productResult.add(p);
                };
            }
            return productResult;
        }
        return null;
    }
    public static int generatePartID() {
        return ++PartID;
    }
    public static int generateProductID() {
        return ++ProductID;
    }
    public static void updateProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            int productIdx = allProducts.indexOf(selectedProduct);
            allProducts.set(productIdx, selectedProduct);
        }
    }
    public static void updatePart(Part selectedPart) {
        if(allProducts.contains(selectedPart)) {
            int partIdx = allParts.indexOf(selectedPart);
            allParts.set(partIdx, selectedPart);
        }
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
