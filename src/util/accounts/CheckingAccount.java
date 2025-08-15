package util.accounts;

import util.Customer;
import util.AccountType;

public class CheckingAccount extends BankAccount {
    private double maxOverdraft = 1500d;

    public CheckingAccount(String iban, double balance, Customer owner, AccountType type) {
        super(iban, balance, owner, type);
    }

    @Override
    public void printDetails() {
        System.out.println("\n┌─────────────── \u001B[1mBANK ACCOUNTS\u001B[0m ──────────────┐\n" +
                "\n  Account Holder: " + getOwner().getLastName() + " " + getOwner().getFirstName() +
                "\n  IBAN: " + getIban() +
                "\n  Balance: " + getBalance() +
                "\n  Maximum Overdraft: " + maxOverdraft +
                "\n\n└────────────────────────────────────────────┘\n");
    }

    @Override
    public double calculateInterest() {
        return 0;
    }

    public double getMaxOverdraft() {
        return maxOverdraft;
    }
}