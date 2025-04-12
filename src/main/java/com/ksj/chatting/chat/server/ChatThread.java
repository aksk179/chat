package com.ksj.chatting.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatThread extends Thread {

    Socket socket;

    static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

    public ChatThread(Socket socket) {
        this.socket = socket;
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            list.add(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

                System.out.println("inMsg : " + inMsg);
                sendAll(">>" + inMsg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAll(String s) {
        for (PrintWriter out : list) {
            out.println(s);
            out.flush();
        }
    }
}
