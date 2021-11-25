package Save.FileExtensions;

import Constructions.Building;
import Save.SaveFile;
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
import java.util.ArrayList;

public final class Xml implements SaveFile {
    String path;

    public Xml(String _path) {
        path = _path;
    }

    public void save(ArrayList<Building> arrayList) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = null;
        try {
            dbuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = dbuilder.newDocument();

        Element filmsMain = document.createElement("films");
        document.appendChild(filmsMain);
        for (Building b : arrayList) {
            for (String film : b.films) {
                Element filmChild = document.createElement("film");
                filmsMain.appendChild(filmChild);

                Element filmName = document.createElement("name");
                filmName.appendChild(document.createTextNode(film));
                filmChild.appendChild(filmName);

                Element nameBuilb = document.createElement("building");
                nameBuilb.appendChild(document.createTextNode(b.name));
                filmChild.appendChild(nameBuilb);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
