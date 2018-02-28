package kancolle.gui.xml;

import java.nio.file.Path;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import kancolle.fleet.FleetType;
import kancolle.structure.Kanmusu;

public class EventBuilder {
    final Document document;

    public EventBuilder() {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Logger.getGlobal().severe(e.toString());
        }

        this.document = documentBuilder.newDocument();
    }

    public boolean write(final Path path) {
        Transformer transformer = null;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            Logger.getGlobal().severe(e.toString());
            return false;
        }

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");

        try {
            transformer.transform(new DOMSource(this.document), new StreamResult(path.toFile()));
        } catch (TransformerException e) {
            Logger.getGlobal().severe(e.toString());
            return false;
        }

        return true;
    }

    public Element setEventAreaInfo(final int no, final String areaName, final String tagName,
            final FleetType fleetType, final boolean isFastOnly) {
        Element eventArea = this.document.createElement("event");
        eventArea.setAttribute("no", String.valueOf(no));
        eventArea.setAttribute("area", areaName);
        eventArea.setAttribute("tag", tagName);
        eventArea.setAttribute("fleet-type", fleetType.typeName());
        eventArea.setAttribute("fast-only", isFastOnly ? "yes" : "no");

        this.document.appendChild(eventArea);

        return eventArea;
    }

    public Element buildKanmusuElement(final int no, final Kanmusu kanmusu) {
        Element element = this.document.createElement("kanmusu");
        element.setAttribute("no", String.valueOf(no));
        element.setAttribute("id", String.valueOf(kanmusu.id()));
        element.setAttribute("ship-type", kanmusu.shipTypeString());
        element.setAttribute("level", String.valueOf(kanmusu.level()));
        element.setAttribute("speed", kanmusu.speedString());
        element.appendChild(this.document.createTextNode(kanmusu.name()));

        return element;
    }

    public Element buildFilterElement(final String level) {
        Element element = this.document.createElement("filter");
        element.setAttribute("name", "level");
        element.appendChild(this.document.createTextNode(level));

        return element;
    }
}
