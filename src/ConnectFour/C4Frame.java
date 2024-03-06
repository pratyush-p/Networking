package ConnectFour;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class C4Frame extends JFrame implements MouseListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server
    ObjectOutputStream os;

    public C4Frame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("Connect 4 Game");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        // adds a KeyListener to the Frame
        addMouseListener(this);

        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message
        if(player == 'r')
            text = "Waiting for Blue to Connect";

        setSize(900,700);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        // draws the background
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString(text,20,55);

        // draws the tic-tac-toe grid lines to the screen
        g.setColor(Color.BLACK);

        for (int y = 0; y <= 6; y++) {
            for (int x = 0; x <= 7; x++) {
                g.drawOval((x) * (90) + 60, (y) * (90) + 60, 80, 80);
            }
        }

        // draws the player moves to the screen
        for(int r=0; r<gameData.getGrid().length; r++) {
            for(int c=0; c<gameData.getGrid()[0].length; c++) {
                if (gameData.getGrid()[r][c] != ' ') {
                    g.setColor(gameData.getGrid()[r][c] == 'X' ? Color.RED : Color.BLUE);
                    g.fillOval((c) * (90) + 60, (r) * (90) + 60, 80, 80);    
                }
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(String turn) {
        if(turn=="Red" && player=='X')
            text = "Your turn";
        else
        {
            text = turn+"'s turn.";
        }
        repaint();
    }

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
        int x = e.getX();
        int r = -1;
        int c;

        if (x >= 60 && x < 150) {
            c = 0;
        } else if (x >= 150 && x < 240) {
            c = 1;
        } else if (x >= 240 && x < 330) {
            c = 2;
        } else if (x >= 330 && x < 420) {
            c = 3;
        } else if (x >= 420 && x < 510) {
            c = 4;
        } else if (x >= 510 && x < 600) {
            c = 5;
        } else if (x >= 600 && x < 690) {
            c = 6;
        } else {
            c = -1;
        }

        if (c != -1) {

            for (int i = gameData.getGrid().length - 1; i >= 0; i--) {
                if (gameData.getGrid()[i][c] == ' ') {
                    r = i;
                }
            }
            if (r != -1) {
                try {
                    os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + c + r + player));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }    
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

}