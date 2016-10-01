package com.Main;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Pablo on 1/10/2016.
 */
public class Nodo {
    private int NodoTel;
    private String NodoID;
    private int NodoBytes;
    private String NodoIP;
    private int NodoPort;

    public int getNodoTel() {
        return NodoTel;
    }

    public void setNodoTel(int nodoTel) {
        NodoTel = nodoTel;
    }

    public String getNodoID() {
        return NodoID;
    }

    public void setNodoID(String nodoID) {
        NodoID = nodoID;
    }

    public int getNodoBytes() {
        return NodoBytes;
    }

    public void setNodoBytes(int nodoBytes) {
        NodoBytes = nodoBytes;
    }

    public String getNodoIP() {
        return NodoIP;
    }

    public void setNodoIP(String nodoIP) {
        NodoIP = nodoIP;
    }

    public int getNodoPort() {
        return NodoPort;
    }

    public void setNodoPort(int nodoPort) {
        NodoPort = nodoPort;
    }

}
