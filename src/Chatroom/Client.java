package Chatroom;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {
  
    public static void main(String[] args) {
        try {
            // create an object for the TTT game
            GameData gameData = new GameData();

            // create a connection to server
            Socket socket = new Socket("127.0.0.1",8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            // determine if playing as X or O
            CommandFromServer cfs = (CommandFromServer) is.readObject();
            C4Frame frame;

            // Create the Frame based on which player the server says this client is
            if(cfs.getCommand() == CommandFromServer.CONNECTED_AS_X)
                frame = new C4Frame(gameData,os,'X');
            else
                frame = new C4Frame(gameData,os, 'O');

            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is,os,frame);
            Thread t = new Thread(cl);
            t.start();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
