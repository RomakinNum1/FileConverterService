package Save;

import Constructions.Building;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;

public interface SaveFile {
    void save(ArrayList<Building> buildings);
}
