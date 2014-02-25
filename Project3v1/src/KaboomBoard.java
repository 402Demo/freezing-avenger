import java.util.Observable;

/**
 * KaboomBoard represents the board state of the classic game "Kaboom", 
 * similar to the game Minesweeper.
 * @author Ryan Chang
 * @version 2013.2.21
 */
public class KaboomBoard extends Observable
{
    private int[][] grid;
    private Object[][] board;
    private boolean[][] exposed;
    private int numBombs;
    private int curBoardSize;
    private static final int kBomb = 1;
    private static final int kNoBomb = 0;
    private static final int kDoNothing = 0;
    private static final int kIncrementFlag = 1;
    private static final int kDecrementFlag = -1;


    /**
     * Constructs a new game of Collapse
     * @param boardNum board number to play
     * @param boardSize size of board
     * @param boardDifficulty difficulty factor for bomb placement
     */
    public KaboomBoard(int boardNum, int boardSize, int boardDifficulty)
    {
        curBoardSize = boardSize;

        grid = new int[boardSize][boardSize];
        board = new Object[boardSize][boardSize];
        exposed = new boolean[boardSize][boardSize];
        generateGrid(boardDifficulty, boardSize, boardNum);
        initializeBoard();
    }

    /**
     * Sets flag piece on the board
     * @param row row to set flag
     * @param col col to set flag
     * @return factor to increase or decrease flag count by
     */
    public int flag(int row, int col)
    {
        int response = kDoNothing;
        
        // If tile is hidden, flag it
        if (board[row][col].equals("-"))
        {
            board[row][col] = "@";
            response = kIncrementFlag;
        }
        // If tile is already flagged, unflag it
        else if (board[row][col].equals("@"))
        {
            board[row][col] = "-";
            response = kDecrementFlag;
        }
        
        return response;
    }

    /**
     * Removes all tiles and places 2 green tiles in the upper left corner
     */
    public void cheat()
    {
        // Iterate through all rows
        for (int row = 0; row < grid.length; row++)
        {
            // Iterate through all columns
            for (int col = 0; col < grid[0].length; col++)
            {
                // Clear the board
                grid[row][col] = kNoBomb;       
            }
        }
        grid[0][0] = kBomb;
        grid[0][1] = kNoBomb;

        // Iterate through all rows
        for (int row = 0; row < grid.length; row++)
        {
            // Iterate through all columns
            for (int col = 0; col < grid[0].length; col++)
            {
                // Clear the board
                board[row][col] = " "; 
                exposed[row][col] = true;
            }
        }
        board[0][0] = "-";
        exposed[0][0] = false;
        board[0][1] = "-";
        exposed[0][1] = false;
    }

    /**
     * Gets the current grid
     * @return the grid
     */
    public Object[][] getBoard()
    {
        return board;
    }
    
    /**
     * Attempts to make a move in the specified row and column
     * @param row row to make a move
     * @param col column to make a move
     * @return game is lost
     */
    public boolean move(int row, int col)
    {
        boolean gameLost = false;

        // Check if bomb is underneath tile
        if (grid[row][col] == kBomb)
        {
            revealBoard();
            board[row][col] = "*";
            gameLost = true;

        }
        // If no bomb, then reveal the tile
        else
        {
            revealTile(row, col);
        }
        
        setChanged();
        notifyObservers();

        return gameLost;
    }

    /**
     * Checks if the game has been won
     * @return if the game is won
     */
    public boolean getGameWon()
    {
        boolean won = true;
        
        // Iterate through all rows
        for (int row = 0; row < curBoardSize; row++)
        {
            // Iterate through all columns
            for (int col = 0; col < curBoardSize; col++)
            {
                // If there are covered tiles that do not have bombs, the game is not won
                if (grid[row][col] != kBomb && !exposed[row][col])
                {
                    won = false;
                }
            }
        }
        
        return won;
    }

    /**
     * Getter for number of bombs placed on the board
     * @return number of bombs on the board
     */
    public int getNumBombs()
    {
        return numBombs;
    }

    /**
     * Uncovers all hidden tiles
     */
    public void revealBoard()
    {
        // Iterate over all rows
        for (int row = 0; row < curBoardSize; row++)
        {
            // Iterate over all columns
            for (int col = 0; col < curBoardSize; col++)
            {
                // If the tile does not have a bomb and is not exposed, expose it
                if (grid[row][col] != kBomb && !exposed[row][col])
                {
                    exposed[row][col] = true;
                    revealTile(row, col);
                }
                // All remaining unexposed tiles are bombs
                else if (!exposed[row][col])
                {
                    board[row][col] = "B";
                }
            }
        }
    }
    
    private int checkNumBombsCardinalDirections(int row, int col)
    {
        int curNumBombs = 0;
        
        // Check if right is a bomb
        if (col < curBoardSize - 1 && grid[row][col + 1] == kBomb)
        {
            curNumBombs++;
        }
        // Check if left is a bomb
        if (col > 0 && grid[row][col - 1] == kBomb)
        {
            curNumBombs++;
        }
        // Check if down is a bomb
        if (row < curBoardSize - 1 && grid[row + 1][col] == kBomb)
        {
            curNumBombs++;
        }
        // Check if up is a bomb
        if (row > 0 && grid[row - 1][col] == kBomb)
        {
            curNumBombs++;
        }
        
        return curNumBombs;
    }
    
    private int checkNumBombsDiagonalUp(int row, int col)
    {
        int curNumBombs = 0;
        
        // Check if up-right is a bomb
        if (row > 0 && col < curBoardSize - 1 && grid[row - 1][col + 1] == kBomb)
        {
            curNumBombs++;
        }
        // Check if up-left is a bomb
        if (row > 0 && col > 0 && grid[row - 1][col - 1] == kBomb)
        {
            curNumBombs++;
        }

        return curNumBombs;
    }
    
    private int checkNumBombsDiagonalDown(int row, int col)
    {
        int curNumBombs = 0;
        
        // Check if down-right is a bomb
        if (row < curBoardSize - 1 && col < curBoardSize - 1 && grid[row + 1][col + 1] 
                == kBomb)
        {
            curNumBombs++;
        }
        // Check if down-left is a bomb
        if (row < curBoardSize - 1 && col > 0 && grid[row + 1][col - 1] == kBomb)
        {
            curNumBombs++;
        }

        return curNumBombs;
    }

    private int checkNumBombs(int row, int col)
    {
        int curNumBombs = 0;

        curNumBombs += checkNumBombsCardinalDirections(row, col);
        curNumBombs += checkNumBombsDiagonalUp(row, col);
        curNumBombs += checkNumBombsDiagonalDown(row, col);
        
        return curNumBombs;

    }
    
    private void revealCardinalDirections(int row, int col)
    {
        // Check if right is exposed
        if (col < curBoardSize - 1 && !exposed[row][col + 1])
        {
            revealTile(row, col + 1);
        }
        // Check if left is exposed
        if (col > 0 && !exposed[row][col - 1])
        {
            revealTile(row, col - 1);
        }
        // Check if down is exposed
        if (row < curBoardSize - 1 && !exposed[row + 1][col])
        {
            revealTile(row + 1, col);
        }
        // Check if up is exposed
        if (row > 0 && !exposed[row - 1][col])
        {
            revealTile(row - 1, col);
        }
    }

    private void revealUpDiagonalDirections(int row, int col)
    {
        // Check if up-right is exposed
        if (row > 0 && col < curBoardSize - 1 && !exposed[row - 1][col + 1])
        {
            revealTile(row - 1, col + 1);
        }
        // Check if up-left is exposed
        if (row > 0 && col > 0 && !exposed[row - 1][col - 1])
        {
            revealTile(row - 1, col - 1);
        }
    }
    
    private void revealDownDiagonalDirections(int row, int col)
    {
        // Check if down-right is exposed
        if (row < curBoardSize - 1 && col < curBoardSize - 1 && 
            !exposed[row + 1][col + 1])
        {
            revealTile(row + 1, col + 1);
        }
        // Check if down-left is exposed
        if (row < curBoardSize - 1 && col > 0 && !exposed[row + 1][col - 1])
        {
            revealTile(row + 1, col - 1);
        }
    }
    
    private void revealTile(int row, int col)
    {
        int curNumBombs = checkNumBombs(row, col);

        exposed[row][col] = true;

        // If surrounding tiles contain no bombs, explore the other tiles
        if (curNumBombs == 0)
        {
            revealCardinalDirections(row, col);
            revealUpDiagonalDirections(row, col);
            revealDownDiagonalDirections(row, col);
        }

        // Print number of bombs on tile, or blank if no bombs
        if (curNumBombs > 0)
        {
            board[row][col] = curNumBombs + "";
        }
        else
        {
            board[row][col] = " ";
        }
    }

    private void initializeBoard()
    {
        // Iterate over all rows
        for (int row = 0; row < curBoardSize; row++)
        {
            // Iterate over all columns
            for (int col = 0; col < curBoardSize; col++)
            {
                board[row][col] = "-";
                exposed[row][col] = false;
            }
        }
    }
    
    private void generateGrid(int boardDifficulty, int boardSize, int boardNum)
    {
        java.util.Random generator = new java.util.Random(boardNum);
        int curNumBombs, origNumBombs, curRow, curCol;

        // Initialize board with no bombs and iterate over all rows
        for (int row = 0; row < boardSize; row++)
        {
            // Iterate over all columns
            for (int col = 0; col < boardSize; col++)
            {
                grid[row][col] = kNoBomb;
            }
        }
        // Set number of bombs according to algorithm
        curNumBombs = (int) (Math.pow(boardSize, 2) / boardDifficulty);
        origNumBombs = curNumBombs;

        // Place bombs on game board according to algorithm
        for (int bombs = 0; bombs < origNumBombs; bombs++)
        {
            curRow = generator.nextInt(boardSize);
            curCol = generator.nextInt(boardSize);
            // If current cell already has a bomb, decrease bomb count
            if (grid[curRow][curCol] == kBomb)
            {
                curNumBombs--;
            }
            else
            {
                grid[curRow][curCol] = kBomb;
            }
        }
        
        numBombs = curNumBombs;
    }
}
