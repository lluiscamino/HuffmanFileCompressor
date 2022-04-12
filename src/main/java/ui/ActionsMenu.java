package ui;

import logic.controller.Controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ActionsMenu extends JPanel {
    private final Controller controller;
    private final DialogSender dialogSender;

    public ActionsMenu(Controller controller, DialogSender dialogSender) {
        super(new GridBagLayout());
        this.controller = controller;
        this.dialogSender = dialogSender;
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
        } catch (IOException e) {
            dialogSender.displayAlertDialog("Error", e.getLocalizedMessage(), DialogSender.NotificationType.ERROR);
        }
    }

    private void onDecompressFile(ActionEvent event) {
        try {
            controller.decompressFile();
        } catch (IOException e) {
            dialogSender.displayAlertDialog("Error", e.getLocalizedMessage(), DialogSender.NotificationType.ERROR);
        }
    }
}
