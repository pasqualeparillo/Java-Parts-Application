package model;

public class OutSourcedPart extends Part {
    private String companyName;

    public OutSourcedPart(int id, String name, String companyName, double price, int stock, int min, int max) {
        setId(id);
        setName(name);
        setCompanyName(companyName);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    /**
     * Create Getter
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Create Setter
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
