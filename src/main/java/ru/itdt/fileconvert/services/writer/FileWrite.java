package ru.itdt.fileconvert.services.writer;

import ru.itdt.fileconvert.services.constructions.Building;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface FileWrite {
    void write(List<Building> buildings, String path) throws IOException, ParserConfigurationException, TransformerException;
}
