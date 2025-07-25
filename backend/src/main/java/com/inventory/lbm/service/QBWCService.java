package com.inventory.lbm.service;

import org.springframework.stereotype.Service;

import com.inventory.lbm.entity.RequestEntity;

import generated.ArrayOfString;

@Service
public interface QBWCService {
    
    public ArrayOfString authenticate(String username, String password);

    public String getClientVersion(String version);

    public String closeConnection(String ticket);

    public String connectionError(String ticket, String hresult, String message);

    public String getLastError(String ticket);

    public int receiveResponseXML(String ticket, String receive_response, String hresult, String message) throws Exception;

    public String sendRequestXML(String ticket, String strHCPResponse, String strCompanyFilename, String qbXMLCountry, int qbXMLMajorVers, int qbXMLMinorVers);

    public String serverVersion();

    public void enqueue(RequestEntity request);

    public RequestEntity dequeue();
}
