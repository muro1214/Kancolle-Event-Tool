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
            final boolean isFastOnly) {
        Element eventArea = this.document.createElement("event");
        eventArea.setAttribute("name", areaName);
        eventArea.setAttribute("no", String.valueOf(no));

        Element fastOnly = this.document.createElement("fast-only");
        fastOnly.appendChild(this.document.createTextNode(isFastOnly ? "yes" : "no"));
        eventArea.appendChild(fastOnly);

        Element tag = this.document.createElement("tag");
        tag.appendChild(this.document.createTextNode(tagName));
        eventArea.appendChild(tag);

        this.document.appendChild(eventArea);

        return eventArea;
    }

    public Element buildFleetElement(final FleetType fleetType) {
        Element fleet = this.document.createElement("fleet");
        fleet.setAttribute("type", fleetType.typeName());

        return fleet;
    }

    public Element buildKanmusuElement(final int no, final Kanmusu kanmusu) {
        Element element = this.document.createElement("kanmusu");
        element.setAttribute("name", kanmusu.name());
        element.setAttribute("no", String.valueOf(no));

        Element id = this.document.createElement("id");
        id.appendChild(this.document.createTextNode(String.valueOf(kanmusu.id())));
        element.appendChild(id);

        Element level = this.document.createElement("level");
        level.appendChild(this.document.createTextNode(String.valueOf(kanmusu.level())));
        element.appendChild(level);

        Element type = this.document.createElement("type");
        type.appendChild(this.document.createTextNode(kanmusu.shipTypeString()));
        element.appendChild(type);

        Element speed = this.document.createElement("speed");
        speed.appendChild(this.document.createTextNode(kanmusu.speedString()));
        element.appendChild(speed);

        return element;
    }

    public Element buildFilterElement(final String level) {
        Element element = this.document.createElement("filter");
        Element element2 = this.document.createElement("level");
        element2.appendChild(this.document.createTextNode(level));
        element.appendChild(element2);

        return element;
    }
}
