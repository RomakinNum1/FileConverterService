package ru.itdt.fileconvert.writer.extensions;

import ru.itdt.fileconvert.constructions.Building;
import ru.itdt.fileconvert.writer.FileWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public final class XmlWriter implements FileWriter {
    private final String path;

    public XmlWriter(String _path) {
        path = _path;
    }

    public void save(List<Building> buildings) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();

        Document document = dbuilder.newDocument();

        Element filmsMain = document.createElement("films");
        document.appendChild(filmsMain);
        for (Building building : buildings) {
            for (String film : building.getFilms()) {
                Element filmChild = document.createElement("film");
                filmsMain.appendChild(filmChild);

                Element filmName = document.createElement("name");
                filmName.appendChild(document.createTextNode(film));
                filmChild.appendChild(filmName);

                Element nameBuilb = document.createElement("building");
                nameBuilb.appendChild(document.createTextNode(building.getName()));
                filmChild.appendChild(nameBuilb);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;

        transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }
}
