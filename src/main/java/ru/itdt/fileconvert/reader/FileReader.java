package ru.itdt.fileconvert.reader;

import ru.itdt.fileconvert.constructions.Building;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<Building> read() throws IOException, XMLStreamException, ParseException;
}
