package com.ksj.chatting.chat.client;

import com.ksj.chatting.chat.server.ChatThread;
import com.ksj.chatting.chat.vo.ChatVO;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        String ip = args[0];
        String port = args[1];
        String userName = args[2];
        int nPort = Integer.parseInt(port);

        Socket socket = new Socket(ip, nPort);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        //쓰레드 시작
        ChatReceiverThread thread = new ChatReceiverThread(socket);
        thread.start();

        ChatVO chatVO = new ChatVO();
        chatVO.userName = userName;
        chatVO.command = "ENTER";

        out.writeObject(chatVO);
        out.flush();

        BufferedReader br = null;

        boolean flag = true;
        while (flag) {
            br = new BufferedReader(new InputStreamReader(System.in));
            String outMsg = br.readLine();

            if (outMsg.length() > 0) {
                if ("EXIT".equalsIgnoreCase(outMsg)) {
                    chatVO = new ChatVO();
                    chatVO.userName = userName;
                    chatVO.command = "EXIT";
                    flag = false;
                } else {
                    chatVO = new ChatVO();
                    chatVO.userName = userName;
                    chatVO.msg = outMsg;
                    chatVO.command = "CHAT";
                }

                out.writeObject(chatVO);
                out.flush();
            }
        }

        if (br != null) br.close();
        if (socket != null) socket.close();
        System.out.println("종료되었습니다.");
    }
}
