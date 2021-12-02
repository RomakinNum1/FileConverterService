package ru.itdt.fileconvert.services.writer;

import ru.itdt.fileconvert.services.writer.extensions.JsonWrite;
import ru.itdt.fileconvert.services.writer.extensions.XmlWrite;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public final class WriteFactory {
    public FileWrite getExtention(String path) throws IOException {
        switch (FilenameUtils.getExtension(path)) {
            case "xml" -> {
                return new XmlWrite();
            }
            case "json" -> {
                return new JsonWrite();
            }
            default -> throw new IOException("Данное расширение файла не может быть обработано");
        }
    }
}
