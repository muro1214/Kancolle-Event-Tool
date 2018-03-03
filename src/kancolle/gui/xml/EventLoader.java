package kancolle.gui.xml;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kancolle.fleet.FleetType;
import kancolle.fleet.Fleets;
import kancolle.structure.Kanmusu;

public class EventLoader {
    final Element root;

    public EventLoader(final String uri) {
    	System.out.println(uri);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse("file:///" + uri);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger.getGlobal().severe(e.toString());
        }

        this.root = document.getDocumentElement();
    }

    public String getEventName() {
        return this.root.getAttribute("name");
    }

    public boolean isFastOnly() {
        return getTargetElement(this.root, "fast-only").getTextContent().equals("yes");
    }

    public String getTag() {
        return getTargetElement(this.root, "tag").getTextContent();
    }

    public String getFilterLevel() {
        return getTargetElement(getTargetElement(this.root, "filter"), "level").getTextContent();
    }

    public FleetType getFleetType() {
        return FleetType.getType(getTargetElement(this.root, "fleet").getAttribute("type"));
    }

    public Kanmusu getKanmusu(final int no) {
        NodeList kanmusus = getTargetElement(this.root, "fleet").getChildNodes();
        for (int i = 0; i < kanmusus.getLength(); i++) {
            Node rootChild = kanmusus.item(i);
            if (rootChild.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element element = (Element) rootChild;
            if (!element.getNodeName().equals("kanmusu")) {
                continue;
            }
            if (!element.getAttribute("no").equals(String.valueOf(no))) {
                continue;
            }
            String id = getTargetElement(element, "id").getTextContent();
            return Fleets.getKanmusuFromId(Integer.parseInt(id));
        }
        return null;
    }

    private static Element getTargetElement(final Element parent, final String nodeName) {
        NodeList rootChildrens = parent.getChildNodes();
        for (int i = 0; i < rootChildrens.getLength(); i++) {
            Node rootChild = rootChildrens.item(i);
            if (rootChild.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element element = (Element) rootChild;
            if (element.getNodeName().equals(nodeName)) {
                return element;
            }
        }
        return null;
    }
}
