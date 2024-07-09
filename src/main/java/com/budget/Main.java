package com.budget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        BudgetManager budgetManager = new BudgetManager();
        FileManager fileManager = new FileManager();
        fileManager.loadData(budgetManager);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Household Budget Management");
            System.out.println("1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. Add Recurring Cost");
            System.out.println("4. View Budget for Date Range");
            System.out.println("5. View Total Income and Expenses");
            System.out.println("6. Save and Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add Expense
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    Date expenseDate = parseDate(scanner.next());
                    System.out.print("Enter amount: ");
                    double expenseAmount = scanner.nextDouble();
                    budgetManager.addExpense(new Expense(expenseDate, expenseAmount));
                    break;
                case 2:
                    // Add Income
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    Date incomeDate = parseDate(scanner.next());
                    System.out.print("Enter amount: ");
                    double incomeAmount = scanner.nextDouble();
                    budgetManager.addIncome(new Income(incomeDate, incomeAmount));
                    break;
                case 3:
                    // Add Recurring Cost
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    Date recurringCostDate = parseDate(scanner.next());
                    System.out.print("Enter amount: ");
                    double recurringCostAmount = scanner.nextDouble();
                    budgetManager.addRecurringCost(new RecurringCost(recurringCostDate, recurringCostAmount));
                    break;
                case 4:
                    // View Budget for Date Range
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    Date startDate = parseDate(scanner.next());
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    Date endDate = parseDate(scanner.next());
                    List<Budget> budgets = budgetManager.getBudgetByDateRange(startDate, endDate);
                    for (Budget budget : budgets) {
                        System.out.println(budget.getClass().getSimpleName() + ": " + sdf.format(budget.getDate()) + " - " + budget.getAmount());
                    }
                    break;
                case 5:
                    // View Total Income and Expenses
                    System.out.println("Total Income: " + budgetManager.getTotalIncomes());
                    System.out.println("Total Expenses: " + budgetManager.getTotalExpenses());
                    System.out.println("Total Recurring Costs: " + budgetManager.getTotalRecurringCosts());
                    break;
                case 6:
                    // Save and Exit
                    fileManager.saveData(budgetManager.getExpenses(), budgetManager.getIncomes(), budgetManager.getRecurringCosts());
                    System.out.println("Data saved successfully. Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);
    }

    private static Date parseDate(String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return null;
        }
    }
}
