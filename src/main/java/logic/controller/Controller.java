package logic.controller;

import logic.model.transformation.Transformation;

import java.io.File;
import java.io.IOException;

public interface Controller {
    String getCompressedFileExtension();

    void selectFile(File file);

    Transformation compressFile() throws IOException;

    Transformation compressFile(File inputFile, File outputFile) throws IOException;

    Transformation decompressFile() throws IOException;

    Transformation decompressFile(File inputFile, File outputFile) throws IOException;
}
