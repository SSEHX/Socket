package com.paobe.FileOption;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by xuqi on 2017/2/5.
 */
public class FileOption {
    private JPanel panel1;
    private JProgressBar progressBar1;
    private JButton stopButton;
    private JLabel fileName;
    private JLabel fileSize;
    private JLabel fileType;
    private JLabel completed;
    private JLabel serverState;
    private JLabel ip;
    private JLabel port;

    public FileOption() {
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startFileServer();
            }
        });
    }

    public static void main(String[] args){
        //创建一个线程安全的界面
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new JFrame("test");
                new SetWindowCenter(jFrame);
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jFrame.setContentPane(new FileOption().panel1);
            }
        });

    }

    public void startFileServer() {
        new SwingWorker<String,String>(){

            @Override
            protected String doInBackground() throws Exception {
                ServerSocket serverSocket = null;
                Socket socket = null;
                try {
                    publish("Listening");
                    serverSocket = new ServerSocket(3333);
                    socket = serverSocket.accept();
                    publish("Connect Success");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ip.setText(socket.getInetAddress().getHostAddress());
                port.setText(String.valueOf(socket.getPort()));
                FileInputSocket fileInputSocket = new FileInputSocket(socket);
                fileInputSocket.setjProgressBar(progressBar1);
                fileInputSocket.setFileNameLabel(fileName);
                fileInputSocket.setFileSizeLabel(fileSize);
                fileInputSocket.setFileTypeLabel(fileType);
                fileInputSocket.setCompleted(completed);
                fileInputSocket.recvFile();
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String state : chunks){
                    if (state == "Listening"){
                        serverState.setText("Listening");
                    }else if (state == "Connect Success"){
                        serverState.setText("Connect Success");
                    }
                }
                super.process(chunks);
            }
        }.execute();

    }
}
