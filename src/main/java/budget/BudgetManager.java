package budget;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BudgetManager {
    private static final String BUDGET_FILE = "budgets.dat";
    private List<Budget> budgets;

    // ANSI escape code for light green
    private static final String ANSI_LIGHT_GREEN = "\u001B[92m";
    private static final String ANSI_RESET = "\u001B[0m";

    public BudgetManager() {
        budgets = loadBudgets();
    }

    @SuppressWarnings("unchecked")
    private List<Budget> loadBudgets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BUDGET_FILE))) {
            return (List<Budget>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void saveBudgets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BUDGET_FILE))) {
            oos.writeObject(budgets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBudget(String name, double income, String startDate, String endDate) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!isValidDate(startDate) || !isValidDate(endDate)) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                System.out.print("Enter the start date (YYYY-MM-DD): ");
                startDate = scanner.nextLine();
                System.out.print("Enter the end date (YYYY-MM-DD): ");
                endDate = scanner.nextLine();
            } else {
                break;
            }
        }

        // Check if the budget name already exists
        if (findBudgetByName(name) != null) {
            System.out.println("Budget with name '" + name + "' already exists. Please choose another name.");
            return;
        }

        budgets.add(new Budget(name, income, startDate, endDate));
        saveBudgets();
        System.out.println(ANSI_LIGHT_GREEN + "Budget created successfully." + ANSI_RESET);
    }

    public void addExpense(String budgetName, double amount, String description, String date) {
        if (!isValidDate(date)) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        budget.addExpense(new Expense(amount, description, date));
        saveBudgets();
        System.out.println(ANSI_LIGHT_GREEN + "Expense added successfully." + ANSI_RESET);
    }

    public void editBudget(String budgetName, String field, String newValue) {
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        switch (field.toLowerCase()) {
            case "income":
                try {
                    budget.setIncome(Double.parseDouble(newValue));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid income value. Please enter a numeric value.");
                    return;
                }
                break;
            case "start_date":
                if (!isValidDate(newValue)) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    return;
                }
                budget.setStartDate(newValue);
                break;
            case "end_date":
                if (!isValidDate(newValue)) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    return;
                }
                budget.setEndDate(newValue);
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }
        saveBudgets();
        System.out.println(ANSI_LIGHT_GREEN + "Budget edited successfully." + ANSI_RESET);
    }

    public void editExpense(String budgetName, int expenseIndex, String field, String newValue) {
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        if (expenseIndex < 0 || expenseIndex >= budget.getExpenses().size()) {
            System.out.println("Invalid expense index.");
            return;
        }
        Expense expense = budget.getExpenses().get(expenseIndex);
        switch (field.toLowerCase()) {
            case "amount":
                try {
                    expense.setAmount(Double.parseDouble(newValue));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount value. Please enter a numeric value.");
                    return;
                }
                break;
            case "description":
                expense.setDescription(newValue);
                break;
            case "date":
                if (!isValidDate(newValue)) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    return;
                }
                expense.setDate(newValue);
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }
        saveBudgets();
        System.out.println(ANSI_LIGHT_GREEN + "Expense edited successfully." + ANSI_RESET);
    }

    public void deleteBudget(String budgetName) {
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        budgets.remove(budget);
        saveBudgets();
        System.out.println(ANSI_LIGHT_GREEN + "Budget deleted successfully." + ANSI_RESET);
    }

    public void viewBudgets() {
        if (budgets.isEmpty()) {
            System.out.println("No budgets available.");
        } else {
            for (Budget budget : budgets) {
                System.out.println(budget);
            }
        }
    }

    public void viewBudgetByDateRange(String budgetName, String startDate, String endDate) {
        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        System.out.println("Total Income: " + budget.getIncome());
        double totalExpenses = 0;
        for (Expense expense : budget.getExpenses()) {
            if (startDate.compareTo(expense.getDate()) <= 0 && endDate.compareTo(expense.getDate()) >= 0) {
                totalExpenses += expense.getAmount();
                System.out.println(expense);
            }
        }
        System.out.println("Total Expenses: " + totalExpenses);
    }

    private Budget findBudgetByName(String budgetName) {
        for (Budget budget : budgets) {
            if (budget.getName().equalsIgnoreCase(budgetName)) {
                return budget;
            }
        }
        return null;
    }

    private boolean isValidDate(String date) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
