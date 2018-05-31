package edu.javacourse.grn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZagsSystem {
    public static void main(String[] args) {
        ZagsSystem zagsSystems = new ZagsSystem();
        zagsSystems.start();
    }

    private void start() {
        System.out.println("Zags-System started");
        try {
            ServerSocket ses = new ServerSocket(7779);
            while(true) {
                Socket socket = ses.accept();
                ZagsRequestHandler zh = new ZagsRequestHandler(socket);
                new Thread(zh).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
