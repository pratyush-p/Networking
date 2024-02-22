package ConnectFour;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
 
            // Creating an object of ServerSocket class
            // in the main() method  for socket connection
            ServerSocket ss = new ServerSocket(3000);
 
            // Establishing a connection
            Socket redSocket = ss.accept();
            ObjectInputStream redInput
                = new ObjectInputStream(redSocket.getInputStream());
            ObjectOutputStream redOutput
                = new ObjectOutputStream(redSocket.getOutputStream());


            Socket blueSocket = ss.accept();
  
            // Lastly close the socket using standard close
            // method to release memory resources
            ss.close();
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
 
            // Display the exception on the console
            System.out.println(e);
        }
        
    }
}
