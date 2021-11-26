package Open;

import Constructions.Building;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface OpenFile {
    List<Building> getData();
}
