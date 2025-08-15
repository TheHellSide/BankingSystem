package util;

import util.accounts.BankAccount;

import java.util.ArrayList;

public class Customer {
    private static int counter = 0;
    private int id;
    private String firstName;
    private String lastName;
    private ArrayList<BankAccount> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = counter++;
    }

    public void printCustomerDetails() {
        System.out.println("\n┌──────────────────────── \u001B[1mCUSTOMER INFORMATION\u001B[0m ───────────────────────┐\n");
        System.out.println("\u001B[1m  Customer: " + lastName + " " + firstName + "\u001B[0m");
        System.out.println();

        if (accounts == null || accounts.isEmpty()) {
            System.out.println("  No accounts associated.\n");
            System.out.println("└─────────────────────────────────────────────────────────────────────┘\n");
            return;
        }

        for (BankAccount account : accounts) {
            System.out.println("  Account Type: " + account.getType());
            System.out.println("  IBAN: " + account.getIban());
            System.out.println();
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────┘\n");
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    // Setter for adding an account
    public void addAccount(BankAccount account) {
        this.accounts.add(account);
    }
}