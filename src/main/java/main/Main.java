package main;

import logic.controller.Controller;
import logic.controller.InputStreamFactory;
import logic.controller.OutputStreamFactory;
import logic.decoder.Decoder;
import logic.decoder.HuffmanTreeDecoder;
import logic.encoder.BitEncodingMapBuilder;
import logic.encoder.Encoder;
import logic.encoder.HuffmanTreeBuilder;
import logic.encoder.HuffmanTreeEncoder;
import ui.ApplicationUI;

public class Main {
    public static void main(String[] args) {
        Encoder encoder = new Encoder(new HuffmanTreeBuilder(), new BitEncodingMapBuilder(), new HuffmanTreeEncoder());
        Decoder decoder = new Decoder(new HuffmanTreeDecoder());
        Controller controller = new Controller(encoder, decoder, new InputStreamFactory(), new OutputStreamFactory());
        ApplicationUI ui = new ApplicationUI(controller);
        ui.setVisible(true);
    }
}
