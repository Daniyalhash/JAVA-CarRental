package DB; // Ensure that the package declaration matches the package name

import java.time.LocalDateTime;

public class LiveRenting {
    private int idliveRenting;
    private int idcar;
    private int idcustomer;
    private LocalDateTime Time;
    private LocalDateTime returnTime;

    public LiveRenting(int idliveRenting,int idcar, int idcustomer, LocalDateTime Time, LocalDateTime returnTime) {
        this.idliveRenting = idliveRenting;
        this.idcar = idcar;
        this.idcustomer = idcustomer;
        this.Time = Time;
        this.returnTime = returnTime;
    }
    public int getId() {
        return idliveRenting;
    }
    // Getters and setters
    public int getIdcar() {
        return idcar;
    }

    public void setIdcar(int idcar) {
        this.idcar = idcar;
    }

    public int getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(int idcustomer) {
        this.idcustomer = idcustomer;
    }

    public LocalDateTime getRentTime() {
        return Time;
    }

    public void setRentTime(LocalDateTime rentTime) {
        this.Time = rentTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    // Fetch customer name
    public String getCustomerName() {
        Customer customer = MyJDBC.getCustomerById(idcustomer);
        return customer != null ? customer.getName() : "Unknown";
    }

    // Fetch customer phone number
    public String getCustomerPhoneNumber() {
        Customer customer = MyJDBC.getCustomerById(idcustomer);
        return customer != null ? customer.getPhoneNumber() : "Unknown";
    }
}
