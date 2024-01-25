
package atm;

import java.util.Scanner;

class Account {
    private String accountnumber;
    private String pin;
    private double balance;
    private TransactionHistory transactionHistory;

    public Account(String accountNumber, String pin, double balance) {
        this.accountnumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new TransactionHistory();
    }

    public String getAccountNumber() {
        return accountnumber;
    }

    public boolean verifyPin(String enterePin) {
        return pin.equals(enterePin);
    }

    public double getBalance() {
        return balance;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.addTransaction(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.addTransaction(new Transaction("Withdraw", amount));
        } else {
            System.out.println("Your have "+balance+" money in your account \n");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.addTransaction(new Transaction("Transfer to " + recipient.getAccountNumber(), amount));
        } else {
            System.out.println("Your have "+balance+" money in your account \n");
        }
    }
}

class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + ": " + amount;
    }
}

class TransactionHistory {
    private static final int MAX_TRANSACTIONS = 10;
    private Transaction[] transactions;
    private int size;

    public TransactionHistory() {
        transactions = new Transaction[MAX_TRANSACTIONS];
        size = 0;
    }

    public void addTransaction(Transaction transaction) {
        if (size < MAX_TRANSACTIONS) {
            transactions[size++] = transaction;
        } else {
            // Shift transactions to make room for the new one
            System.arraycopy(transactions, 1, transactions, 0, MAX_TRANSACTIONS - 1);
            transactions[MAX_TRANSACTIONS - 1] = transaction;
        }
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + transactions[i]);
        }
        System.out.println();
    }
}

public class ATMInterface {
    private static final String ACCOUNT_NUMBER = "121212";
    private static final String PIN = "123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------*Welcome to the ATM System*----------\n");

        
        Account account = new Account(ACCOUNT_NUMBER, PIN, 1000.0);

     
        if (login(scanner, account)) {
            int choice;
            do {
                Menu();
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        account.getTransactionHistory().displayTransactionHistory();
                        break;
                    case 2:
                        System.out.print("\nEnter withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("\nEnter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("\nEnter recipient's account number: ");
                        String recipientAccountNumber = scanner.next();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                     
                        Account recipient = new Account(recipientAccountNumber, "", 0.0);
                        account.transfer(recipient, transferAmount);
                        break;
                    case 5:
                        System.out.println("\nThank you for using the ATM.");
                        break;
                    default:
                }
            } while (choice != 5);
        }
    }

    static void Menu() {
        
        System.out.println("\n1. View Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

     static boolean login(Scanner scanner, Account account) {
        System.out.print("Enter account number: ");
        String enteredAccountNumber = scanner.next();
        System.out.print("Enter PIN: ");
        String enterePin = scanner.next();
        System.out.println("\n*************************************************");

        if (enteredAccountNumber.equals(account.getAccountNumber()) && account.verifyPin(enterePin)) {
            System.out.println("Login successful!\n");
            return true;
        } else {
            System.out.println("Invalid account number or PIN\n");
            return false;
        }
        
    }
}
