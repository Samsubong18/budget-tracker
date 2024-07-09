package com.budget;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class FileManager {
    private static final String FILE_PATH = "budget_data.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void saveData(List<Expense> expenses, List<Income> incomes, List<RecurringCost> recurringCosts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Expense expense : expenses) {
                writer.write("Expense," + sdf.format(expense.getDate()) + "," + expense.getAmount() + "\n");
            }
            for (Income income : incomes) {
                writer.write("Income," + sdf.format(income.getDate()) + "," + income.getAmount() + "\n");
            }
            for (RecurringCost recurringCost : recurringCosts) {
                writer.write("RecurringCost," + sdf.format(recurringCost.getDate()) + "," + recurringCost.getAmount() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(BudgetManager budgetManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                Date date = sdf.parse(parts[1]);
                double amount = Double.parseDouble(parts[2]);

                switch (type) {
                    case "Expense":
                        budgetManager.addExpense(new Expense(date, amount));
                        break;
                    case "Income":
                        budgetManager.addIncome(new Income(date, amount));
                        break;
                    case "RecurringCost":
                        budgetManager.addRecurringCost(new RecurringCost(date, amount));
                        break;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
