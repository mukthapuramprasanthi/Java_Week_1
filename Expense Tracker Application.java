// Expense Tracker Application in Java

import java.util.*;

class Expense {
    private String date;
    private double amount;
    private String category;
    private String description;

    public Expense(String date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void displayDetails() {
        System.out.println("Date: " + date);
        System.out.println("Amount: " + amount);
        System.out.println("Category: " + category);
        System.out.println("Description: " + description);
    }
}

public class ExpenseTracker {
    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Filter Expenses by Category");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    filterExpensesByCategory(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter Category: ");
        String category = scanner.next();
        System.out.print("Enter Description: ");
        String description = scanner.next();

        Expense expense = new Expense(date, amount, category, description);
        expenses.add(expense);

        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\nExpenses:");
        for (Expense expense : expenses) {
            expense.displayDetails();
            System.out.println("-------------------");
        }
    }

    private static void filterExpensesByCategory(Scanner scanner) {
        System.out.print("Enter Category to Filter: ");
        String category = scanner.next();

        System.out.println("\nFiltered Expenses:");
        boolean found = false;
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                expense.displayDetails();
                System.out.println("-------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found for the category: " + category);
        }
    }
}
