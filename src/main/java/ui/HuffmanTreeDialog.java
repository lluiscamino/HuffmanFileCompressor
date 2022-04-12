package ui;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import logic.model.tree.visitors.DOTGraphVisitor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HuffmanTreeDialog extends JDialog {
    private static final int DEFAULT_WIDTH = 600, MIN_WIDTH_DIFF_TO_TRIGGER_REPAINT = 50;
    private static final String SVG_EXTENSION = ".svg";

    private final DialogSender dialogSender;
    private final Graphviz graph;
    private final JLabel imageLabel = new JLabel();
    private final JFileChooser fileChooser = new JFileChooser();

    private boolean componentShown = false;
    private int graphWidth = DEFAULT_WIDTH;

    public HuffmanTreeDialog(DOTGraphVisitor dotGraphVisitor, DialogSender dialogSender) {
        this.dialogSender = dialogSender;
        this.setTitle("Árbol de codificación");
        this.setLayout(new BorderLayout());
        String dotContents = dotGraphVisitor.getDOTGraph();
        this.graph = Graphviz.fromString(dotContents);
        this.renderGraph(DEFAULT_WIDTH);
        JButton saveGraphButton = new JButton("Guardar imagen en fichero");
        saveGraphButton.addActionListener(this::onGraphImageSave);
        this.add(saveGraphButton, BorderLayout.PAGE_END);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                componentShown = true;
            }

            @Override
            public void componentResized(ComponentEvent e) {
                if (shouldTriggerRepaint()) {
                    renderGraph(getWidth());
                }
            }

            private boolean shouldTriggerRepaint() {
                return componentShown && Math.abs(getWidth() - graphWidth) > MIN_WIDTH_DIFF_TO_TRIGGER_REPAINT;
            }
        });
    }

    private void renderGraph(int width) {
        graphWidth = width;
        BufferedImage graphImage = graph.width(width).render(Format.SVG).toImage();
        imageLabel.setIcon(new ImageIcon(graphImage));
        add(imageLabel, BorderLayout.CENTER);
        pack();
    }

    private void onGraphImageSave(ActionEvent event) {
        try {
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.exists() && !file.getPath().trim().endsWith(SVG_EXTENSION)) {
                    file = new File(file.getAbsolutePath() + SVG_EXTENSION);
                }
                graph.render(Format.SVG).toFile(file);
                dialogSender.displayAlertDialog("Éxito", "Imagen guardada en " + file.getName(),
                        DialogSender.NotificationType.INFO);
            }
        } catch (IOException e) {
            dialogSender.displayAlertDialog("Error", e.getLocalizedMessage(), DialogSender.NotificationType.ERROR);
        }
    }
}
