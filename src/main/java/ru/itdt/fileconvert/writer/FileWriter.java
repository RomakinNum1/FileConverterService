package ru.itdt.fileconvert.writer;

import ru.itdt.fileconvert.constructions.Building;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface FileWriter {
    void save(List<Building> buildings) throws IOException, ParserConfigurationException, TransformerException;
}
