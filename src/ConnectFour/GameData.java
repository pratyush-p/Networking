package ConnectFour;
public class GameData
{
    private char[][] grid = {
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '}
    };

    public char[][] getGrid()
    {
        return grid;
    }

    public void reset()
    {

        grid = new char[6][7];
        for(int r=0;r<grid.length; r++)
            for(int c=0; c<grid[0].length; c++)
                grid[r][c]=' ';
    }

   
    public boolean isCat()
    {
        boolean allFinished = true;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ' ') {
                    allFinished = false;
                }
            }
        }

        return allFinished && !isWinner('X') && !isWinner('O');
    }

    public boolean isWinner(char letter)
    {

        boolean ret = false;

        for (int j = 0; j < grid[0].length-3 ; j++ ){
            for (int i = 0; i < grid.length; i++){
                if (grid[i][j] == letter && grid[i][j+1] == letter && grid[i][j+2] == letter && grid[i][j+3] == letter){
                    ret = true;
                }           
            }
        }

        for (int i = 0; i < grid.length-3 ; i++ ){
            for (int j = 0; j < grid[0].length; j++){
                if (grid[i][j] == letter && grid[i+1][j] == letter && grid[i+2][j] == letter && grid[i+3][j] == letter){
                    ret = true;
                }           
            }
        }

        for (int i = 3; i < grid.length; i++){
            for (int j = 0; j < grid[0].length-3; j++){
                if (grid[i][j] == letter && grid[i-1][j+1] == letter && grid[i-2][j+2] == letter && grid[i-3][j+3] == letter)
                    ret = true;
            }
        }

        for (int i = 3; i < grid.length; i++){
            for (int j = 3; j < grid[0].length; j++){
                if (grid[i][j] == letter && grid[i-1][j-1] == letter && grid[i-2][j-2] == letter && grid[i-3][j-3] == letter)
                    ret = true;
            }
        }
        return ret;
    }

}