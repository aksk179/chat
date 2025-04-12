package com.ksj.chatting.chat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        String port = args[0];
        int nPort = Integer.parseInt(port);

        ServerSocket serverSocket = new ServerSocket(nPort);

        while (true) {
            System.out.println("대기중...");
            Socket socket = serverSocket.accept();

            //쓰레드 시작
            ChatThread thread = new ChatThread(socket);
            thread.start();
        }
    }
}
