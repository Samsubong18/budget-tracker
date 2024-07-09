package com.budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetManager {
    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();
    private List<RecurringCost> recurringCosts = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void addIncome(Income income) {
        incomes.add(income);
    }

    public void addRecurringCost(RecurringCost recurringCost) {
        recurringCosts.add(recurringCost);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<RecurringCost> getRecurringCosts() {
        return recurringCosts;
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public double getTotalIncomes() {
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }
    
    public double getTotalRecurringCosts() {
        return recurringCosts.stream().mapToDouble(RecurringCost::getAmount).sum();
    }

    public List<Budget> getBudgetByDateRange(Date startDate, Date endDate) {
        List<Budget> budgets = new ArrayList<>();
        for (Expense expense : expenses) {
            if (!expense.getDate().before(startDate) && !expense.getDate().after(endDate)) {
                budgets.add(expense);
            }
        }
        for (Income income : incomes) {
            if (!income.getDate().before(startDate) && !income.getDate().after(endDate)) {
                budgets.add(income);
            }
        }
        for (RecurringCost recurringCost : recurringCosts) {
            if (!recurringCost.getDate().before(startDate) && !recurringCost.getDate().after(endDate)) {
                budgets.add(recurringCost);
            }
        }
        return budgets;
    }
}
