package ui;

import logic.controller.Controller;

import javax.swing.JFrame;
import java.awt.HeadlessException;

public class ApplicationUI extends JFrame {
    public ApplicationUI(Controller controller) throws HeadlessException {
        super("Compresor de archivos Huffman");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new FileCompressionPanel(controller));
        pack();
    }
}
