package ui;

import logic.model.bits.BitSequence;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.Map;
import java.util.stream.Collectors;

public class BitEncodingMapDialog extends JDialog {
    private static final int HEIGHT_PER_ITEM = 21, WIDTH = 250, MAX_HEIGHT = 380;

    public BitEncodingMapDialog(Map<Byte, BitSequence> bitEncodingMap) {
        setTitle("Codificación Huffman");
        int height = Math.min(HEIGHT_PER_ITEM * bitEncodingMap.size(), MAX_HEIGHT);
        setBounds(0, 0, WIDTH, height);
        String bitEncodingMapString = getBitEncodingMapHTMLString(bitEncodingMap);
        JLabel label = new JLabel(bitEncodingMapString);
        JScrollPane scrollPane = new JScrollPane();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(label);
    }

    private String getBitEncodingMapHTMLString(Map<Byte, BitSequence> bitEncodingMap) {
        return "<html>" +
                bitEncodingMap
                        .entrySet()
                        .stream()
                        .map(this::getBitEncodingMapEntryHTMLString)
                        .collect(Collectors.joining("<br>"))
                + "</html>";
    }

    private String getBitEncodingMapEntryHTMLString(Map.Entry<Byte, BitSequence> entry) {
        return "<b>" + entry.getValue() + "</b>: " + entry.getKey();
    }
}
