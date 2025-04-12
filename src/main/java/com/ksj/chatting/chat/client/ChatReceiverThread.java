package com.ksj.chatting.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatReceiverThread extends Thread{
    
    Socket socket;
    
    public ChatReceiverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();

            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String inMsg = br.readLine();

                System.out.println("inMsg : " + inMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
