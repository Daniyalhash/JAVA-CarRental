package DB;

public class RentSlipData {
    private String name;
    private int phone;
    private int idcar;
    private String brand;
    private String model;
    private int days;
    private int payment;

    public RentSlipData(String name, int phone, int idcar, String brand, String model, int days, int payment) {
        this.name = name;
        this.phone = phone;
        this.idcar = idcar;
        this.brand = brand;
        this.model = model;
        this.days = days;
        this.payment = payment;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public int getIdcar() {
        return idcar;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getDays() {
        return days;
    }

    public int getPayment() {
        return payment;
    }
}
