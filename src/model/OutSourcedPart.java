package model;

public class OutSourcedPart extends Part {
    private String companyName;

    public OutSourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
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
