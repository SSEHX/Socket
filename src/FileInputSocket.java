import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xuqi on 2017/2/4.
 */
public class FileInputSocket {
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


    public FileInputSocket(Socket socket) {
        this.socket = socket;
    }

    //接收文件
    public boolean recvFile(){
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());

            //获取文件名
            fileName = dataInputStream.readUTF();
            System.out.println("文件名：  "+fileName);
            file = new File(filePath + fileName);
            fileOutputStream = new FileOutputStream(file);
            fileLenght = Long.parseLong(dataInputStream.readUTF());
            System.out.println(fileLenght);

            //从socke流获取文件
            while ((dataLenght = dataInputStream.read(readData)) != -1){
                fileOutputStream.write(readData);

                //计算接收到的总字节数
                recvDataLenght = recvDataLenght+dataLenght;
                System.out.println(""+(int)(recvDataLenght/fileLenght)*100);
            }
            fileOutputStream.flush();
            System.out.println("文件大小："+file.length());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3333);
        Socket socket = serverSocket.accept();
        FileInputSocket fileInputSocket = new FileInputSocket(socket);
        System.out.println(fileInputSocket.recvFile());
    }

}

