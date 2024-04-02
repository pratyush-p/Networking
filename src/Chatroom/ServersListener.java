package Chatroom;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    // static data that is shared between both listeners
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    public ServersListener(ObjectInputStream is, ObjectOutputStream os) {
        this.is = is;
        this.os = os;
        outs.add(os);
    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                Message curMessage = (Message) is.readObject();
                Server.bigMessage(curMessage);
                if (curMessage.text.equals("")) {
                    System.err.println(curMessage.name + " has connected!");
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void outputMessage(Message m) {
        try {
            os.writeObject(m);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
