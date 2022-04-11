package logic.controller;

import java.io.File;

public class FileFactory {
    public File createFile(String path) {
        return new File(path);
    }
}
