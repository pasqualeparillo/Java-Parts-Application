package model;
import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private double price = 0.0;
    private int stock = 0;
    private int min;
    private int max;
    private ArrayList<Part> associatedParts = new ArrayList<Part>();

    public Product(int id, String name, double price, int stock, int min, int max) {
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    /**
     * Start Create Setters
     */
    public void setID(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
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

    public ArrayList<Part> getAssociatedParts() {
        return associatedParts;
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
