package ui;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppGui app = new AppGui();
            app.setVisible(true);
        });
    }
}
