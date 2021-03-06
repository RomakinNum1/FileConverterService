package ru.itdt.fileconvert.services.reader.extensions;

import ru.itdt.fileconvert.services.constructions.Building;
import ru.itdt.fileconvert.services.reader.FileRead;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class XmlRead implements FileRead {
    public List<Building> read(String path) throws IOException, XMLStreamException {
        List<Building> buildings = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            XMLEventReader reader = factory.createXMLEventReader(fileInputStream);
            XMLEvent xmlEvent = reader.nextEvent();

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
