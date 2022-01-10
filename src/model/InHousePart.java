package model;

public class InHousePart extends Part {
    private int id;

    public InHousePart(int id, String name, double price, int stock, int min, int max) {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    /**
     * Create Getter
     */
    public int getId() {
        return id;
    }
    /**
     * Create Setter
     */
    public void setId(int id) {
        this.id = id;
    }

}
