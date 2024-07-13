package budget;

import java.io.Serializable;

public class RecurringExpense implements Serializable {
    private static final long serialVersionUID = 1L;
    private double amount;
    private String description;
    private String startDate;
    private String endDate;
    private String frequency; // e.g., "daily", "weekly", "monthly"

    public RecurringExpense(double amount, String description, String startDate, String endDate, String frequency) {
        this.amount = amount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public double getTotalAmount() {
        // Calculate the total amount based on frequency and the duration between start and end dates
        // This is a simple placeholder. You may need a more sophisticated calculation.
        return amount;
    }

    @Override
    public String toString() {
        return "RecurringExpense{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
