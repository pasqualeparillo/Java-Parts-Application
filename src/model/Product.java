package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Model
 */
public class Product {
    private int id;
    private String name;
    private double price = 0.0;
    private int stock = 0;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the ID
     */
    public void setID(int id) {
        this.id = id;
    }
    /**
     * Sets the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Sets the name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Sets the min value
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * Sets the max value
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Sets the associated parts.
     */
    public void setAssociatedParts(Part part){associatedParts.add(part);}
    /**
     * End Create Setters
     */
    /**
     * Start Create Getters
     */
    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public ObservableList<Part> getAssociatedParts(){return associatedParts;}

    public void addAssParts(Part part){
        associatedParts.add(part);
    }
    /**
     * End Create Getters
     */
    /**
     * Parts Methods
     */
    public boolean deleteAssociatedPart(int part) {
        if(associatedParts.contains(part)) {
            associatedParts.remove(part);
            return true;
        }
        return false;
    }

    public int getAssociatedPartSize() {
        return associatedParts.size();
    }
}
