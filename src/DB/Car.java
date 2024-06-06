package DB;

public class Car {
    private int id;
    private String make;
    private String model;
    private int basePricePerDay;
    private boolean isAvailable;

    public Car(int id, String make, String model, int basePricePerDay, boolean isAvailable) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = isAvailable;
    }


    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getbasePricePerDay() {
        return basePricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


}
