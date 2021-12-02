package ru.itdt.fileconvert.services.reader;

import ru.itdt.fileconvert.services.reader.extensions.JsonRead;
import ru.itdt.fileconvert.services.reader.extensions.XmlRead;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public final class ReadFactory {
    public FileRead getExtention(String path) throws IOException {
        switch (FilenameUtils.getExtension(path)) {
            case "xml" -> {
                return new XmlRead();
            }
            case "json" -> {
                return new JsonRead();
            }
            default -> throw new IOException("Данное расширение файла не может быть обработано");
        }
    }
}
