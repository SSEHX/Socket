package com.paobe.FileOption;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by xuqi on 2017/2/4.
 */
public class FileInputSocket {


    private JProgressBar jProgressBar = null;
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private File file = null;
    private String filePath = "/Users/xuqi/Desktop/file_recv/";
    private FileOutputStream fileOutputStream = null;
    private byte[] readData = new byte[1024];
    private String fileName = null;
    private int dataLenght = 0;
    private long fileLenght = 0;
    private long recvDataLenght = 0;


    private JLabel fileNameLabel = null;
    private JLabel fileSizeLabel = null;
    private JLabel fileTypeLabel = null;

    private JLabel completed = null;


    public FileInputSocket(Socket socket) {
        this.socket = socket;
    }

    //接收文件
    public void recvFile(){


        //线程安全传输文件更新进度条
        new SwingWorker<Integer,Integer>(){
            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());

                    //获取文件名
                    fileName = dataInputStream.readUTF();
                    System.out.println("文件名：  "+fileName);

                    if (fileNameLabel != null) {
                        fileNameLabel.setText(fileName);
                    }

                    file = new File(filePath + fileName);
                    fileOutputStream = new FileOutputStream(file);
                    fileLenght = Long.parseLong(dataInputStream.readUTF());

                    if (fileSizeLabel != null){
                        fileSizeLabel.setText(String.valueOf(fileLenght));
                    }

//                    if (fileTypeLabel != null) {
//                        fileTypeLabel.setText(file.getName().split(".")[1]);
//                    }

                    System.out.println(fileLenght);

                    //从socke流获取文件
                    int oldvalue = -1;
                    while ((dataLenght = dataInputStream.read(readData)) != -1){
                        fileOutputStream.write(readData,0,dataLenght);

                        //计算接收到的总字节数 并更新到进度条
                        recvDataLenght = recvDataLenght+dataLenght;
                        int value = (int)(((float)recvDataLenght/(float)fileLenght)*100);
                        if (value != oldvalue){
                            oldvalue = value;
                            publish(value);
                        }
                    }
//                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                if (jProgressBar != null) {
                    for (Integer value : chunks) {
                        jProgressBar.setValue(value);
                        completed.setText(value+"%");
                    }
                }
                super.process(chunks);
            }
        }.execute();
    }
    public void setjProgressBar(JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }

    public void setFileNameLabel(JLabel fileNameLabel) {
        this.fileNameLabel = fileNameLabel;
    }

    public void setFileSizeLabel(JLabel fileSizeLabel) {
        this.fileSizeLabel = fileSizeLabel;
    }

    public void setFileTypeLabel(JLabel fileTypeLabel) {
        this.fileTypeLabel = fileTypeLabel;
    }

    public void setCompleted(JLabel completed) {
        this.completed = completed;
    }
}

