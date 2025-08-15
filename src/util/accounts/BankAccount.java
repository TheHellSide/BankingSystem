package util.accounts;

import util.Customer;
import util.AccountType;
import util.Transaction;

import java.util.ArrayList;

public abstract class BankAccount {
    private String iban;
    private AccountType type;
    private double balance;
    private Customer owner;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    // Corrected constructor
    public BankAccount(String iban, double balance, Customer owner, AccountType type) {
        this.iban = iban;
        this.balance = balance;
        this.owner = owner;
        this.type = type;
    }

    public void deposit(double amount) {
        this.balance += validateTransaction(amount);
    }

    public void withdraw(double amount) {
        this.balance -= validateTransaction(amount);
    }

    private double validateTransaction(double amount) {
        String message = amount > 0 ? "Operation performed successfully!" : "Invalid amount...";
        System.out.println(message);
        if (message.equals("Operation performed successfully!")) {
            return amount;
        }
        return 0;
    }

    public abstract void printDetails();
    public abstract double calculateInterest();

    // Getters
    public String getIban() {
        return iban;
    }

    public AccountType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}