package fa.training.entities;

import java.time.LocalDate;

public class Order {
    private String number;
    private LocalDate date;

    public Order() {}

    public Order(String number, LocalDate date) {
        this.number = number;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
                "number='" + number + '\'' +
                ", date=" + date +
                '}';
    }
}
