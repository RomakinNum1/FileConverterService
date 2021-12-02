package ru.itdt.fileconvert.services.reader;

import ru.itdt.fileconvert.services.constructions.Building;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface FileRead {
    List<Building> read(String path) throws IOException, XMLStreamException, ParseException;
}
