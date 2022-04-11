package ui;

import logic.controller.Controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class ActionsMenu extends JPanel {
    private final Controller controller;
    private final AlertSender alertSender;

    public ActionsMenu(Controller controller, AlertSender alertSender) {
        super(new GridBagLayout());
        this.controller = controller;
        this.alertSender = alertSender;
        this.setBorder(BorderFactory.createTitledBorder("Seleccionar acción"));
        JButton compressButton = new JButton("Comprimir"), decompressButton = new JButton("Descomprimir");
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
        buttonsPanel.add(compressButton);
        buttonsPanel.add(decompressButton);
        add(buttonsPanel);
    }
}
