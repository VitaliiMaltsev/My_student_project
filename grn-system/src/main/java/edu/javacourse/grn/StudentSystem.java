package edu.javacourse.grn;

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

public class StudentSystem {
    public static void main(String[] args) {
        StudentSystem ss = new StudentSystem();
        ss.start();
    }

    private void start() {
        System.out.println("Student-System started");
        try {
            ServerSocket ses = new ServerSocket(7778);
            while(true) {
                Socket socket = ses.accept();
                StudentRequestHandler sr = new StudentRequestHandler(socket);
                new Thread(sr).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


