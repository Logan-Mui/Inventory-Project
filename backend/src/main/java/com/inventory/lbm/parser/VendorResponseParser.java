package com.inventory.lbm.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.inventory.lbm.entity.VendorEntity;
import com.inventory.lbm.model.VendorResponse;

@Component
public final class VendorResponseParser {

        private String getText(Element parent, String tagName) {
        Node item = parent.getElementsByTagName(tagName).item(0);
        return item != null ? item.getTextContent() : null;
        }

        private Document loadXml(String xml) {
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

        public VendorResponse fromQBXML(String xml) throws Exception {
            Document doc = loadXml(xml);
            Element root = doc.getDocumentElement();

            NodeList children = root.getElementsByTagName("QBXMLMsgsRs").item(0).getChildNodes();

            for(int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeName = node.getNodeName();

                    switch (nodeName) {
                        case "VendorQueryRs" -> {
                            return parseVendorQuery((Element) node);
                        }
                        default -> throw new IllegalArgumentException("Unknown QBXML response type:" + nodeName);
                    }
                }
            }
            throw new IllegalArgumentException("No recognizable QBXML response found");
        }

        private VendorResponse parseVendorQuery(Element root) {
        List<VendorEntity> vendorList = new ArrayList<>();
        UUID responseId = UUID.fromString(root.getAttribute("requestID"));
        NodeList vendors = root.getElementsByTagName("VendorRet");
            for (int i = 0; i < vendors.getLength(); i++) {
                Element line = (Element) vendors.item(i);
                String listID = getText(line,"ListID");
                String fullName = getText(line, "Name");
                String companyName = getText(line, "CompanyName");
                String phone = getText(line, "Phone");
                Boolean isActive = Boolean.valueOf(getText(line,"IsActive"));
                
                VendorEntity queryItem = new VendorEntity(listID, fullName, companyName, phone, isActive);
                vendorList.add(queryItem);
            } 

            VendorResponse response = new VendorResponse(responseId, vendorList);
        
            return response;
    }
}
