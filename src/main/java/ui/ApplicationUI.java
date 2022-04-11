package ui;

import logic.controller.Controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.HeadlessException;

public class ApplicationUI extends JFrame {
    public ApplicationUI(Controller controller) throws HeadlessException {
        super("Compresor de archivos Huffman");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.add(new FileSelectorPanel());
        mainPanel.add(new ActionsMenu(controller, new AlertSender()));
        add(mainPanel);
        pack();
    }
}
