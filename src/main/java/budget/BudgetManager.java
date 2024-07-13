package budget;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private List<Budget> budgets;
    private static final String FILE_NAME = "budgets.ser";

    public BudgetManager() {
        budgets = new ArrayList<>();
        loadBudgets();
    }

    public void createBudget(String name, double income, String startDate, String endDate) {
        Budget budget = new Budget(name, income, startDate, endDate);
        budgets.add(budget);
        saveBudgets();
    }

    public void addExpense(String budgetName, double amount, String description, String date) {
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        budget.addExpense(new Expense(amount, description, date));
        saveBudgets();
    }

    public void editBudget(String budgetName, String field, String newValue) {
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        switch (field) {
            case "income":
                try {
                    double newIncome = Double.parseDouble(newValue);
                    budget.setIncome(newIncome);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter the income as a number.");
                }
                break;
            case "start_date":
                budget.setStartDate(newValue);
                break;
            case "end_date":
                budget.setEndDate(newValue);
                break;
            default:
                System.out.println("Invalid field. Valid fields are: income, start_date, end_date.");
                return;
        }
        saveBudgets();
    }

    public void editExpense(String budgetName, int expenseIndex, String field, String newValue) {
        Budget budget = findBudgetByName(budgetName);
        if (budget == null) {
            System.out.println("Budget not found. Please enter a valid budget name.");
            return;
        }
        try {
            Expense expense = budget.getExpenses().get(expenseIndex);
            switch (field) {
                case "amount":
                    expense.setAmount(Double.parseDouble(newValue));
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
                    System.out.println("Invalid field. Valid fields are: amount, description, date.");
                    return;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Expense index out of bounds.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for amount. Enter a number.");
        }
        saveBudgets();
    }

    public Budget findBudgetByName(String name) {
        return budgets.stream()
                .filter(budget -> budget.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void deleteBudget(String budgetName) {
        Budget budget = findBudgetByName(budgetName);
        if (budget != null) {
            budgets.remove(budget);
            saveBudgets();
        }
    }

    private void saveBudgets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(budgets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBudgets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            budgets = (List<Budget>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            budgets = new ArrayList<>();
        }
    }
}
