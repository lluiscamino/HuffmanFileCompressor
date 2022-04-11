package ui;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class FileSelectorPanel extends JPanel {
    public FileSelectorPanel() {
        super(new CardLayout());
        this.setBorder(BorderFactory.createTitledBorder("Seleccionar archivo"));
        this.add(new JFileChooser());
    }
}
