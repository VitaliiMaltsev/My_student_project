package edu.javacourse.grn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class ZagsRequestHandler implements Runnable {

    private Socket socket;

    public ZagsRequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            processRequest(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(Socket socket) throws IOException {
        StringBuilder sb = new StringBuilder();
        Reader br = new InputStreamReader(socket.getInputStream());
        char[] request = new char[256];
        int count = br.read(request);
        boolean result = true;
        String message = "ZAGS ANSWER";
        while(count != -1) {
            sb.append(new String(request, 0, count));
            if(sb.toString().endsWith("</family>")) {
                message="ZAGS ANSWER FOR CHILD";
                break;
            }else if (sb.toString().endsWith("</marriage>")){
                message="ZAGS ANSWER FOR WEDDING";
                break;
            }
            count = br.read(request);
        }
        System.out.println(sb.toString());

        try {
            ZAGSPerson person = buildPerson(sb.toString());
            System.out.println(person);

            result = checkPerson(person);
        } catch(Exception ex) {
            ex.printStackTrace();
            result = false;
            message = "ZAGS-system ERROR";
        }

        OutputStream os = socket.getOutputStream();
        os.write(("ZAGS-system answer: "+"<?xml version=\"1.0\" ?><answer>" +
                "<result>" + result + "</result>" +
                "<message>" + message + "</message>" +
                "</answer>").getBytes());

        socket.close();

    }

    private boolean checkPerson(ZAGSPerson person) {
        return true;
    }

    private ZAGSPerson buildPerson(String s) throws Exception {
        byte[] buffer = s.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        ZAGSPerson person = new ZAGSPerson();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(bis);
        NodeList childNodes = doc.getFirstChild().getChildNodes();
        for(int i=0; i<childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if(node instanceof Element) {
                if ("surName".equals(node.getNodeName())) {
                    person.setSurName(node.getTextContent().trim());
                }
                if ("givenName".equals(node.getNodeName())) {
                    person.setGivenName(node.getTextContent().trim());
                }
                if ("patronymic".equals(node.getNodeName())) {
                    person.setPatronymic(node.getTextContent().trim());
                }
                if ("dateOfBirth".equals(node.getNodeName())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    person.setDateOfBirth(sdf.parse(node.getTextContent()));
                }
            }
        }

        return person;
    }


}
