/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BudgetEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date date;
    private String type;
    private double amount;
    private String description;

    public BudgetEntry(Date date, String type, double amount, String description) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Date: %s, Type: %s, Amount: %.2f, Description: %s", 
            sdf.format(date), type, amount, description);
    }
}
