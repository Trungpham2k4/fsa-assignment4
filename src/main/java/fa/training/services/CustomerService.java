package fa.training.services;

import fa.training.entities.Customer;
import fa.training.entities.Order;
import fa.training.utils.Constants;
import fa.training.utils.Validator;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomerService {
    private final List<Customer> customers;
    private final Scanner scanner;
    public CustomerService() {
        customers = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    public List<String> createCustomer(){
        String name = getStringInput("Enter name: ");
        String phoneNumber = inputNoneExistPhoneNumber("Enter phone number: ");
        String address = getStringInput("Enter address: ");
        System.out.println("Enter order infor: ");
        List<Order> orders = new ArrayList<>();
        String answer = "y";
        do{
            String orderNumber = inputValidOrderNumber("+ number: ");
            if(orders.stream().anyMatch(order -> order.getNumber().equals(orderNumber))){
                System.out.println("Order already exists!");
                continue;
            }
            LocalDate orderDate = inputValidDate("+ date: ");
            orders.add(new Order(orderNumber, orderDate));
            answer = getStringInput("Do you want to order another order? [Y/N]: ");
        }while(!answer.equalsIgnoreCase("n"));
        Customer customer = new Customer(name, phoneNumber, address, orders);
        customers.add(customer);
        return customers.stream()
                .map(Customer::toString)
                .toList();
    }
    public String save(List<String> customers) throws IOException {
        File file = new File(Constants.OUTPUT_FILE);
        if(!file.exists()){
            File parentFile = file.getParentFile();
            if(parentFile == null || !parentFile.exists()){
                parentFile.mkdirs();
            }
            if(file.createNewFile()){
                System.out.println("File not found. Created new file: " + Constants.OUTPUT_FILE);
            }
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            for(String customer : customers){
                oos.writeObject(customer);
            }
        }catch(IOException e){
            return "Error while saving data: " + e.getMessage();
        }
        return "Save data successful";
    }

    public List<String> findAll(){
        return customers.stream().map(Customer::toString).collect(Collectors.toList());
    }
    public void display(List<String> customers){
        System.out.printf("%-25s%-25s%-25s%-25s", "Customer Name", "Address", "Phone Number", "OrderList");
        System.out.println();
        for(String customer : customers){
            String attributes = customer.substring(9, customer.length() - 1);
            int orderIdx = attributes.indexOf("orders");
            String order = attributes.substring(orderIdx + 13, attributes.length() - 1);
            String otherAttr = attributes.substring(0, orderIdx - 2);
            String[] attrs = otherAttr.split(",");
            System.out.printf("%-25s%-25s%-25s%-25s",
                    attrs[0].split("=")[1].replaceAll("'", ""),
                    attrs[1].split("=")[1].replaceAll("'", ""),
                    attrs[2].split("=")[1].replaceAll("'", ""),
                    order);
            System.out.println();
        }
    }
    public List<String> search(String phone){
        Customer customer = findByPhone(phone);
        if(customer == null){
            System.out.println("Customer not found");
            return new ArrayList<>();
        }
        return customer.getOrders().stream().map(Order::toString).collect(Collectors.toList());
    }
    public boolean remove(String phone){
        return customers.removeIf(customer -> customer.getPhone().equals(phone));
    }

    public Customer findByPhone(String phone){
        for(Customer customer : customers){
            if(customer.getPhone().equals(phone)){
                return customer;
            }
        }
        return null;
    }

    public String inputValidPhoneNumber(String prompt){
        while(true){
            String phoneNumber = getStringInput(prompt);
            if(Validator.isValidPhoneNumber(phoneNumber)){
                return phoneNumber.replaceAll("\\s+", "");
            }
            System.out.println("Invalid phone number");
        }
    }

    private String inputNoneExistPhoneNumber(String prompt){
        while (true){
            String phoneNumber = inputValidPhoneNumber(prompt);
            if(findByPhone(phoneNumber) == null){
                return phoneNumber;
            }
            System.out.println("Phone number already exists!");
        }
    }

    private String inputValidOrderNumber(String prompt){
        while (true){
            String orderNumber = getStringInput(prompt);
            if(Validator.isValidOrderNumber(orderNumber)){
                return orderNumber.replaceAll("\\s+", "");
            }
            System.out.println("Invalid order number");
        }
    }

    private LocalDate inputValidDate(String prompt){
        while(true){
            String date = getStringInput(prompt);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            if(Validator.isValidDate(date, dateTimeFormatter)){
                return LocalDate.parse(date, dateTimeFormatter);
            }
            System.out.println("Invalid date");
        }
    }

    private String getStringInput(String prompt) {
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine();
            if(Validator.isBlank(input)) {
                System.out.println("Don't let the input blank");
            }else{
                return input.trim();
            }
        }
    }
}
