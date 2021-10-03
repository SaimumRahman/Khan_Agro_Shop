package khan.solution.Model;

public class PayBill {

    private String Address,Customer_ID,Email,Name,Order_ID,Phone,Total_Bill;

    public PayBill() {
    }

    public PayBill(String address, String customer_ID, String email, String name, String order_ID, String phone, String total_Bill) {
        Address = address;
        Customer_ID = customer_ID;
        Email = email;
        Name = name;
        Order_ID = order_ID;
        Phone = phone;
        Total_Bill = total_Bill;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTotal_Bill() {
        return Total_Bill;
    }

    public void setTotal_Bill(String total_Bill) {
        Total_Bill = total_Bill;
    }
}
