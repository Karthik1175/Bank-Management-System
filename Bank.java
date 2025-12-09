import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private int id;
    private String name;
    private double balance;

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Balance: " + balance;
    }
}

public class Bank {
    private List<Account> accounts;
    private Scanner scanner;
    private int nextAccountId;

    public Bank() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextAccountId = 1001;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.start();
    }

    private void start() {
        System.out.println("===== BANK MANAGEMENT SYSTEM =====");

        while (true) {
            showMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    showAllAccounts();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println("-----------------------------------");
        }
    }

    private void showMenu() {
        System.out.println("\n1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Check Balance");
        System.out.println("5. Show All Accounts");
        System.out.println("6. Exit");
    }

    private void createAccount() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        Account acc = new Account(nextAccountId, name);
        accounts.add(acc);
        System.out.println("Account created successfully. Account ID: " + nextAccountId);
        nextAccountId++;
    }

    private void depositMoney() {
        Account acc = findAccountById();
        if (acc == null) return;

        double amount = readDouble("Enter amount to deposit: ");
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        acc.deposit(amount);
        System.out.println("Deposited " + amount + ". New balance: " + acc.getBalance());
    }

    private void withdrawMoney() {
        Account acc = findAccountById();
        if (acc == null) return;

        double amount = readDouble("Enter amount to withdraw: ");
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        boolean ok = acc.withdraw(amount);
        if (ok) {
            System.out.println("Withdrawn " + amount + ". New balance: " + acc.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void checkBalance() {
        Account acc = findAccountById();
        if (acc == null) return;

        System.out.println("Account Holder: " + acc.getName());
        System.out.println("Balance: " + acc.getBalance());
    }

    private void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("=== All Accounts ===");
        for (Account acc : accounts) {
            System.out.println(acc);
        }
    }

    private Account findAccountById() {
        int id = readInt("Enter account ID: ");
        for (Account acc : accounts) {
            if (acc.getId() == id) {
                return acc;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    // helper input methods
    private int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String line = scanner.nextLine();
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
