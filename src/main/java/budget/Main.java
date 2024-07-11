package budget;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BudgetManager manager = new BudgetManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHousehold Budget Application");
            System.out.println("1. Create a new budget");
            System.out.println("2. Add an expense");
            System.out.println("3. View saved budgets");
            System.out.println("4. Edit a budget");
            System.out.println("5. Edit an expense");
            System.out.println("6. View budget by date range");
            System.out.println("7. Delete a budget");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nCreate a New Budget:");
                    System.out.print("Enter the name of the budget: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the total income: ");
                    double income = 0;
                    while (true) {
                        try {
                            income = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. Enter the total income as a number: ");
                        }
                    }
                    System.out.print("Enter the start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Enter the end date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    manager.createBudget(name, income, startDate, endDate);
                    break;
                case 2:
                    System.out.println("\nAdd an Expense:");
                    System.out.print("Enter the name of the budget to add an expense: ");
                    String budgetName = scanner.nextLine();
                    System.out.print("Enter the expense amount: ");
                    double amount = 0;
                    while (true) {
                        try {
                            amount = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. Enter the amount as a number: ");
                        }
                    }
                    System.out.print("Enter the expense description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter the date of the expense (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    manager.addExpense(budgetName, amount, description, date);
                    break;
                case 3:
                    System.out.println("\nView Saved Budgets:");
                    manager.viewBudgets();
                    break;
                case 4:
                    System.out.println("\nEdit a Budget:");
                    System.out.print("Enter the name of the budget to edit: ");
                    String editBudgetName = scanner.nextLine();
                    System.out.print("What do you want to edit? (income/start_date/end_date): ");
                    String field = scanner.nextLine();
                    System.out.print("Enter the new value: ");
                    String newValue = scanner.nextLine();
                    manager.editBudget(editBudgetName, field, newValue);
                    break;
                case 5:
                    System.out.println("\nEdit an Expense:");
                    System.out.print("Enter the name of the budget to edit: ");
                    String editExpenseBudgetName = scanner.nextLine();
                    System.out.print("Enter the expense ID to edit: ");
                    int expenseIndex = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter the field to edit (amount/description/date): ");
                    String expenseField = scanner.nextLine();
                    System.out.print("Enter the new value: ");
                    String expenseNewValue = scanner.nextLine();
                    manager.editExpense(editExpenseBudgetName, expenseIndex, expenseField, expenseNewValue);
                    break;
                case 6:
                    System.out.println("\nView Budget by Date Range:");
                    System.out.print("Enter the name of the budget to view: ");
                    String viewBudgetName = scanner.nextLine();
                    System.out.print("Enter the start date of the range (YYYY-MM-DD): ");
                    String viewStartDate = scanner.nextLine();
                    System.out.print("Enter the end date of the range (YYYY-MM-DD): ");
                    String viewEndDate = scanner.nextLine();
                    manager.viewBudgetByDateRange(viewBudgetName, viewStartDate, viewEndDate);
                    break;
                case 7:
                    System.out.println("\nDelete a Budget:");
                    System.out.print("Enter the name of the budget to delete: ");
                    String deleteBudgetName = scanner.nextLine();
                    manager.deleteBudget(deleteBudgetName);
                    break;
                case 8:
                    System.out.println("\nExiting Program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
