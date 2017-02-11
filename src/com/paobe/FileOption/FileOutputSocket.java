package com.paobe.FileOption;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileOutputSocket {
    private Socket socket = null;
    private DataOutputStream dataOutputStream = null;
    private File file = null;
    private FileInputStream fileInputStream = null;
    private byte[] sendData = new byte[1024];
    private int dataLenght = 0;
    private long fileLenght = 0;
    private long sendDataLenght = 0;

    public FileOutputSocket(Socket socket,File file) {
        this.socket = socket;
        this.file = file;
    }

    public boolean sendFile() {
        try {
            fileInputStream = new FileInputStream(file);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //发送文件名
            dataOutputStream.writeUTF(file.getName());
            System.out.println(file.getName());
            //获取文件大小
            fileLenght = file.length();
            System.out.println(fileLenght);
            dataOutputStream.writeUTF(String.valueOf(fileLenght));

            while ((dataLenght = fileInputStream.read(sendData)) != -1){
                dataOutputStream.write(sendData,0,dataLenght);
                sendDataLenght = sendDataLenght+dataLenght;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Socket socket = null;
        File file = null;
        try {
            socket = new Socket("192.168.1.113", 3333);
            file = new File("/Users/xuqi/Documents/Virtual Machines.localized/Ubuntu 64 位.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputSocket fileOutputSocket = new FileOutputSocket(socket, file);
        System.out.println(fileOutputSocket.sendFile());

    }
}