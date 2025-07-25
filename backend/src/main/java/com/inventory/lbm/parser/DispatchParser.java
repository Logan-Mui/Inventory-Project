package com.inventory.lbm.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class DispatchParser {
    private static Document loadXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xml));
            Document document = builder.parse(inputSource);
            return document;
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            return null;
        }
    }


    public static UUID fromQBXML(String xml) throws Exception {
        Document doc = loadXml(xml);
        Element root = doc.getDocumentElement();

        NodeList children = root.getElementsByTagName("QBXMLMsgsRs").item(0).getChildNodes();

        for(int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                return parseUUID((Element) node);
            }
        }
        throw new IllegalArgumentException("No recognizable QBXML response found");
    }



    private static UUID parseUUID(Element root) {
        UUID responseID = UUID.fromString(root.getAttribute("requestID"));
        return responseID;
    }
}