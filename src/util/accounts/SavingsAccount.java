package util.accounts;

import util.Customer;
import util.AccountType;

public class SavingsAccount extends BankAccount {
    private double interestRate = 0.001d;

    public SavingsAccount(String iban, double balance, Customer owner, AccountType type) {
        super(iban, balance, owner, type);
    }

    @Override
    public void printDetails() {
        System.out.println("\n┌─────────────── \u001B[1mBANK ACCOUNTS\u001B[0m ──────────────┐\n" +
                "\n  Account Holder: " + getOwner().getFirstName() + " " + getOwner().getLastName() +
                "\n  IBAN: " + getIban() +
                "\n  Balance: " + getBalance() +
                "\n  Interest Rate: " + interestRate +
                "\n\n└────────────────────────────────────────────┘\n");
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }
}