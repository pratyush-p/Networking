package Chatroom;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientsListener implements Runnable {
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private FrameData frame = null;
    private String name = "";

    public ClientsListener(ObjectInputStream is,
                           ObjectOutputStream os,
                           FrameData frame,
                           String name) {
        this.is = is;
        this.os = os;
        this.frame = frame;
        this.name = name; 

    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                Message msg = (Message) is.readObject();
                frame.newMessage(msg, os);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
