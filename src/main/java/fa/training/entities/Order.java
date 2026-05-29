package fa.training.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "{" + number +
                ", " + date.format(formatter) +
                '}';
    }
}
