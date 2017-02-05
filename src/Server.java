import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*

 */
public class Server{

    private int port = 3001;
    private ServerSocket serverSocket = null;
    private ArrayList<Socket> sockets  = new ArrayList<>();
    private Socket socket = null;

    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            socket = serverSocket.accept();
            System.out.println(socket);
            sockets.add(socket);
            System.out.println("第"+sockets.size()+"个-->"+socket.getInetAddress().getHostAddress()+">>>客户端已连接！");
            new Thread(new Handle(socket)).start();
        }
    }


    public static void main(String[] args) throws IOException {
        new Server();
    }

    //客户端处理程序
    class Handle implements Runnable{

        private Socket socket = null;
        private ObjectInputStream objectInputStream = null;
        private ObjectOutputStream objectOutputStream = null;
        private Data data = null;

        public Handle(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    data = (Data)objectInputStream.readObject();
                    for (Socket socket:sockets){
                        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(data);
                    }
                }
            } catch (EOFException e){
                sockets.remove(socket);
                System.out.println(socket.getInetAddress().getHostAddress()+"已下线");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}


