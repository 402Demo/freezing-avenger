import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

/**
 * KaboomModel represents the game logic outside of the board itself.
 * @author Ryan Chang
 * @version 2013.2.21
 */
public class KaboomModel extends AbstractTableModel 
{
    /**
     * Icons used only for GUI version
     */
    //private ImageIcon[] icons;
    private static final long serialVersionUID = 2643823883721257416L;
    private String[] columnNames;
    private Object[][] board;
    private KaboomBoard game;
    private int boardSize;
    private int flags;
    private int moves;
    private int boardNum;
    private boolean gameLost;
    private Timer timer;
    private LinkedList<HighScore> highscores;
    private LinkedList<Preference> preferenceList;
    private Preference curBoardSize;
    private Preference curBoardDifficulty;
    private FileReader hsread;
    private FileWriter hswrite;
    private static final int kMaxName = 20;
    private static final int kMaxHighScores = 5;
    private static final int kBoardNumbers = 5000;
    private static final int kOneSecond = 1000;
    private static final int kSeconds = 60;
    private static final String kPrefsLoc = "kaboom/preferences.ini";
    private static final String kHofLoc = "kaboom/halloffame.ser";
    protected String timerLabel;
    protected int time;

    /**
     * Constructor generates a new model with an initial board number
     * @param boardNum boardNum to create the model with
     */
    public KaboomModel(int boardNum)
    {
        this.boardNum = boardNum;
        loadPreferences();
        game = new KaboomBoard(boardNum, curBoardSize.getValue(), 
                curBoardDifficulty.getValue());
        initializeModel();
        setColumns();
    }

    /**
     * Getter for KaboomBoard so observers can be added
     * @return the KaboomBoard
     */
    public KaboomBoard getKaboomBoard()
    {
        return game;
    }

    /**
     * Getter for the board
     * @return the board
     */
    public Object[][] getBoard()
    {
        return game.getBoard();
    }

    /**
     * Getter for the number of bombs on the board
     * @return number of bombs
     */
    public int getNumBombs()
    {
        return game.getNumBombs();
    }

    /**
     * Getter for number of columns
     * @return number of columns
     */
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /**
     * Getter for number of rows
     * @return number of rows
     */
    public int getRowCount()
    {
        return board.length;
    }

    /**
     * Getter for column names (swing)
     * @param col column to get name
     * @return column names
     */
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    /**
     * Getter for board
     * @param row row to get value
     * @param col col to get value
     * @return value at board
     */
    public Object getValueAt(int row, int col)
    {
        return board[row][col];
    }

    /**
     * Getter for board size
     * @return the board size
     */
    public int getBoardSize()
    {
        return boardSize;
    }

    /**
     * Getter for number of moves made
     * @return number of moves
     */
    public int getMoves()
    {
        return moves;
    }

    /**
     * Getter for number of flags placed on the board
     * @return number of flags on the board
     */
    public int getFlags()
    {
        return flags;
    }

    /**
     * Getter for the timer
     * @return formatted string representing the time
     */
    public String getTimer()
    {
        return timerLabel;
    }

    /**
     * Getter for whether the game has been lost
     * @return game is lost
     */
    public boolean getGameLost()
    {   
        return gameLost;
    }

    /**
     * Getter for whether the game has been won
     * @return game is won
     */
    public boolean getGameWon()
    {
        boolean gameWon;

        // If the game has not been lost already, then game may be won
        if (!gameLost)
        {
            gameWon = true;
        }
        else
        {
            gameWon = false;
        }

        gameWon = game.getGameWon();

        // Stop timer if game is won
        if (gameWon)
        {
            timer.stop();
        }

        return gameWon;
    }

    /**
     * Getter for all the high scores
     * @return list of high scores
     */
    public LinkedList<HighScore> getHighScores()
    {
        return highscores;
    }

    /**
     * Getter for all the preferences
     * @return list of preferences
     */
    public LinkedList<Preference> getPreferences()
    {
        return preferenceList;
    }

    /**
     * Getter for the current board number
     * @return the board number
     */
    public int getBoardNum()
    {
        return boardNum;
    }

    /**
     * Adds a new high score to the hall of fame if there is room
     * @param name Name to be added to hall of fame
     * @throws IOException if halloffame.ser cannot be read
     */
    public void addHighScore(String name) throws IOException
    {
        // If name is over kMaxName, shorten it
        if (name.length() > kMaxName)
        {
            name = name.substring(0, kMaxName);
        }
        else
        {
            // Add trailing spaces to match instructor output
            for (int length = name.length(); length < kMaxName; length++)
            {
                name += " ";
            }
        }
        // If less than kMaxHighScores, add immediately
        if (highscores.size() < kMaxHighScores)
        {
            highscores.add(new HighScore(name, time));
        }
        else
        {
            // Kick off the lowest numbered person if new score is greater
            if (highscores.get(kMaxHighScores - 1).getTime() > time)
            {
                highscores.remove(kMaxHighScores - 1);
                highscores.add(new HighScore(name, time));
            }
            // Kick off the lowest person lexicographically if new score is greater
            else if (highscores.get(kMaxHighScores - 1).getTime() == time && 
                    highscores.get(kMaxHighScores - 1).getName().compareTo(name) == -1)
            {
                highscores.remove(kMaxHighScores - 1);
                highscores.add(new HighScore(name, time));
            }
        }

        Collections.sort(highscores, new Comparator<HighScore>() 
        {
            public int compare(HighScore arg0, HighScore arg1) 
            {
                // Check if arg0 score is greater than arg1's
                if(arg0.getTime() > arg1.getTime())
                {
                    return 1;
                }
                // Check if arg0 score is less than arg1's
                else if (arg0.getTime() < arg1.getTime())
                {
                    return -1;
                }
                else
                {
                    return arg0.getName().compareTo(arg1.getName());
                }
            }   
        });

        writeHighScores();
    }

    /**
     * Sets the user's preference choices
     * @param p1 first preference choice
     * @param p2 second preference choice
     */
    public void setPreference(Preference p1, Preference p2)
    {
        // Check if p1 is for Board Size
        if (p1.getSection().equals("Board Size"))
        {
            curBoardSize = p1;
        }
        // Check if p1 is for Difficulty
        else if (p1.getSection().equals("Difficulty"))
        {
            curBoardDifficulty = p1;
        }
        //Check if p2 is for Board Size
        if (p2.getSection().equals("Board Size"))
        {
            curBoardSize = p2;
        }
        // CHeck if p2 is for Difficulty
        else if (p2.getSection().equals("Difficulty"))
        {
            curBoardDifficulty = p2;
        }

        timer.stop();
        game = new KaboomBoard(boardNum, curBoardSize.getValue(), 
                curBoardDifficulty.getValue());
        initializeModel();
    }

    /**
     * Commands the board to move to specified row and column
     * @param row to make move
     * @param col to make move
     */
    public void move(int row, int col)
    {
        moves++;

        // If board reports game over, stop timer
        if (game.move(row, col))
        {
            gameLost = true;
            timer.stop();
        }
    }

    /**
     * Commands board to place or remove a flag
     * @param row to place or remove flag
     * @param col to place or remove flag
     */
    public void flag(int row, int col)
    {
        flags += game.flag(row, col);
    }

    /**
     * Commands the board to cheat
     */
    public void cheat()
    {
        game.cheat();
    }

    /**
     * Unhides all tiles
     */
    public void peek()
    {
        game.revealBoard();
    }

    /**
     * Restarts board with current board number
     */
    public void restart()
    {
        timer.stop();
        game = new KaboomBoard(boardNum, curBoardSize.getValue(), 
                curBoardDifficulty.getValue());
        initializeModel();
    }

    /**
     * Starts new game board number incremented by one from current board number
     */
    public void newGame()
    {
        // If we're at max board, roll over to 1, otherwise increment
        if (boardNum == kBoardNumbers)
        {
            boardNum = 1;
        }
        else
        {
            boardNum++;
        }

        timer.stop();
        game = new KaboomBoard(boardNum, curBoardSize.getValue(), 
                curBoardDifficulty.getValue());
        initializeModel();
    }

    /**
     * Starts new game with user selected board number
     * @param curBoardNum board number to start new game
     */
    public void selectGame(int curBoardNum)
    {
        boardNum = curBoardNum;
        timer.stop();
        game = new KaboomBoard(boardNum, curBoardSize.getValue(), 
                curBoardDifficulty.getValue());
        initializeModel();
    }

    /**
     * Deletes the hall of fame file
     * @return String representing whether file was able to be deleted
     */
    public String deleteHallOfFame()
    {
        File hof = new File(kHofLoc);
        String result;
        
        // Set deletion success message
        if (hof.delete())
        {
            result = kHofLoc + " deleted.";
        }
        else
        {
            result = kHofLoc + " could not be deleted.";
        }

        return result;

    }

    private void initializeModel()
    {
        try 
        {
            loadHighScores();
        } 
        catch (IOException e) 
        {
            System.out.println("COULD NOT LOAD HIGH SCORES");
        }
        boardSize = curBoardSize.getValue();
        board = game.getBoard();
        flags = 0;
        moves = 0;
        gameLost = false;

        //setColumns();
        //loadImages();
        //paintBoard();
        time = 0;
        timerLabel = "0:00";
        timer = new Timer(kOneSecond, new TimerAction());
        timer.start();
    }

    private void writeHighScores() throws IOException
    {
        hswrite = new FileWriter(kHofLoc);
        // Write all the high scores to file
        for (int numScores = 0; numScores < highscores.size(); numScores++)
        {

            hswrite.write(highscores.get(numScores).getName() + "=" + 
                    highscores.get(numScores).getTime() + "\n");
        }
        hswrite.close();
    }

    private void loadHighScores() throws IOException
    {
        Scanner reader;
        String token;
        String[] result;

        highscores = new LinkedList<HighScore>();

        try 
        {
            hsread = new FileReader(kHofLoc);
        } 
        catch (FileNotFoundException e) 
        {
            try 
            {
                hswrite = new FileWriter(kHofLoc);
            } 
            catch (IOException e1) 
            {
                System.out.println("WARNING: HIGH SCORES FILE COULD NOT BE WRITTEN!");
                hswrite = null;
                System.exit(1);
            }
            hsread = new FileReader(kHofLoc);
            hswrite.close();
        }

        reader = new Scanner(hsread);
        // Read in all high scores from file
        while (reader.hasNext())
        {
            token = reader.nextLine();
            result = token.split("=");
            highscores.add(new HighScore(result[0], Integer.parseInt(result[1])));
        }
        reader.close();
        hsread.close();
    }

    private void loadPreferences()
    {
        Wini preferences;

        try 
        {
            preferences = new Wini(new File(kPrefsLoc));
        } 
        catch (InvalidFileFormatException e) 
        {
            System.out.println("WARNING 1: PREFERENCES FILE COULD NOT BE READ IN");
            preferences = null;
        } 
        catch (IOException e) 
        {
            System.out.println("WARNING 2: PREFERENCES FILE COULD NOT BE READ IN");
            preferences = null;
        }

        Set<String> sectionNames = preferences.keySet();
        preferenceList = new LinkedList<Preference>();
        // Iterate over all ini sections
        for (int section = 0; section < sectionNames.size(); section++)
        {
            Map<String, String> map = preferences.get(sectionNames.toArray()[section]);
            // Iterate over all ini keys
            for (int key = 0; key < map.size(); key++)
            {
                preferenceList.add(new Preference((String)sectionNames.toArray()[section]
                        , map.keySet().toArray()[key].toString(), 
                        Integer.parseInt(
                                map.get(map.keySet().toArray()[key].toString()))));
            }
        }
        curBoardSize = null;
        int index = 0;
        // Set board size to default in ini
        while (curBoardSize == null)
        {
            // Set first occurrence of Board Size to curBoardSize
            if (preferenceList.get(index).getSection().equals("Board Size"))
            {
                curBoardSize = preferenceList.get(index);
            }
            index++;
        }

        curBoardDifficulty = null;
        index = 0;
        // Set difficulty to default in ini
        while (curBoardDifficulty == null)
        {
            // Set first occurrence of Difficulty to curBoardDifficulty
            if (preferenceList.get(index).getSection().equals("Difficulty"))
            {
                curBoardDifficulty = preferenceList.get(index);
            }
            index++;
        }
    }

    private void setColumns()
    {
        columnNames = new String[boardSize];
        // Iterate over all columns to set name to blank
        for (int size = 0 ; size < boardSize; size++)
        {
            columnNames[size] = "";
        }
    }
    
    /**
     * Swing Leftover
    private void loadImages()
    {
        // Any images will be loaded here
        icons = new ImageIcon[3];
        icons[0] = new ImageIcon("src/green.jpg");
        icons[1] = new ImageIcon("src/red.jpg"); //purple
        icons[2] = new ImageIcon("src/yellow.jpg"); //cyan
    }
     */


    /**
     * TimerAction represents the action to be performed when a timer is fired
     */
    public class TimerAction implements ActionListener
    {
        private NumberFormat nm = NumberFormat.getInstance();

        /**
         * Increments the time when fired
         * @param evt event that is fired
         */
        public void actionPerformed(ActionEvent evt)
        {
            nm.setMinimumIntegerDigits(2);

            time++;
            timerLabel = time / kSeconds + ":" + nm.format(time % kSeconds);
        }   
    }


    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    //  @Override
    //  public Class getColumnClass(int c)
    //  {
    //      Class block = Object.class;
    //      
    //      for (int blocks = 0; blocks < boardSize; blocks++)
    //      {
    //          if (getValueAt(blocks, c) != null)
    //          {
    //              block = getValueAt(blocks, c).getClass();
    //          }
    //      }
    //      return String.class;
    //  }   
}
