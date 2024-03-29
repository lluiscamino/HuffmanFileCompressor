package integrationtest;

import logic.controller.ApplicationController;
import logic.controller.Controller;
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
import logic.model.transformation.Transformation;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileCompressionTest {
    private static final String TEST_FILES_DIR_PATH = "src/integrationtest/resources/testfiles";
    private static final String COMPRESSED_FILES_DIR_PATH = "src/integrationtest/resources/testfiles/comp";
    private static final String DECOMPRESSED_FILES_DIR_PATH = "src/integrationtest/resources/testfiles/decomp";

    private Controller controller;
    private int totalTestFiles;
    private AtomicLong processedTestFiles, testFileSizesSum, compressedFileSizesSum;

    @BeforeEach
    void setUp() {
        Encoder encoder = new Encoder(new HuffmanTreeBuilder(), new BitEncodingMapBuilder(), new HuffmanTreeEncoder());
        Decoder decoder = new Decoder(new HuffmanTreeDecoder());
        controller = new ApplicationController(encoder, decoder, new FileFactory(), new InputStreamFactory(),
                new OutputStreamFactory(), new TransformationFactory());
        processedTestFiles = new AtomicLong();
        testFileSizesSum = new AtomicLong();
        compressedFileSizesSum = new AtomicLong();
    }

    @Test
    void testFileCompression() {
        File testFilesDirectory = new File(TEST_FILES_DIR_PATH);
        File[] testFiles = testFilesDirectory.listFiles();
        if (testFiles == null) {
            throw new RuntimeException(testFilesDirectory + " is not a directory!");
        }
        totalTestFiles = testFiles.length;
        Arrays.stream(testFiles).filter(File::isFile).forEach(this::testFileCompression);
        float compressionRatio = (float) testFileSizesSum.get() / compressedFileSizesSum.get();
        System.out.println(
                "Compression ratio: " + testFileSizesSum + "B/" + compressedFileSizesSum + "B = " + compressionRatio);
    }

    private void testFileCompression(File file) {
        File compressedFile = new File(
                COMPRESSED_FILES_DIR_PATH + "/" + file.getName() + controller.getCompressedFileExtension()),
                decompressedFile = new File(DECOMPRESSED_FILES_DIR_PATH + "/" + file.getName());
        try {
            Transformation compression = controller.compressFile(file, compressedFile);
            controller.decompressFile(compressedFile, decompressedFile);
            assertTrue(FileUtils.contentEquals(file, decompressedFile));
            testFileSizesSum.addAndGet(compression.inputFileSizeBytes());
            compressedFileSizesSum.addAndGet(compression.outputFileSizeBytes());
            long numProcessedFiles = processedTestFiles.incrementAndGet();
            System.out.println(
                    file.getName() + " OK! " + compression.fileSizeRatio() + " (" + numProcessedFiles + "/" + totalTestFiles + ")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
