package q1.model;

public class Item {
    private int id;
    private String name;
    private int doseExist;
    private double price;

    public Item() {
    }

    public Item(int id, String name, int doseExist, double price) {
        this.id = id;
        this.name = name;
        this.doseExist = doseExist;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDoseExist() {
        return doseExist;
    }

    public void setDoseExist(int doseExist) {
        this.doseExist = doseExist;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
