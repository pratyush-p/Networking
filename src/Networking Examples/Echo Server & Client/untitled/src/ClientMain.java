import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain
{
    public static void main(String[] args)
    {
        try
        {
            // connects to the server on the given IPAddress and port
            Socket socket = new Socket("127.0.0.1",8000);

            // creates a stream for writing objects to the server
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            // creates a stream for reading objects from the server
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

            String text = "";
            Scanner keyboard = new Scanner(System.in);
            do {
                System.out.print("Enter text to send to the server (\"Exit\" to Quit): ");
                text = keyboard.next();


                if(!text.equals("exit"))
                {
                    // write the given text to the server
                    os.writeObject(text);
                    os.reset();
                    // reads the text back from the server
                    String echo = (String) is.readObject();
                    System.out.println("\t echo: " + echo);
                }
            }while(!text.equals("exit"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
