import com.google.gson.stream.JsonWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xembly.ImpossibleModificationException;
import org.xembly.SyntaxException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, XMLStreamException, XMLParseException, ParseException, ImpossibleModificationException, SyntaxException, TransformerException {
        if (args[0].substring(args[0].lastIndexOf('.')).equals(".xml") && args[1].substring(args[1].lastIndexOf('.')).equals(".json")) {
            ArrayList<Building> result = openXml(args[0]);
            saveJson(args[1], result);
        }
        else if(args[0].substring(args[0].lastIndexOf('.')).equals(".json") && args[1].substring(args[1].lastIndexOf('.')).equals(".xml"))
        {
            ArrayList<Building> result = openJson(args[0]);
            saveXml(args[1], result);
        }
        System.out.println("Файл " + args[1] + " создан");
    }

    private static void saveXml(String path, ArrayList<Building> arrayList) throws ParserConfigurationException, SyntaxException, ImpossibleModificationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();

        // 2. Create a Document from the above DocumentBuilder.
        Document document = dbuilder.newDocument();

        // 3. Create the elements you want using the Element class and its appendChild method.

        // root element
        Element users = document.createElement("buildings");
        document.appendChild(users);
        for (Building b : arrayList) {
            // child element
            Element user = document.createElement("building");
            users.appendChild(user);

            Element nameBuild = document.createElement("name");
            nameBuild.appendChild(document.createTextNode(b.name));
            user.appendChild(nameBuild);

            Element films = document.createElement("films");
            for (String film : b.films) {
                Element filmchild = document.createElement("film");
                filmchild.appendChild(document.createTextNode(film));
                films.appendChild(filmchild);
            }
            user.appendChild(films);
        }

        // 4. Create a new Transformer instance and a new DOMSource instance.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        // 5. Create a new StreamResult to the output stream you want to use.
        StreamResult result = new StreamResult(new File(path));
        // StreamResult result = new StreamResult(System.out); // to print on console

        // 6. Use transform method to write the DOM object to the output stream.
        transformer.transform(source, result);
    }

    static private ArrayList<Building> openJson(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonBuild = (JSONArray) (((JSONObject) parser.parse(new FileReader(path))).get("buildings"));
        ArrayList<Building> buildings = new ArrayList<>();

        for (Object obj : jsonBuild) {
            JSONObject jsonObject = (JSONObject) ((JSONObject) obj).get("building");
            Building building = new Building();
            building.name = ((String) jsonObject.get("name"));
            building.films.addAll(getFilms(jsonObject));

            buildings.add(building);
        }

        return buildings;
    }

    static private ArrayList<String> getFilms(JSONObject jsonGenre) {
        JSONArray jsonFilms = (JSONArray) jsonGenre.get("films");
        ArrayList<String> musicBands = new ArrayList<>();

        for (Object film : jsonFilms) {
            JSONObject jsonObject = (JSONObject) film;

            musicBands.add((String) jsonObject.get("film"));
        }

        return musicBands;
    }

    private static void saveJson(String path, ArrayList<Building> result) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(new FileWriter(path));
        jsonWriter.beginObject();
        jsonWriter.name("buildings");
        jsonWriter.beginArray();
        for (Building b : result) {
            jsonWriter.beginObject();
            jsonWriter.name("building");

            jsonWriter.beginObject();
            jsonWriter.name("name");
            jsonWriter.value(b.name);

            jsonWriter.name("films");
            jsonWriter.beginArray();

            for (String film : b.films) {
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

    private static ArrayList<Building> openXml(String path) throws IOException, XMLStreamException, XMLParseException {
        ArrayList<Building> buildings = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(path));
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

                if (tagName.equals("name")) {
                    building.name = (reader.nextEvent().asCharacters().getData());
                } else if (tagName.equals("films")) {
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
