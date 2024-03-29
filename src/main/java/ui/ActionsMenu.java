package ui;

import logic.controller.Controller;
import logic.model.bits.BitSequence;
import logic.model.transformation.Transformation;
import logic.model.tree.HuffmanTree;
import logic.model.tree.visitors.DOTGraphVisitor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Map;

public class ActionsMenu extends JPanel {
    private static final String[] SUCCESS_DIALOG_OPTIONS = {"Ver codificación", "Cerrar"};
    private static final int DISPLAY_ENCODING_INDEX = 0;

    private final Controller controller;
    private final DialogSender dialogSender;
    private final JButton compressButton = new JButton("Comprimir"),
            decompressButton = new JButton("Descomprimir");

    public ActionsMenu(Controller controller, DialogSender dialogSender) {
        super(new GridBagLayout());
        this.controller = controller;
        this.dialogSender = dialogSender;
        this.setBorder(BorderFactory.createTitledBorder("Seleccionar acción"));
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
        compressButton.addActionListener(event -> new Thread(this::onCompressFile).start());
        decompressButton.addActionListener(event -> new Thread(this::onDecompressFile).start());
        buttonsPanel.add(compressButton);
        buttonsPanel.add(decompressButton);
        add(buttonsPanel);
    }

    private void onCompressFile() {
        try {
            signalTransformationStart();
            Transformation transformation = controller.compressFile();
            int option = dialogSender.displayOptionDialog(
                    buildCompressionSuccessTitle(transformation),
                    buildCompressionSuccessMessage(transformation),
                    DialogSender.NotificationType.INFO,
                    SUCCESS_DIALOG_OPTIONS
            );
            if (option == DISPLAY_ENCODING_INDEX) {
                showHuffmanTreeDialog(transformation.huffmanTree());
                showBitEncodingMapDialog(transformation.bitEncodingMap());
            }
        } catch (IOException | IllegalStateException e) {
            dialogSender.displayAlertDialog("Error", e.getLocalizedMessage(), DialogSender.NotificationType.ERROR);
        } finally {
            signalTransformationEnd();
        }
    }

    private void onDecompressFile() {
        try {
            signalTransformationStart();
            Transformation transformation = controller.decompressFile();
            int option = dialogSender.displayOptionDialog(
                    buildDecompressionSuccessTitle(transformation),
                    buildDecompressionSuccessMessage(transformation),
                    DialogSender.NotificationType.INFO,
                    SUCCESS_DIALOG_OPTIONS
            );
            if (option == DISPLAY_ENCODING_INDEX) {
                showHuffmanTreeDialog(transformation.huffmanTree());
            }
        } catch (IOException | IllegalStateException e) {
            dialogSender.displayAlertDialog("Error", e.getLocalizedMessage(), DialogSender.NotificationType.ERROR);
        } finally {
            signalTransformationEnd();
        }
    }

    private void signalTransformationStart() {
        compressButton.setEnabled(false);
        decompressButton.setEnabled(false);
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }

    private void signalTransformationEnd() {
        compressButton.setEnabled(true);
        decompressButton.setEnabled(true);
        setCursor(Cursor.getDefaultCursor());
    }

    private String buildCompressionSuccessTitle(Transformation transformation) {
        String inputFileName = transformation.inputFile().getName();
        return "¡Fichero " + inputFileName + " comprimido con éxito!";
    }

    private String buildCompressionSuccessMessage(Transformation transformation) {
        return String.format("""
                        - Archivo comprimido: %s
                        - Tamaño archivo original: %dB
                        - Tamaño archivo comprimido: %dB
                        - Tamaño mínimo teórico: %dB
                        - Ratio compresión: %f
                        - Entropía: %f
                        - Tiempo compresión: %s
                        """, transformation.outputFile().getPath(), transformation.inputFileSizeBytes(),
                transformation.outputFileSizeBytes(), transformation.theoreticalExpectedFileSize(),
                transformation.fileSizeRatio(), transformation.entropy(),
                transformation.humanReadableDuration());
    }

    private String buildDecompressionSuccessTitle(Transformation transformation) {
        String inputFileName = transformation.inputFile().getName();
        return "¡Fichero " + inputFileName + " extraído con éxito!";
    }

    private String buildDecompressionSuccessMessage(Transformation transformation) {
        return String.format("""
                        - Archivo extraído: %s
                        - Tamaño archivo comprimido: %dB
                        - Tamaño archivo extraído: %dB
                        - Tiempo extracción: %s
                        """, transformation.outputFile().getPath(), transformation.inputFileSizeBytes(),
                transformation.outputFileSizeBytes(), transformation.humanReadableDuration());
    }

    private void showHuffmanTreeDialog(HuffmanTree huffmanTree) {
        HuffmanTreeDialog dialog = new HuffmanTreeDialog(new DOTGraphVisitor(huffmanTree), dialogSender);
        dialog.setVisible(true);
    }

    private void showBitEncodingMapDialog(Map<Byte, BitSequence> bitEncodingMap) {
        BitEncodingMapDialog dialog = new BitEncodingMapDialog(bitEncodingMap);
        dialog.setVisible(true);
    }
}