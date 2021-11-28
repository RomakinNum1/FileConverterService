package ru.itdt.fileconvert.writer;

import ru.itdt.fileconvert.writer.extensions.JsonWriter;
import ru.itdt.fileconvert.writer.extensions.XmlWriter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public final class WriteFactory {
    public FileWriter saveFile(String path) throws IOException {
        switch (FilenameUtils.getExtension(path)) {
            case "xml" -> {
                return new XmlWriter(path);
            }
            case "json" -> {
                return new JsonWriter(path);
            }
            default -> throw new IOException("Данное расширение файла не может быть обработано");
        }
    }
}
