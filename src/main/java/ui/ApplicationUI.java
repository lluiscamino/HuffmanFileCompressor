package ui;

import logic.controller.Controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.HeadlessException;

public class ApplicationUI extends JFrame {
    public ApplicationUI(Controller controller) throws HeadlessException {
        super("Compresor de archivos Huffman");
        setSystemLookAndFeel();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.add(new FileSelectorPanel(controller));
        mainPanel.add(new ActionsMenu(controller, new DialogSender()));
        add(mainPanel);
        pack();
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
