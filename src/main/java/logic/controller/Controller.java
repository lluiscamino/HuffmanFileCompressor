package logic.controller;

import java.io.File;
import java.io.IOException;

public interface Controller {
    String getCompressedFileExtension();

    void selectFile(File file);

    void compressFile() throws IOException;

    void compressFile(File inputFile, File outputFile) throws IOException;

    void decompressFile() throws IOException;

    void decompressFile(File inputFile, File outputFile) throws IOException;
}
