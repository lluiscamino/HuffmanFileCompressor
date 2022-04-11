package ui;

import javax.swing.JOptionPane;

public class AlertSender {
    public void displayAlert(String title, String message, NotificationType type) {
        JOptionPane.showMessageDialog(null, message, title, type.toJOptionPaneMessageType());
    }

    public enum NotificationType {
        NONE, INFO, WARNING, ERROR;

        public int toJOptionPaneMessageType() {
            return switch (this) {
                case NONE -> JOptionPane.PLAIN_MESSAGE;
                case INFO -> JOptionPane.INFORMATION_MESSAGE;
                case WARNING -> JOptionPane.WARNING_MESSAGE;
                case ERROR -> JOptionPane.ERROR_MESSAGE;
            };
        }
    }
}
