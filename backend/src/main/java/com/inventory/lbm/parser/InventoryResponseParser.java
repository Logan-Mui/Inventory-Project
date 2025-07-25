package com.inventory.lbm.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.inventory.lbm.entity.InventoryEntity;
import com.inventory.lbm.entity.VendorEntity;
import com.inventory.lbm.model.InventoryResponse;
import com.inventory.lbm.service.VendorStoreService;

@Component
public final class InventoryResponseParser {

    private final VendorStoreService vendorStoreService;

    public InventoryResponseParser(VendorStoreService vendorStoreService) {
        this.vendorStoreService = vendorStoreService;
    }

    private static String getText(Element parent, String tagName) {
    Node item = parent.getElementsByTagName(tagName).item(0);
    return item != null ? item.getTextContent() : null;
    }


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

    public InventoryResponse fromQBXML(String xml) throws Exception {
        Document doc = loadXml(xml);
        
        Element root = doc.getDocumentElement();

        NodeList children = root.getElementsByTagName("QBXMLMsgsRs").item(0).getChildNodes();

        for(int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();

                switch (nodeName) {
                    case "InventoryAdjustmentAddRs" -> {
                        return parseInventoryAdjustmentAdd((Element) node);
                    }
                    case "ItemInventoryQueryRs" -> {
                        return parseItemInventoryQuery((Element) node);
                    }
                    case "ItemInventoryAddRs" -> {
                        return parseItemInventoryAdd((Element) node);
                    }
                    default -> throw new IllegalArgumentException("Unknown QBXML response type:" + nodeName);
                }
            }
        }
        throw new IllegalArgumentException("No recognizable QBXML response found");
    }

    private InventoryResponse parseInventoryAdjustmentAdd(Element root) {
        List<InventoryEntity> itemList= new ArrayList<>();
        Element line = (Element) root.getElementsByTagName("InventoryAdjustmentRet").item(0);
        UUID responseID = UUID.fromString(root.getAttribute("requestID"));

        String listID = getText(line,"ListID");
        String name = getText(line,"Name"); 
        int quantityDifference = Integer.parseInt(getText(line,"QuantityDifference"));
        String editSequence = getText(line,"EditSequence");
        String vendorId = getText(((Element) line.getElementsByTagName("PrefVendorRef").item(0)),"ListID");

        Optional<VendorEntity> optionalVendorRef = vendorStoreService.getVendor(vendorId);

                optionalVendorRef.ifPresent(vendorRef -> {
                    InventoryEntity queryItem = new InventoryEntity(listID, name, quantityDifference, vendorRef, editSequence);
                    itemList.add(queryItem);
                });

        InventoryResponse response = new InventoryResponse(responseID, itemList);

        return response;
    }

    private InventoryResponse parseItemInventoryQuery(Element root) {
        UUID responseID = UUID.fromString(root.getAttribute("requestID"));
        List<InventoryEntity> itemList = new ArrayList<>();
        NodeList items = root.getElementsByTagName("ItemInventoryRet");
            for (int i = 0; i < items.getLength(); i++) {
                Element line = (Element) items.item(i);
                String listID = getText(line,"ListID");
                String name = getText(line, "Name");
                int quantity = Integer.parseInt(getText(line,"QuantityOnHand"));
                String editSequence = getText(line, "EditSequence");
                String vendorId = getText(((Element) line.getElementsByTagName("PrefVendorRef").item(0)),"ListID");

                Optional<VendorEntity> optionalVendorRef = vendorStoreService.getVendor(vendorId);

                optionalVendorRef.ifPresent(vendorRef -> {
                    InventoryEntity queryItem = new InventoryEntity(listID, name, quantity, vendorRef, editSequence);
                    itemList.add(queryItem);
                });
            }

        InventoryResponse response = new InventoryResponse(responseID, itemList);

        return response;
    }

    private InventoryResponse parseItemInventoryAdd(Element root) {
        List<InventoryEntity> itemList = new ArrayList<>();
        Element line = (Element) root.getElementsByTagName("ItemInventoryRet").item(0);
        UUID responseID = UUID.fromString(root.getAttribute("requestID"));

        
        
        
        
        String listID = getText(line,"ListID");
        String name = getText(line,"Name");
        String editSequence = getText(line,"EditSequence");
        int quantity = 0;
        String vendorId = getText(((Element) line.getElementsByTagName("PrefVendorRef").item(0)),"ListID");

        Optional<VendorEntity> optionalVendorRef = vendorStoreService.getVendor(vendorId);
            optionalVendorRef.ifPresent(vendorRef -> {
            InventoryEntity queryItem = new InventoryEntity(listID, name, quantity, vendorRef, editSequence);
            itemList.add(queryItem);
        });

        InventoryResponse response = new InventoryResponse(responseID, itemList);

        return response;
    }
}
