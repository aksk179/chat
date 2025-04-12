package com.ksj.chatting.chat.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        String ip = args[0];
        String port = args[1];
        int nPort = Integer.parseInt(port);

        Socket socket = new Socket(ip, nPort);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String outMsg = br.readLine();

            if (outMsg.length() > 0) {
                outMsg += "\n";
                System.out.println("outMsg : " + outMsg);
                out.write(outMsg.getBytes(StandardCharsets.UTF_8));
                out.flush();
                System.out.println("전송완료");
            }
        }

    }
}
