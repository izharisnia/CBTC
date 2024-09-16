import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Scanner;

class Bank {
    private List<Account> accounts = new ArrayList<>();
    private static final String FILE_NAME = "accounts.dat";

    // Load accounts when Bank is instantiated
    public Bank() {
        loadAccounts();
    }

    public void createAccount(String accountNumber, String accountHolder, double initialDeposit) {
        Account newAccount = new Account(accountNumber, accountHolder, initialDeposit);
        accounts.add(newAccount);
        System.out.println("\n*** Account created successfully! ***\n");
        saveAccounts();
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void deposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            saveAccounts();
        } else {
            System.out.println("\n>>> Account not found.\n");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            saveAccounts();
        } else {
            System.out.println("\n>>> Account not found.\n");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.println("\n*** Transfer successful! ***\n");
                saveAccounts();
            } else {
                System.out.println("\n>>> Insufficient balance for transfer.\n");
            }
        } else {
            System.out.println("\n>>> One or both accounts not found.\n");
        }
    }

    // Serialize accounts to a file
    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
            System.out.println("*** Accounts saved successfully. ***");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Deserialize accounts from a file
    private void loadAccounts() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                accounts = (List<Account>) ois.readObject();
                System.out.println("\n*** Accounts loaded successfully. ***\n");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading accounts: " + e.getMessage());
            }
        }
    }

    public void displayAccount(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.displayAccountDetails();
        } else {
            System.out.println("\n>>> Account not found.\n");
        }
    }
}

class Account implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization compatibility

    private String accountNumber;
    private String accountHolder;
    private double balance;

    public Account(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("*** Deposited: $" + amount + " ***\n");
        } else {
            System.out.println(">>> Invalid deposit amount.\n");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("*** Withdrew: $" + amount + " ***\n");
        } else {
            System.out.println(">>> Insufficient funds or invalid amount.\n");
        }
    }

    public void displayAccountDetails() {
        System.out.println("========================================");
        System.out.println("| Account Number: " + accountNumber);
        System.out.println("| Account Holder: " + accountHolder);
        System.out.println("| Balance: $" + balance);
        System.out.println("========================================\n");
    }
}

public class BankSystem {
    public static void main(String[] args) {
        Bank bank = new Bank(); // Automatically loads data from file using loadAccounts()
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===============================");
            System.out.println("||    Banking System Menu     ||");
            System.out.println("===============================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Display Account");
            System.out.println("6. Exit");
            System.out.println("===============================");
            System.out.print("Choose an option (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n>>> Enter Account Details <<<");
                    System.out.print("Enter Account Number: ");
                    String accountNumber = scanner.next();
                    System.out.print("Enter Account Holder Name: ");
                    String accountHolder = scanner.next();
                    System.out.print("Enter Initial Deposit: ");
                    double initialDeposit = scanner.nextDouble();
                    bank.createAccount(accountNumber, accountHolder, initialDeposit);
                    break;
                case 2:
                    System.out.print("\nEnter Account Number: ");
                    accountNumber = scanner.next();
                    System.out.print("Enter Amount to Deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(accountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("\nEnter Account Number: ");
                    accountNumber = scanner.next();
                    System.out.print("Enter Amount to Withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(accountNumber, withdrawAmount);
                    break;
                case 4:
                    System.out.print("\nEnter Sender Account Number: ");
                    String fromAccount = scanner.next();
                    System.out.print("Enter Receiver Account Number: ");
                    String toAccount = scanner.next();
                    System.out.print("Enter Amount to Transfer: ");
                    double transferAmount = scanner.nextDouble();
                    bank.transfer(fromAccount, toAccount, transferAmount);
                    break;
                case 5:
                    System.out.print("\nEnter Account Number: ");
                    accountNumber = scanner.next();
                    bank.displayAccount(accountNumber);
                    break;
                case 6:
                    System.out.println("\n*** Exiting system. Goodbye! ***");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\n>>> Invalid option. Try again.\n");
            }
        }
    }
}