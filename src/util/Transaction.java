package util;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private double amount;
    private TransactionType type;
    private String description;

    public Transaction(double amount, TransactionType type, String description) {
        this.date = LocalDate.now();
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    // Getters
    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}