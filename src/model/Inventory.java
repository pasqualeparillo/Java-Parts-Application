package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
/**
 * Inventory model
 */
public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int PartID = 0;
    private static int ProductID = 0;
    /**
     * ADD PART TO OUR PARTS LIST
     */
    public static void addPart(Part partIdx) {
        if(partIdx != null) {
            allParts.add(partIdx);
        }
    }
    /**
     * ADD PRODUCT TO OUR PRODUCTS LIST
     */
    public static void addProduct(Product productIdx) {
        if(productIdx != null) {
            allProducts.add(productIdx);
        }
    }
    /**
     * REMOVE THE PASSED PRODUCT
     */
    public static boolean removeProduct(Product partSelected) {
        if(allProducts.contains(partSelected)) {
            allProducts.remove(partSelected);
            return true;
        }
        return false;
    }
    /**
     * REMOVE THE PASSED PART
     */
    public static boolean removePart(Part partSelected) {
        if(allParts.contains(partSelected)) {
            allParts.remove(partSelected);
            return true;
        }
        return false;
    }
    /**
     * LOOKUP PART BY ID & RETURN IT
     */
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
    /**
     * LOOKUP PART BY NAME & RETURN IT
     */
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
    /**
     * LOOKUP PRODUCT BY ID & RETURN IT
     */
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
    /**
     * LOOKUP PRODUCT BY NAME & RETURN IT
     */
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
    /**
     * GENERATE PART & PRODUCT ID'S
     */
    public static int generatePartID() {
        return ++PartID;
    }
    public static int generateProductID() {
        return ++ProductID;
    }

    /**
     * UPDATE OUR PRODUCT @PARAMS oldProduct & selectedProduct -> what we would like to update it with
     */
    public static void updateProduct(Product oldProduct, Product selectedProduct) {
        if(allProducts.contains(oldProduct)) {
            int productIdx = allProducts.indexOf(oldProduct);
            allProducts.set(productIdx, selectedProduct);
        }
    }
    /**
     * UPDATE OUR PART @PARAMS oldPart & selectedPart -> what we would like to update it with
     */
    public static void updatePart(Part oldPart, Part selectedPart) {
        if(allParts.contains(oldPart)) {
            int partIdx = allParts.indexOf(oldPart);
            allParts.set(partIdx, selectedPart);
        }
    }
    /**
     * RETURN ALL PARTS & PRODUCTS
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
