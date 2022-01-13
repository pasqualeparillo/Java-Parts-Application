package model;

/**
 * In house part model
 */
public class InHousePart extends Part {
    private int machineID;
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);

        this.machineID = machineID;

    }
    //CREATE GETTERS
    public int getMachineID() {
        return machineID;
    }
    //CREATE SETTERS
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

}
