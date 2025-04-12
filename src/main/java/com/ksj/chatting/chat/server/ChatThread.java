package com.ksj.chatting.chat.server;

import java.io.*;
import java.net.Socket;

public class ChatThread extends Thread {

    Socket socket;

    public ChatThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String connIp = socket.getInetAddress().getHostAddress();
            System.out.println("connIp에서 연결시도 : " + connIp);
            InputStream in = socket.getInputStream( );
            OutputStream out = socket.getOutputStream( );

            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String inMsg = br.readLine();

                System.out.println(inMsg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
