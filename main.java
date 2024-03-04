import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userID;
    private String userPIN;
    private double accountBalance;

    public User(String userID, String userPIN, double accountBalance) {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double balance) {
        this.accountBalance = balance;
    }
}

class ATM {
    private Map<String, User> users;

    public ATM() {
        users = new HashMap<>();
        // Initialize some sample users
        users.put("123456", new User("123456", "7890", 1000));
        users.put("654321", new User("654321", "0987", 500));
    }

    public boolean authenticateUser(String userID, String userPIN) {
        return users.containsKey(userID) && users.get(userID).getUserPIN().equals(userPIN);
    }

    public double checkBalance(String userID) {
        return users.get(userID).getAccountBalance();
    }

    public void withdraw(String userID, double amount) {
        User user = users.get(userID);
        double balance = user.getAccountBalance();
        if (balance >= amount) {
            user.setAccountBalance(balance - amount);
            System.out.println("Amount withdrawn successfully.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void deposit(String userID, double amount) {
        User user = users.get(userID);
        double balance = user.getAccountBalance();
        user.setAccountBalance(balance + amount);
        System.out.println("Amount deposited successfully.");
    }
}

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("Welcome to the ATM!");
        System.out.print("Enter your user ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String userPIN = scanner.nextLine();

        if (atm.authenticateUser(userID, userPIN)) {
            System.out.println("Authentication successful.");
            while (true) {
                System.out.println("\n1. Check Balance\n2. Withdraw\n3. Deposit\n4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Your balance is: $" + atm.checkBalance(userID));
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdraw(userID, withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(userID, depositAmount);
                        break;
                    case 4:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        } else {
            System.out.println("Invalid user ID or PIN. Authentication failed.");
        }
    }
}
