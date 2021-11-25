package Open.FileExtensions;

import Constructions.Building;
import Open.OpenFile;
import org.json.simple.JSONArray;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Xml implements OpenFile {
    String path;

    public Xml(String _path) {
        path = _path;
    }

    public List<Building> open() {
        List<Building> buildings = new ArrayList<>();

        XMLEventReader reader = readFile();

        XMLEvent xmlEvent;
        try {
            xmlEvent = reader.nextEvent();

            if (reader.hasNext()) {
                xmlEvent = reader.nextEvent();
            }

            while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("buildings")) {
                xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String tagName = startElement.getName().getLocalPart();

                    if (tagName.equals("building"))
                        buildings.add(getBuilding(reader));
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    private XMLEventReader readFile() {
        XMLEventReader reader = null;

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            reader = factory.createXMLEventReader(fileInputStream);
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

        return reader;
    }

    private Building getBuilding(XMLEventReader reader) {
        Building building = new Building();

        XMLEvent xmlEvent;
        try {
            do {
                xmlEvent = reader.nextEvent();


                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String tagName = startElement.getName().getLocalPart();

                    if (tagName.equals("name")) {
                        building.name = (reader.nextEvent().asCharacters().getData());
                    } else if (tagName.equals("films")) {
                        building.films.addAll(getFilms(reader));
                    }
                }

            }
            while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("building"));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return building;
    }

    private ArrayList<String> getFilms(XMLEventReader reader) {
        ArrayList<String> films = new ArrayList<>();

        XMLEvent xmlEvent;
        try {
            do {
                xmlEvent = reader.nextEvent();

                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String tagName = startElement.getName().getLocalPart();

                    if (tagName.equals("film")) {
                        films.add(reader.nextEvent().asCharacters().getData());
                    }
                }
            }
            while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("films"));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return films;
    }
}
