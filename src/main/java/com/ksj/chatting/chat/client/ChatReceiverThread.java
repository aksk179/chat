package com.ksj.chatting.chat.client;

import com.ksj.chatting.chat.vo.ChatVO;

import java.io.*;
import java.net.Socket;

public class ChatReceiverThread extends Thread{
    
    Socket socket;
    
    public ChatReceiverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                ChatVO receivedVO = (ChatVO) in.readObject();
                if ("ENTER".equals(receivedVO.command)) {
                    System.out.println(receivedVO.userName + "님이 입장했습니다.");
                } else if ("CHAT".equals(receivedVO.command)){
                    System.out.println(receivedVO.userName + " : " + receivedVO.msg);
                } else if ("EXIT".equals(receivedVO.command)){
                    System.out.println(receivedVO.userName + "님이 퇴장했습니다.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
