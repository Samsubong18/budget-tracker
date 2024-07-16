package budget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Budget implements Serializable {
    private String name;
    private double income;
    private String startDate;
    private String endDate;
    private List<Expense> expenses;

    public Budget(String name, double income, String startDate, String endDate) {
        this.name = name;
        this.income = income;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public double getAvailableBalance() {
        return income - getTotalExpenses();
    }

    @Override
    public String toString() {
        return String.format("Budget: %s\nIncome: %.2f\nStart Date: %s\nEnd Date: %s\nTotal Expenses: %.2f\nAvailable Balance: %.2f\n",
                name, income, startDate, endDate, getTotalExpenses(), getAvailableBalance());
    }
}
