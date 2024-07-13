package budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BudgetGUI extends JFrame {
    private BudgetManager manager;
    private JTextArea displayArea;

    public BudgetGUI(BudgetManager manager) {
        this.manager = manager;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Household Budget Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2));
        addButton("Create a new budget", buttonPanel, e -> createBudget());
        addButton("Add an expense", buttonPanel, e -> addExpense());
        addButton("View saved budgets", buttonPanel, e -> viewBudgets());
        addButton("Edit a budget", buttonPanel, e -> editBudget());
        addButton("Edit an expense", buttonPanel, e -> editExpense());
        addButton("View budget by date range", buttonPanel, e -> viewBudgetByDateRange());
        addButton("Delete a budget", buttonPanel, e -> deleteBudget());
        addButton("Exit", buttonPanel, e -> System.exit(0));

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void addButton(String title, JPanel panel, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        panel.add(button);
    }

    private void createBudget() {
        String name = JOptionPane.showInputDialog(this, "Enter the name of the budget:");
        if (name == null) return;

        String incomeStr = JOptionPane.showInputDialog(this, "Enter the total income:");
        if (incomeStr == null) return;
        double income;
        try {
            income = Double.parseDouble(incomeStr);
        } catch (NumberFormatException e) {
            showMessage("Invalid input. Enter the total income as a number.");
            return;
        }

        String startDate = JOptionPane.showInputDialog(this, "Enter the start date (YYYY-MM-DD):");
        if (startDate == null) return;

        String endDate = JOptionPane.showInputDialog(this, "Enter the end date (YYYY-MM-DD):");
        if (endDate == null) return;

        manager.createBudget(name, income, startDate, endDate);
        showMessage("Budget created successfully.");
    }

    private void addExpense() {
        List<Budget> budgets = manager.getBudgets();
        if (budgets.isEmpty()) {
            showMessage("No budgets available. Please create a budget first.");
            return;
        }

        Budget selectedBudget = (Budget) JOptionPane.showInputDialog(this, "Select a budget:",
                "Add Expense", JOptionPane.QUESTION_MESSAGE, null, budgets.toArray(), budgets.get(0));
        if (selectedBudget == null) return;

        String amountStr = JOptionPane.showInputDialog(this, "Enter the expense amount:");
        if (amountStr == null) return;
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            showMessage("Invalid input. Enter the amount as a number.");
            return;
        }

        String description = JOptionPane.showInputDialog(this, "Enter the expense description:");
        if (description == null) return;

        String date = JOptionPane.showInputDialog(this, "Enter the date of the expense (YYYY-MM-DD):");
        if (date == null) return;

        manager.addExpense(selectedBudget.getName(), amount, description, date);
        showMessage("Expense added successfully.");
    }

    private void viewBudgets() {
        StringBuilder sb = new StringBuilder("Saved Budgets:\n");
        for (Budget budget : manager.getBudgets()) {
            sb.append(budget.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void editBudget() {
        List<Budget> budgets = manager.getBudgets();
        if (budgets.isEmpty()) {
            showMessage("No budgets available to edit.");
            return;
        }

        Budget selectedBudget = (Budget) JOptionPane.showInputDialog(this, "Select a budget to edit:",
                "Edit Budget", JOptionPane.QUESTION_MESSAGE, null, budgets.toArray(), budgets.get(0));
        if (selectedBudget == null) return;

        String field = JOptionPane.showInputDialog(this, "What do you want to edit? (income/start_date/end_date):");
        if (field == null) return;

        String newValue = JOptionPane.showInputDialog(this, "Enter the new value:");
        if (newValue == null) return;

        manager.editBudget(selectedBudget.getName(), field, newValue);
        showMessage("Budget edited successfully.");
    }

    private void editExpense() {
        List<Budget> budgets = manager.getBudgets();
        if (budgets.isEmpty()) {
            showMessage("No budgets available to edit.");
            return;
        }

        Budget selectedBudget = (Budget) JOptionPane.showInputDialog(this, "Select a budget to edit:",
                "Edit Expense", JOptionPane.QUESTION_MESSAGE, null, budgets.toArray(), budgets.get(0));
        if (selectedBudget == null) return;

        List<Expense> expenses = selectedBudget.getExpenses();
        if (expenses.isEmpty()) {
            showMessage("No expenses available to edit.");
            return;
        }

        Expense selectedExpense = (Expense) JOptionPane.showInputDialog(this, "Select an expense to edit:",
                "Edit Expense", JOptionPane.QUESTION_MESSAGE, null, expenses.toArray(), expenses.get(0));
        if (selectedExpense == null) return;

        String field = JOptionPane.showInputDialog(this, "Enter the field to edit (amount/description/date):");
        if (field == null) return;

        String newValue = JOptionPane.showInputDialog(this, "Enter the new value:");
        if (newValue == null) return;

        manager.editExpense(selectedBudget.getName(), expenses.indexOf(selectedExpense), field, newValue);
        showMessage("Expense edited successfully.");
    }

    private void viewBudgetByDateRange() {
        List<Budget> budgets = manager.getBudgets();
        if (budgets.isEmpty()) {
            showMessage("No budgets available to view.");
            return;
        }

        Budget selectedBudget = (Budget) JOptionPane.showInputDialog(this, "Select a budget to view:",
                "View Budget by Date Range", JOptionPane.QUESTION_MESSAGE, null, budgets.toArray(), budgets.get(0));
        if (selectedBudget == null) return;

        String startDate = JOptionPane.showInputDialog(this, "Enter the start date of the range (YYYY-MM-DD):");
        if (startDate == null) return;

        String endDate = JOptionPane.showInputDialog(this, "Enter the end date of the range (YYYY-MM-DD):");
        if (endDate == null) return;

        StringBuilder sb = new StringBuilder("Expenses for " + selectedBudget.getName() + " from " + startDate + " to " + endDate + ":\n");
        for (Expense expense : selectedBudget.getExpenses()) {
            if (expense.getDate().compareTo(startDate) >= 0 && expense.getDate().compareTo(endDate) <= 0) {
                sb.append(expense.toString()).append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }

    private void deleteBudget() {
        List<Budget> budgets = manager.getBudgets();
        if (budgets.isEmpty()) {
            showMessage("No budgets available to delete.");
            return;
        }

        Budget selectedBudget = (Budget) JOptionPane.showInputDialog(this, "Select a budget to delete:",
                "Delete Budget", JOptionPane.QUESTION_MESSAGE, null, budgets.toArray(), budgets.get(0));
        if (selectedBudget == null) return;

        manager.deleteBudget(selectedBudget.getName());
        showMessage("Budget deleted successfully.");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
