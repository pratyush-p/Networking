import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Client {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Enter the IP address of server: ");
        String IP = inputScanner.nextLine();
        try {
            Socket mainSocket = new Socket(IP, 3000);
            ObjectOutputStream output = new ObjectOutputStream(mainSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(mainSocket.getInputStream());
            Message connectionMessage = (Message) input.readObject();
            System.out.println(connectionMessage.message);
            Thread.sleep(500);
            mainView();
            mainLoop(input, output);
            mainSocket.close();            
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void mainView() {
        JFrame mainFrame = new JFrame("Main Frame");
        mainFrame.setLayout(null);
        mainFrame.setBounds(0, 0, 700, 700);
        mainFrame.setBackground(Color.BLACK);

        JLabel mainLabel = new JLabel("");

        mainFrame.add(mainLabel);
        createWindow(mainFrame);
    }

    public static void createWindow(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // System.err.println("did thing");
                // writeToFile();
            }
        });
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void mainLoop(ObjectInputStream inputStream, ObjectOutputStream outputStream) {

    }
}
