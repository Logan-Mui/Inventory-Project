package com.inventory.lbm.controller.qbwc;

import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.AUTHENTICATE_REQEUST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.CLIENTVERSION_REQUEST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.CLOSECONNECTION_REQUEST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.CONNECTIONERROR_REQUEST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.GETLASTERROR_REQUEST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.QB_MESSAGES_NAMESPACE;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.RECEIVERESPONSEXML_REQUEST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.SENDREQUESTXML_REQUEST;
import static com.inventory.lbm.controller.qbwc.QBWCServiceConstants.SERVERVERSION_REQUEST;
import com.inventory.lbm.service.QBWCService;
import com.inventory.lbm.service.impl.qbwc.QBWCServiceImpl;

import generated.ArrayOfString;
import generated.Authenticate;
import generated.AuthenticateResponse;
import generated.ClientVersion;
import generated.ClientVersionResponse;
import generated.CloseConnection;
import generated.CloseConnectionResponse;
import generated.ConnectionError;
import generated.ConnectionErrorResponse;
import generated.GetLastError;
import generated.GetLastErrorResponse;
import generated.ReceiveResponseXML;
import generated.ReceiveResponseXMLResponse;
import generated.SendRequestXML;
import generated.SendRequestXMLResponse;
import generated.ServerVersion;
import generated.ServerVersionResponse;

@Endpoint
public class QBWCEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(QBWCEndpoint.class);

    private final QBWCService qbwcService;

    public QBWCEndpoint(QBWCServiceImpl qbwcService) {
        this.qbwcService = qbwcService;
    }

    @PayloadRoot(localPart = AUTHENTICATE_REQEUST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public AuthenticateResponse authenticate(@RequestPayload Authenticate request) throws DatatypeConfigurationException {

        logger.info("QBE - Recieved authenticate request with username '" + request.getStrUserName() + ".");
        
        ArrayOfString result = qbwcService.authenticate(request.getStrUserName(), request.getStrPassword());

        //this can return a string array with up to four strings
            //session token, which can be anything really
            //nvu for invalid, none if no work is needed, the pathname of the company file for the update, or an empty string to use the current company open
            //seconds to wait for the next update (optional)
            //number of seconds to be used as minruneverynseconds (optional)
        AuthenticateResponse response = new AuthenticateResponse();

        response.setAuthenticateResult(result);

        return response;
    }

    @PayloadRoot(localPart = CLIENTVERSION_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public ClientVersionResponse clientVersion(@RequestPayload ClientVersion request) throws DatatypeConfigurationException {

        
        logger.info("QBE - Recieved clientVersion request.");
        
        String result = qbwcService.getClientVersion(request.getStrVersion());
        
        //this can return with an empty string, "O:"", "E:"".
        ClientVersionResponse response = new ClientVersionResponse();

        response.setClientVersionResult(result);

        return response;
    }

    @PayloadRoot(localPart = CLOSECONNECTION_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public CloseConnectionResponse closeConnection(@RequestPayload CloseConnection request) {
        
        logger.info("QBE - Recieved close connection request.");

        String result = qbwcService.closeConnection(request.getTicket());

        CloseConnectionResponse response = new CloseConnectionResponse();

        response.setCloseConnectionResult(result);
        
        return response;
    }

    @PayloadRoot(localPart = CONNECTIONERROR_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public ConnectionErrorResponse connectionError(@RequestPayload ConnectionError request) {
            
        logger.info("QBE - Recieved connection error request.");

        String result = qbwcService.connectionError(request.getTicket(), request.getHresult(), request.getMessage());

        ConnectionErrorResponse response = new ConnectionErrorResponse();

        response.setConnectionErrorResult(result);
        
        return response;
    }

    @PayloadRoot(localPart = GETLASTERROR_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetLastErrorResponse getLastError(@RequestPayload GetLastError request) {
        
        logger.info("QBE - Recieved get last error request.");

        String result = qbwcService.getLastError(request.getTicket());

        GetLastErrorResponse response = new GetLastErrorResponse();

        response.setGetLastErrorResult(result);
        
        return response;
    }

    @PayloadRoot(localPart = RECEIVERESPONSEXML_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public ReceiveResponseXMLResponse receiveResponseXML(@RequestPayload ReceiveResponseXML request) throws Exception {
            
        logger.info("QBE - Recieved recieve responseXML request.");

        int result = qbwcService.receiveResponseXML(request.getTicket(), request.getResponse(), request.getHresult(), request.getMessage());

        ReceiveResponseXMLResponse response = new ReceiveResponseXMLResponse();

        response.setReceiveResponseXMLResult(result);
        
        return response;
    }

    @PayloadRoot(localPart = SENDREQUESTXML_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public SendRequestXMLResponse sendRequestXML(@RequestPayload SendRequestXML request) {
        
        logger.info("QBE - Recieved get send requestXML request.");
        
        SendRequestXMLResponse response = new SendRequestXMLResponse();

        String result = qbwcService.sendRequestXML(request.getTicket(), request.getStrHCPResponse(), request.getStrCompanyFileName(), request.getQbXMLCountry(), request.getQbXMLMajorVers(), request.getQbXMLMinorVers());

        response.setSendRequestXMLResult(result);

        return response;
    }

    @PayloadRoot(localPart = SERVERVERSION_REQUEST, namespace = QB_MESSAGES_NAMESPACE)
    @ResponsePayload
    public ServerVersionResponse serverVersion(@RequestPayload ServerVersion request) {

        logger.info("QBE - Recieved server version request.");

        ServerVersionResponse response = new ServerVersionResponse();

        String result = qbwcService.serverVersion();

        response.setServerVersionResult(result);

        return response;
    }
}