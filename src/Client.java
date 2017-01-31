import com.sun.corba.se.impl.orbutil.ObjectWriter;
import gnu.io.SerialPort;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Client{

    private String serverIp = null;
    private int serverPort = -1;
    private Socket socket = null;
    private boolean isConnect = false;

    public Client(String serverIp, int serverPort, ClientWindow clientWindow) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        try {
            clientWindow.showConnecting();
            socket = new Socket(serverIp, serverPort);
            isConnect = true;
        } catch (ConnectException e1) {
            clientWindow.showConnectError();
        }catch (IOException e) {
        }
        if (isConnect) {
            System.out.println("连接成功");
            clientWindow.closeWindow();
            new Thread(new Send(socket)).start();
            try {
                new Thread(new Recv(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


//发送线程
class Send implements Runnable{
    private Socket socket = null;
    private Data data = null;
    private ObjectOutputStream objectOutputStream = null;
//    private BufferedReader bufferedReader;

    public Send(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        System.out.println("发送线程启动");
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//            while (true) {
//                data = new Data(bufferedReader.readLine());
//                objectOutputStream.writeObject(data);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//接收线程
class Recv implements Runnable{

    private Socket socket = null;
    private String data = null;
    private BufferedReader bufferedReader = null;
    private ObjectInputStream objectInputStream = null;

    public Recv(Socket socket) throws IOException{
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        System.out.println("接收线程启动");
        try {

            while (true){
                System.out.println("正在接收对象");

                Object a = objectInputStream.readObject();
                Data b = (Data)a;
                System.out.println(b.getData());
            }
        } catch (EOFException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

//数据类
class Data implements Serializable{

    private String data = null;

    public Data(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
