import java.util.*;

class User {
    private String name;
    private String address;
    private String contact;
    private String password;
    private int accountNumber;
    private double balance;
    private List<String> transactionHistory;

    public User(String name, String address, String contact, String password, int accountNumber, double initialDeposit) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial deposit: " + initialDeposit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount + ", Balance: " + balance);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount + ", Balance: " + balance);
            return true;
        } else {
            return false;
        }
    }

    public boolean transfer(User recipient, double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to Account: " + recipient.getAccountNumber() + ", Balance: " + balance);
            return true;
        } else {
            return false;
        }
    }
}

public class BankingInformationSystem {
    private static Map<Integer, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int accountCounter = 1001;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Banking Information System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Thank you for using the Banking Information System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter contact: ");
        String contact = scanner.nextLine();
        System.out.print("Set your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial deposit: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        int accountNumber = accountCounter++;
        User user = new User(name, address, contact, password, accountNumber, initialDeposit);
        users.put(accountNumber, user);

        System.out.println("Registration successful! Your account number is: " + accountNumber);
    }

    private static void loginUser() {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(accountNumber);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!\n");
            userMenu(user);
        } else {
            System.out.println("Invalid account number or password.");
        }
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Account Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Account Statement");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Account Number: " + user.getAccountNumber());
                    System.out.println("Name: " + user.getName());
                    System.out.println("Address: " + user.getAddress());
                    System.out.println("Contact: " + user.getContact());
                    System.out.println("Balance: " + user.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    System.out.println("Deposit successful!");
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    if (user.withdraw(withdrawalAmount)) {
                        System.out.println("Withdrawal successful!");
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                    break;
                case 4:
                    System.out.print("Enter recipient's account number: ");
                    int recipientAccount = scanner.nextInt();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    User recipient = users.get(recipientAccount);
                    if (recipient != null && user.transfer(recipient, transferAmount)) {
                        System.out.println("Transfer successful!");
                    } else {
                        System.out.println("Transfer failed. Check account details or balance.");
                    }
                    break;
                case 5:
                    System.out.println("Transaction History:");
                    for (String transaction : user.getTransactionHistory()) {
                        System.out.println(transaction);
                    }
                    break;
                case 6:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
