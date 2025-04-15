package com.ksj.chatting.chat.server;

import com.ksj.chatting.chat.vo.ChatVO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatThread extends Thread {

    Socket socket;

    static List<ObjectOutputStream> list = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
    ObjectOutputStream out = null;
    ObjectInputStream in = null;

    public ChatThread(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
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
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                ChatVO receivedVO = (ChatVO) in.readObject();

                sendAll(receivedVO);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAll(ChatVO chatVO) {
        for (ObjectOutputStream out : list) {
            try {
                out.writeObject(chatVO);
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if ("EXIT".equalsIgnoreCase(chatVO.command)) {
            try {
                if (socket != null) socket.close();
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
