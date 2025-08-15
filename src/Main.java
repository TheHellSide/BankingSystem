import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import util.*;
import util.accounts.*;

public class Main {
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static String MENU = """
┌───────── [1mBANKING SYSTEM MANAGEMENT[0m ────────┐

  1. Create new customer
  2. Open new account
  3. View customer accounts
  4. Deposit money
  5. Withdraw money
  6. Show account details
  7. Calculate interest
  8. View transactions
  9. View customers
  0. Exit program
    
└────────────────────────────────────────────┘
""";

    public static void main(String[] args) {
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println(MENU);

                int userOption = getValidInput();
                double amount;
                BankAccount account;

                switch (userOption) {
                    case 1:
                        createCustomer();
                        break;

                    case 2:
                        openNewAccount();
                        break;

                    case 3:
                        viewCustomerAccounts();
                        break;

                    case 4:
                        do {
                            try {
                                System.out.print("Amount to deposit: ");
                                amount = Double.parseDouble(input.nextLine());
                                if (amount <= 0) {
                                    System.out.println("The amount must be greater than zero!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid amount value...");
                                amount = -1;
                            }
                        } while (amount <= 0);

                        account = getIbanInput("deposit");
                        if (account != null) {
                            processTransaction(account, amount, TransactionType.DEPOSIT);
                        }
                        break;

                    case 5:
                        do {
                            try {
                                System.out.print("Amount to withdraw: ");
                                amount = Double.parseDouble(input.nextLine());
                                if (amount <= 0) {
                                    System.out.println("The amount must be greater than zero!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid amount value...");
                                amount = -1;
                            }
                        } while (amount <= 0);

                        account = getIbanInput("withdraw");
                        if (account != null) {
                            processTransaction(account, amount, TransactionType.WITHDRAWAL);
                        }
                        break;

                    case 6:
                        account = getIbanInput("view");
                        if (account != null) {
                            viewAccountDetails(account);
                        }
                        break;

                    case 7:
                        account = getIbanInput("interest calculation");
                        if (account != null) {
                            calculateAccountInterest(account);
                        }
                        break;

                    case 8:
                        account = getIbanInput("transaction viewing");
                        if (account != null) {
                            viewTransactions(account);
                        }
                        break;

                    case 9:
                        for (Customer customer : customers) {
                            customer.printCustomerDetails();
                        }
                        break;

                    case 0:
                        System.out.println("See you soon...");
                        isRunning = false;
                        break;

                    default:
                        System.out.println("Invalid option... Please try again");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            if (!isRunning) {
                input.close();
            } else {
                System.out.println("Press ENTER to continue...");
                input.nextLine();
            }
        }
    }

    public static int getValidInput() {
        while (true) {
            try {
                System.out.print("Choose an option: ");
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid value... Please try again");
            }
        }
    }

    public static int getIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid value... Please try again");
            }
        }
    }


    public static void createCustomer() {
        String firstName, lastName;
        System.out.print("First Name: ");
        firstName = input.nextLine();
        System.out.print("Last Name: ");
        lastName = input.nextLine();

        Customer customer = new Customer(firstName, lastName);
        customers.add(customer);
        System.out.println("Customer created with ID: " + customer.getId() + "\n");
    }

    public static void openNewAccount() {
        if (customers.isEmpty()) {
            System.out.println("No customers exist. Please create one first.");
            return;
        }
        int customerId = getIntegerInput("Enter customer ID: ");

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("\nCustomer not found!");
            return;
        }

        System.out.print("""

┌───────── [1mBANK ACCOUNTS[0m ────────┐

  1. Checking Account
  2. Savings Account

└────────────────────────────────┘

Select a Bank Account type:\s""");
        String accountOption = input.nextLine();

        String newIban = generateUniqueIban();
        double newBalance = 0.0;
        AccountType type;

        switch (accountOption) {
            case "1":
                type = AccountType.CHECKING;
                customer.addAccount(new CheckingAccount(newIban, newBalance, customer, type));
                System.out.println("Checking Account created with IBAN: " + newIban);
                break;
            case "2":
                type = AccountType.SAVINGS;
                customer.addAccount(new SavingsAccount(newIban, newBalance, customer, type));
                System.out.println("Savings Account created with IBAN: " + newIban);
                break;
            default:
                System.out.println("Invalid account type...");
        }
        System.out.println();
    }

    public static String generateUniqueIban() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String iban;
        boolean isUnique;
        do {
            StringBuilder sb = new StringBuilder("IT");
            for (int i = 0; i < 25; i++) {
                int idx = (int) (Math.random() * characters.length());
                sb.append(characters.charAt(idx));
            }
            iban = sb.toString();
            isUnique = true;
            for (Customer customer : customers) {
                for (BankAccount account : customer.getAccounts()) {
                    if (account.getIban().equals(iban)) {
                        isUnique = false;
                        break;
                    }
                }
                if (!isUnique) {
                    break;
                }
            }
        } while (!isUnique);
        return iban;
    }

    public static void viewCustomerAccounts() {
        if (customers.isEmpty()) {
            System.out.println("No customers exist. Please create one first.");
            return;
        }

        int customerId = getIntegerInput("Enter customer ID: ");
        System.out.println();

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found!\n");
            return;
        }

        if (customer.getAccounts() == null || customer.getAccounts().isEmpty()) {
            System.out.println("No accounts associated.");
            System.out.println("\n");
            return;
        }

        System.out.println("┌──────────────────────── [1mCUSTOMER ACCOUNTS[0m ───────────────────────┐\n");
        for (BankAccount account : customer.getAccounts()) {
            System.out.println("  Account Type: " + account.getType());
            System.out.println("  IBAN: " + account.getIban());
            System.out.println("  Balance: " + account.getBalance());
            System.out.println();
        }
        System.out.println("└──────────────────────────────────────────────────────────────┘\n");
    }

    public static BankAccount getIbanInput(String action) {
        String prompt;
        switch (action) {
            case "deposit":
                prompt = "Enter the IBAN of the account to deposit to: ";
                break;
            case "withdraw":
                prompt = "Enter the IBAN of the account to withdraw from: ";
                break;
            case "view":
                prompt = "Enter the IBAN of the account to view details for: ";
                break;
            case "interest calculation":
                prompt = "Enter the IBAN of the account to calculate interest for: ";
                break;
            case "transaction viewing":
                prompt = "Enter the IBAN of the account to view transactions for: ";
                break;
            default:
                prompt = "Enter IBAN: ";
        }
        System.out.print(prompt);
        String iban = input.nextLine();
        System.out.println();

        for (Customer customer : customers) {
            for (BankAccount account : customer.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }

        System.out.println("No account found with IBAN: " + iban + "...");
        return null;
    }

    public static void viewAccountDetails(BankAccount account) {
        account.printDetails();
    }

    public static void calculateAccountInterest(BankAccount account) {
        double interest = account.calculateInterest();
        System.out.println("Interest: " + interest + "\n");
    }

    public static void processTransaction(BankAccount account, double amount, TransactionType type) {
        if (type == TransactionType.DEPOSIT) {
            account.deposit(amount);
        } else {
            if (account instanceof SavingsAccount) {
                if (amount > account.getBalance()) {
                    System.out.println("Insufficient balance, withdrawal not possible...\n");
                    return;
                } else {
                    account.withdraw(amount);
                }
            } else {
                if (amount > account.getBalance() + ((CheckingAccount) account).getMaxOverdraft()) {
                    System.out.println("Insufficient balance, withdrawal not possible...\n");
                    return;
                } else {
                    account.withdraw(amount);
                }
            }
        }

        System.out.print("Description: ");
        String description = input.nextLine();
        account.getTransactions().add(new Transaction(amount, type, description));
        System.out.println();
    }

    public static void viewTransactions(BankAccount account) {
        System.out.println("┌──────────────────────── [1mTRANSACTIONS[0m ───────────────────────┐\n");
        for (Transaction transaction : account.getTransactions()) {
            System.out.println("  " + transaction.getType());
            System.out.println("  Description: " + transaction.getDescription());
            System.out.println("  Amount: " + transaction.getAmount());
            System.out.println("  Date: " + transaction.getDate());
            System.out.println();
        }
        System.out.println("└────────────────────────────────────────────────────────────┘\n");
    }

    private static Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }
}