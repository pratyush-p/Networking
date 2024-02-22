import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public ServersListener(ObjectInputStream is, ObjectOutputStream os) {
        this.is = is;
        this.os = os;
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                // reads text from the client
                String text = (String) is.readObject();
                // writes the text back to the client
                os.writeObject(text);
                os.reset();
            }catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("Client Disconnected");
                break;
            }
        }
    }
}
