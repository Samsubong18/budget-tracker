package budget;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BudgetManager manager = new BudgetManager();
            new BudgetGUI(manager);
        });
    }
}
