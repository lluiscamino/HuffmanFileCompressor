package ui;

import logic.controller.Controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class ActionsMenu extends JPanel {
    private final Controller controller;
    private final AlertSender alertSender;

    public ActionsMenu(Controller controller, AlertSender alertSender) {
        super(new GridBagLayout());
        this.controller = controller;
        this.alertSender = alertSender;
        this.setBorder(BorderFactory.createTitledBorder("Seleccionar acción"));
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
        JButton compressButton = new JButton("Comprimir"),
                decompressButton = new JButton("Descomprimir");
        compressButton.addActionListener(this::onCompressFile);
        decompressButton.addActionListener(this::onDecompressFile);
        buttonsPanel.add(compressButton);
        buttonsPanel.add(decompressButton);
        add(buttonsPanel);
    }

    private void onCompressFile(ActionEvent event) {
        try {
            controller.compressFile();
        } catch (Exception e) {
            alertSender.displayAlert("Error", e.getLocalizedMessage(), AlertSender.NotificationType.ERROR);
        }
    }

    private void onDecompressFile(ActionEvent event) {
        try {
            controller.decompressFile();
        } catch (Exception e) {
            alertSender.displayAlert("Error", e.getLocalizedMessage(), AlertSender.NotificationType.ERROR);
        }
    }
}
