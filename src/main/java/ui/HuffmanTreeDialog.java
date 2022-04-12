package ui;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import logic.model.tree.visitors.DOTGraphVisitor;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class HuffmanTreeDialog extends JDialog {
    private static final int DEFAULT_WIDTH = 600;
    private static final int MIN_WIDTH_DIFF_TO_TRIGGER_REPAINT = 50;

    private final Graphviz graph;
    private final JLabel imageLabel = new JLabel();

    private boolean componentShown = false;
    private int graphWidth = DEFAULT_WIDTH;

    public HuffmanTreeDialog(DOTGraphVisitor dotGraphVisitor) {
        setTitle("Árbol de codificación");
        String dotContents = dotGraphVisitor.getDOTGraph();
        graph = Graphviz.fromString(dotContents);
        renderGraph(DEFAULT_WIDTH);
        addComponentListener(new ComponentAdapter() {
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
        add(imageLabel);
        pack();
    }
}
