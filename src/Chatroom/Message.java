package Chatroom;

import java.io.Serializable;

public class Message implements Serializable {
    public String text = "";
    public String name = "John Doe";

    public Message(String text, String name) {
        this.text = text;
        this.name = name;
    }
}
