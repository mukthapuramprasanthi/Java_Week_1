// Human Resource Management System (HRMS) in Java

import java.util.*;

class Employee {
    private String name;
    private int employeeID;
    private String designation;
    private String department;
    private String contactInfo;

    public Employee(String name, int employeeID, String designation, String department, String contactInfo) {
        this.name = name;
        this.employeeID = employeeID;
        this.designation = designation;
        this.department = department;
        this.contactInfo = contactInfo;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void displayDetails() {
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Name: " + name);
        System.out.println("Designation: " + designation);
        System.out.println("Department: " + department);
        System.out.println("Contact Info: " + contactInfo);
    }

    public void updateDetails(String designation, String department, String contactInfo) {
        this.designation = designation;
        this.department = department;
        this.contactInfo = contactInfo;
    }
}

public class HumanResourceManagementSystem {
    private static Map<Integer, Employee> employees = new HashMap<>();
    private static int nextEmployeeID = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nHuman Resource Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    viewEmployee(scanner);
                    break;
                case 3:
                    updateEmployee(scanner);
                    break;
                case 4:
                    deleteEmployee(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.next();
        System.out.print("Enter Designation: ");
        String designation = scanner.next();
        System.out.print("Enter Department: ");
        String department = scanner.next();
        System.out.print("Enter Contact Info: ");
        String contactInfo = scanner.next();

        int employeeID = nextEmployeeID++;
        Employee employee = new Employee(name, employeeID, designation, department, contactInfo);
        employees.put(employeeID, employee);

        System.out.println("Employee added successfully. Employee ID: " + employeeID);
    }

    private static void viewEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int employeeID = scanner.nextInt();
        Employee employee = employees.get(employeeID);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        employee.displayDetails();
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int employeeID = scanner.nextInt();
        Employee employee = employees.get(employeeID);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("Enter New Designation: ");
        String designation = scanner.next();
        System.out.print("Enter New Department: ");
        String department = scanner.next();
        System.out.print("Enter New Contact Info: ");
        String contactInfo = scanner.next();

        employee.updateDetails(designation, department, contactInfo);
        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int employeeID = scanner.nextInt();
        if (employees.remove(employeeID) != null) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }
}
