package ru.itdt.fileconvert.reader.extensions;

import ru.itdt.fileconvert.constructions.Building;
import ru.itdt.fileconvert.reader.FileReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public record XmlReader(String path) implements FileReader {

    public List<Building> read() throws FileNotFoundException, XMLStreamException {
        List<Building> buildings = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader;

        FileInputStream fileInputStream = new FileInputStream(path);
        reader = factory.createXMLEventReader(fileInputStream);
        XMLEvent xmlEvent;

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

        return buildings;
    }

    private Building getBuilding(XMLEventReader reader) throws XMLStreamException {
        Building building = new Building();

        XMLEvent xmlEvent;
        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("name")) {
                    building.setName((reader.nextEvent().asCharacters().getData()));
                } else if (tagName.equals("films")) {
                    building.getFilms().addAll(getFilms(reader));
                }
            }

        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("building"));
        return building;
    }

    private ArrayList<String> getFilms(XMLEventReader reader) throws XMLStreamException {
        ArrayList<String> films = new ArrayList<>();

        XMLEvent xmlEvent;
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
        return films;
    }
}
