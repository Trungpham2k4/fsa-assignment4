package fa.training.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    String phoneNumber;
    String address;
    List<Order> orders;
    public Customer() {}
    public Customer(String name, String phone, String address, List<Order> orders) {
        this.name = name;
        this.phoneNumber = phone;
        this.address = address;
        this.orders = new ArrayList<>();
        this.orders.addAll(orders);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phone) {
        this.phoneNumber = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order[");
        for (Order o : orders) {
            sb.append(o.toString());
        }
        sb.append("]");
        return "Customer[" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", orders=" + sb +
                ']';
    }
}
