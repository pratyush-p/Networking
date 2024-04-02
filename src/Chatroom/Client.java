package Chatroom;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
  
    public static void main(String[] args) {
        try {

            String name;
            while (true) {
                System.err.println("What is your chat username: ");
                name = new Scanner(System.in).nextLine();
                boolean contains = false;
                for (String thisName : FrameData.nameList) {
                    if (thisName.equals(name)) {
                        contains = true;
                    }
                }
                if (!contains) {
                    System.err.println("Connected!");
                    break;
                } else {
                    System.err.println("That name is already taken.");
                }
            }

            // create an object for the TTT game
            // create a connection to server
            Socket socket = new Socket("127.0.0.1",8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            FrameData frame = new FrameData(name, os);

            // determine if playing as X or O

            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is,os,frame, name);
            Thread t = new Thread(cl);
            t.start();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
