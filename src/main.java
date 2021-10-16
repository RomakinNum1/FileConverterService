import com.google.gson.stream.JsonWriter;
import org.xml.sax.SAXException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XMLStreamException, XMLParseException {
        ArrayList<Building>result = openXml("E:\\Работа\\Практика\\FileConverterService\\films.xml");
        saveJson("E:\\Работа\\Практика\\FileConverterService", result, "test.json");

    }

    private static void saveJson(String path, ArrayList<Building> result, String name) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(new FileWriter(path + "\\" + name));
        jsonWriter.beginObject();
        jsonWriter.name("buildings");
        jsonWriter.beginArray();
        for (Building b:result) {
            jsonWriter.beginObject();
            jsonWriter.name("building");

            jsonWriter.beginObject();
            jsonWriter.name("name");
            jsonWriter.value(b.name);

            jsonWriter.name("films");
            jsonWriter.beginArray();

            for (String film:b.films) {
                jsonWriter.beginObject();
                jsonWriter.name("film");
                jsonWriter.value(film);
                jsonWriter.endObject();
            }

            jsonWriter.endArray();

            jsonWriter.endObject();

            jsonWriter.endObject();
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
        jsonWriter.close();
    }
    /*jsonWriter.beginObject();
            jsonWriter.name("building");
            jsonWriter.beginObject();
            jsonWriter.name("name");
            jsonWriter.value(b.name);
            jsonWriter.endObject();
            jsonWriter.beginObject();
            jsonWriter.name("films");
            jsonWriter.beginArray();
            for (String film:b.films) {
        jsonWriter.name("film");
        jsonWriter.value(film);
    }
            jsonWriter.endObject();
            jsonWriter.endArray();
            jsonWriter.endObject();*/

    private static ArrayList<Building> openXml(String path) throws IOException, XMLStreamException, XMLParseException {
        ArrayList<Building> buildings = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(path));
        XMLEvent xmlEvent = reader.nextEvent();

        if (reader.hasNext()) {
            xmlEvent = reader.nextEvent();
        }

        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("buildings"))
        {
            xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("building"))
                    buildings.add(getBuilding(xmlEvent, reader));
            }
        }

        return buildings;
    }

    static private Building getBuilding(XMLEvent xmlEvent, XMLEventReader reader) throws XMLStreamException, XMLParseException {
        Building building = new Building();

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if(tagName.equals("name"))
                {
                    building.name = (reader.nextEvent().asCharacters().getData());
                }
                else if(tagName.equals("films"))
                {
                    building.films.addAll(getFilms(xmlEvent, reader));
                }
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("building"));

        return building;
    }

    static private ArrayList<String> getFilms(XMLEvent xmlEvent, XMLEventReader reader) throws XMLStreamException, XMLParseException {
        ArrayList<String> films = new ArrayList<>();

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
