package edu.javacourse.grn;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * Created by antonsaburov on 27.03.17.
 */
public class GrnSystem
{
    public static void main(String[] args) {
        GrnSystem gm = new GrnSystem();
        gm.start();
    }

    private void start() {
        System.out.println("GrnSystem started");
        try {
            ServerSocket ses = new ServerSocket(7777);
            while(true) {
                Socket socket = ses.accept();
                GRNRequestHandler rh = new GRNRequestHandler(socket);
                new Thread(rh).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

