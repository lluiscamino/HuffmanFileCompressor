package ui;

import logic.controller.Controller;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileCompressionPanel extends JPanel {
    private final Controller controller;
    private final JFileChooser fileChooser = new JFileChooser();
    private final JTextField filePathField = new JTextField();

    public FileCompressionPanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Comprimir archivos"));
        JButton selectFileButton = new JButton("Seleccionar archivo"),
                compressFileButton = new JButton("Comprimir");
        this.add(selectFileButton);
        this.add(filePathField);
        this.add(compressFileButton);
        selectFileButton.addActionListener(this::onFileSelectorOpen);
        compressFileButton.addActionListener(event -> new Thread(this::onCompressFileClick).start());
    }

    private void onFileSelectorOpen(ActionEvent event) {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void onCompressFileClick() {
        try {
            File originalFile = new File(filePathField.getText());
            File compressedFile = new File(originalFile.getAbsolutePath() + controller.getCompressedFileExtension());
            controller.compressFile(originalFile, compressedFile);
            String successMessage = buildSuccessMessage(originalFile, compressedFile);
            JOptionPane.showMessageDialog(this, successMessage, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String buildSuccessMessage(File originalFile, File compressedFile) throws IOException {
        long originalFileSize = Files.size(originalFile.toPath()),
                compressedFileSize = Files.size(compressedFile.toPath());
        float compressionRatio = (float) originalFileSize / compressedFileSize;
        return String.format("""
                ¡Archivo comprimido con éxito en %s!
                - Tamaño archivo original: %dB
                - Tamaño archivo comprimido: %dB
                - Ratio compresión: %f
                """, compressedFile.getName(), originalFileSize, compressedFileSize, compressionRatio);
    }
}
