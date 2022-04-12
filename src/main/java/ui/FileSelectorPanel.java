package ui;

import logic.controller.Controller;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;

public class FileSelectorPanel extends JPanel {
    private final Controller controller;
    private final JFileChooser fileChooser = new JFileChooser();

    public FileSelectorPanel(Controller controller) {
        super(new CardLayout());
        this.controller = controller;
        this.setBorder(BorderFactory.createTitledBorder("Seleccionar archivo"));
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.fileChooser.addPropertyChangeListener(this::onFileSelect);
        this.add(fileChooser);
    }

    private void onFileSelect(PropertyChangeEvent propertyChangeEvent) {
        controller.selectFile(fileChooser.getSelectedFile());
    }
}
