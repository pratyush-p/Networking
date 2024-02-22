package ConnectFour;
import java.io.Serializable;

public class Message implements Serializable {
    public int[][] board = {
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0}
    };

    public int code = 0;
    public boolean isRed = false;
    public String message = "";

    public Message(int[][] board, int code, boolean isRed, String message) {
        this.board = board;
        this.code = code;
        this.isRed = isRed;
        this.message = message;
    }

    //CODES:
    //0 - Standard Turn
    //1 - Winning Turn
    //2 - Person Left
    //3 - Want to restart
    //4 - Print statement
}
