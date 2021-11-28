package ru.itdt.fileconvert.reader;

import ru.itdt.fileconvert.reader.extensions.JsonReader;
import ru.itdt.fileconvert.reader.extensions.XmlReader;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public final class ReadFactory {
    public FileReader openFile(String path) throws IOException {
        switch (FilenameUtils.getExtension(path)) {
            case "xml" -> {
                return new XmlReader(path);
            }
            case "json" -> {
                return new JsonReader(path);
            }
            default -> throw new IOException("Данное расширение файла не может быть обработано");
        }
    }
}
