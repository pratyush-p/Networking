package Chatroom;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<ServersListener> listeners = new ArrayList<>();

    public static void main(String[] args) {
        try
        {
            // creates a socket that allows connections on port 8001
            ServerSocket serverSocket = new ServerSocket(8001);

            while (true) {
                listeners.add(acceptSocket(serverSocket));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }

    private static ServersListener acceptSocket(ServerSocket serverSock) {
        try {
            Socket socket = serverSock.accept();
            ObjectOutputStream xos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream xis = new ObjectInputStream(socket.getInputStream());
            ServersListener list = new ServersListener(xis, xos);
            Thread t = new Thread(list);
            t.start();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void bigMessage(Message msg) {
        for (ServersListener s : listeners) {
            s.outputMessage(msg);
        }
    } 


}
