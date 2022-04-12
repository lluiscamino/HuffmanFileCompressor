package ui;

import javax.swing.JOptionPane;

public class DialogSender {
    public void displayAlertDialog(String title, String message, NotificationType type) {
        JOptionPane.showMessageDialog(null, message, title, type.toJOptionPaneMessageType());
    }

    public int displayOptionDialog(String title, String message, NotificationType type, String[] options) {
        int optionType = switch (options.length) {
            case 1 -> JOptionPane.YES_OPTION;
            case 2 -> JOptionPane.YES_NO_OPTION;
            default -> JOptionPane.YES_NO_CANCEL_OPTION;
        };
        return JOptionPane.showOptionDialog(null, message, title, optionType, type.toJOptionPaneMessageType(),
                null, options, options[0]);
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
