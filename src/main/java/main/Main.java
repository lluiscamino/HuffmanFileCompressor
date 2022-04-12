package main;

import logic.controller.ApplicationController;
import logic.controller.FileFactory;
import logic.controller.InputStreamFactory;
import logic.controller.OutputStreamFactory;
import logic.controller.TransformationFactory;
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
        ApplicationController controller = new ApplicationController(encoder, decoder, new FileFactory(),
                new InputStreamFactory(), new OutputStreamFactory(), new TransformationFactory());
        ApplicationUI ui = new ApplicationUI(controller);
        ui.setVisible(true);
    }
}
