package com.inventory.lbm.parser;

import java.util.UUID;

import com.inventory.lbm.entity.InventoryEntity;

public final class RequestParser {

    private RequestParser() {}

    public static String addToQBXML(UUID requestID, String name) {
        String qbxml = """
                <?xml version="1.0" encoding="utf-8"?>\r
                <?qbxml version="13.0"?>\r
                <QBXML>\r
                    <QBXMLMsgsRq onError="stopOnError">\r
                        <ItemInventoryAddRq requestID="%s">\r
                            <ItemInventoryAdd>\r
                                <Name> %s </Name>
                            </ItemInventoryAdd>\r
                        </ItemInventoryAddRq>\r
                    </QBXMLMsgsRq>\r
                </QBXML>
                """;

        return qbxml.formatted(requestID, name);
    }

    public static String updateQtyToQBXML(UUID requestID, String id, int quantityDifference) {
        String qbxml = """
                <?xml version="1.0" encoding="utf-8"?>\r
                <?qbxml version="13.0"?>\r
                <QBXML>\r
                    <QBXMLMsgsRq onError="stopOnError">\r
                        <InventoryAdjustmentAddRq requestID="%s">\r
                            <InventoryAdjustmentAdd defMacro="MACROTYPE">\r
                                <AccountRef>\r
                                </AccountRef>\r
                                <InventoryAdjustmentLineAdd>\r
                                    <ItemRef>\r
                                        <ListID> %s </ListID>\r
                                    </ItemRef>\r
                                    <QuantityAdjustment>\r
                                        <QuantityDifference> %d </QuantityDifference>\r
                                    </QuantityAdjustment>\r
                                </InventoryAdjustmentLineAdd>\r
                            </InventoryAdjustmentAdd>\r
                        </InventoryAdjustmentAddRq>\r
                    </QBXMLMsgsRq>\r
                        </QBXML>
                """;

        return qbxml.formatted(requestID, id, quantityDifference);    
    }

    public static String updateNameToQBXML(UUID requestID, String id, InventoryEntity update) {
        String qbxml = """
                <?xml version="1.0" encoding="utf-8"?>\r
                <?qbxml version="13.0"?>\r
                <QBXML>\r
                    <QBXMLMsgsRq onError="stopOnError">\r
                        <ItemInventoryModRq requestID="%s">\r
                            <ItemInventoryMod>\r
                                <ListID> %s </ListID>
                                <EditSequence> %s </EditSequence>
                                <Name> %s </Name>
                            </ItemInventoryMod>\r
                        </ItemInventoryModRq>\r
                    </QBXMLMsgsRq>\r
                </QBXML>
                """;
        
        String sequence = "";
        String name = update.getName();

        return qbxml.formatted(requestID, id, sequence, name);
    }

    public static String getallToQBXML(UUID requestID) {
        StringBuilder qbxml = new StringBuilder();
        qbxml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        qbxml.append("<?qbxml version=\"13.0\"?>\n");
        qbxml.append("<QBXML>\n");
        qbxml.append("\t<QBXMLMsgsRq onError=\"stopOnError\">\n");
        qbxml.append("\t\t<ItemInventoryQueryRq requestID=\"").append(requestID.toString()).append("\">\n");
        qbxml.append("\t\t</ItemInventoryQueryRq>\n");
        qbxml.append("\t</QBXMLMsgsRq>\n");
        qbxml.append("</QBXML>");
        return qbxml.toString();
    }

    public static String getVendorsToQBXML(UUID requestID) {
        StringBuilder qbxml = new StringBuilder();

        qbxml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        qbxml.append("<?qbxml version=\"13.0\"?>\n");
        qbxml.append("<QBXML>\n");
        qbxml.append("\t<QBXMLMsgsRq onError=\"continueOnError\">\n");
        qbxml.append("\t\t<VendorQueryRq requestID=\"").append(requestID.toString()).append("\">\n");
        qbxml.append("\t\t</VendorQueryRq>\n");
        qbxml.append("\t</QBXMLMsgsRq>\n");
        qbxml.append("</QBXML>\n");
        return qbxml.toString();
    }
}
