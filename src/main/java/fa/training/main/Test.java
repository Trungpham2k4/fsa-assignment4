package fa.training.main;


import fa.training.services.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Test {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerService customerService = new CustomerService(scanner);

    public static void showMenu(){
        String menu = """
                Choose function:
                1. Add a new customer
                2. Show all customers
                3. Search customer
                4. Remove customer
                5. Exit
                """;
        System.out.print(menu);
    }

    public static void main(String[] args) throws IOException {
        if(customerService.load()){
            System.out.println("Loaded customer successfully");
        }else{
            System.out.println("No customer loaded");
        }
        start();
    }

    public static void start() throws IOException {
        int choice;
        do{
            showMenu();
            choice = inputValidOption(1,5);
            switch(choice){
                case 1 -> createCustomer();
                case 2 -> showCustomers();
                case 3 -> searchCustomer();
                case 4 -> removeCustomer();
            }
        }while (choice != 5);
        saveData();
    }

    private static void createCustomer(){
        System.out.println("----Enter Customer information----");
        customerService.createCustomer();
    }
    private static void showCustomers(){
        System.out.println("----LIST OF Customer----");
        List<String> customers = customerService.findAll();
        customerService.display(customers);
    }
    private static void searchCustomer(){
        String phoneNumber = customerService.inputValidPhoneNumber("Enter phone number: ");
        System.out.println("----Search customer----");
        List<String> orders = customerService.search(phoneNumber);
        for(String order : orders){
            System.out.println(order);
        }
    }
    private static void removeCustomer() throws IOException {
        String phoneNumber = customerService.inputValidPhoneNumber("Enter phone number: ");
        if(customerService.remove(phoneNumber)){
            System.out.println("Customer removed");
            saveData();
        }else{
            System.out.println("Can't remove customer with phone number: " + phoneNumber);
        }
    }
    private static void saveData() throws IOException {
        List<String> customers = customerService.findAll();
        System.out.println(customerService.save(customers));
    }

    private static int inputValidOption(int min, int max){
        while(true){
            int option = getIntInput("Please input an option from " + min + " to " + max + ": ");
            if(option < min || option > max){
                System.out.println("Invalid option. Please provide a number between " + min + " and " + max);
            }else{
                return option;
            }
        }
    }

    private static int getIntInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter an integer");
            }
        }
    }
}