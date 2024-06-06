package DB;

public class Customer {
    private int idcustomer;
    private String name;
    private String phone; // Add phone number field

    public Customer(int idcustomer, String name, String phone) {
        this.idcustomer = idcustomer;
        this.name = name;
        this.phone = phone;
    }

    public int getIdcustomer() {
        return idcustomer;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phone;
    }

    // Setters, if needed
}
