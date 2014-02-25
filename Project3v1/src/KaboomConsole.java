import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

/**
 * KaboomConsole is the text-based console user interface to the Kaboom game.
 * The main() method starts the application running using System.in and writer.
 * KaboomConsole can also return the top five high scores from the Hall of Fame.
 * 
 * @author Ryan Chang
 * @version 2013.2.21
 */
public class KaboomConsole implements Observer
{
    private NumberFormat nm;
    private Scanner scanner;
    private PrintWriter writer;
    private KaboomModel game;
    private Object[][] board;
    private int curBoardNum;
    private boolean gameOver;
    private boolean flagMove;
    /** How many hall of fame entries to return */
    public static final int kHallSize = 5;
    private static final int kBoardNumbers = 5000;
    private static final int kClearHoF = 0;
    private static final int kRestartGame = 1;
    private static final int kNewGame = 2;
    private static final int kSelectGame = 3;
    private static final int kScores = 4;
    private static final int kPeek = 5;
    private static final int kCheat = 6;
    private static final int kAbout = 7;
    private static final int kQuit = 8;
    private static final int kPrefs = 9;
    private static final int kSeparatorFactor = 3;
    private static final int kDoubleDigitScore = 10;

    /** Entry point for the application.
     *
     *  @param args ignored     
     */
    public static void main(String[] args)
    {   
        KaboomConsole app = new KaboomConsole(); 
        app.setIOsources(new InputStreamReader(System.in), 
                new OutputStreamWriter(System.out));
        app.run();
    }

    /** Construct a new instance of this class.
     * Create the game components and initialize them,
     * create the user interface and connect it to the game.
     */
    public KaboomConsole()
    {
        curBoardNum = 0;
        nm = NumberFormat.getInstance();
        nm.setMaximumIntegerDigits(1);
    }

    /** Set input/output sources for Stream-based user interfaces.
     * @param rdr A Reader from which user input is obtained.
     * @param wtr A Writer to which program output is displayed.
     */
    public void setIOsources(Reader rdr, Writer wtr) 
    {
        scanner = new Scanner(rdr);
        writer = new PrintWriter(wtr, true);
    }

    /** Run the console user interface, using the i/o streams provided by
     *  setIOsources();
     *  @pre setIOsources() has been called.
     */
    public void run()
    {
        boolean quit = false;
        int menu;
        String command;

        firstRun();

        // Switch for console input
        while (!quit)
        {
            // Check if input stream is open
            if (scanner.hasNext())
            {
                command = scanner.next();
            }
            else
            {
                command = Integer.toString(kQuit);
            }
            // If integer menu command, otherwise board move
            if (isInteger(command))
            {
                menu = Integer.parseInt(command);
                // If quit selected, quit
                if (processMenu(menu))
                {
                    quit = true;
                }
            }
            else 
            {
                // If game is not over and is a valid move, issue move command
                if (checkInput(command))
                {
                    // If move was designated as a flag, flag instead of move
                    if (flagMove)
                    {
                        flag(command);
                    }
                    else
                    {
                        move(command);
                    }
                }
            }
        }
    }
    
    /**
     * Getter for GameModel for unit testing
     * @return the game model
     */
    protected KaboomModel getModel()
    {
        return game;
    }
    
    private boolean processMenu(int menu)
    {
        boolean quit = false;
        
        // Switch on integer menu command
        switch (menu)
        {
            case kClearHoF:
                writer.println(game.deleteHallOfFame());
                break;
            case kRestartGame:
                restartGame();
                break;
            case kNewGame:
                newGame();
                break;
            case kSelectGame:
                selectGame();
                break;
            case kScores:
                writer.println(getHighScores());
                break;
            case kPeek:
                game.peek();
                printBoard();
                printMenu();
                break;
            case kCheat:
                game.cheat();
                printBoard();
                printMenu();
                break;
            case kAbout:
                writer.println("Kaboom by Ryan Chang");
                break;
            case kQuit:
                quit = true;
                break;
            case kPrefs:
                changePreferences();
                printBoard();
                printMenu();
                break;
            default:
                break;
        }
        
        return quit;
    }

    private void changePreferences()
    {
        int index = 0;
        char option = 'a';
        String curSection = game.getPreferences().get(index).getSection();
        String choice;

        writer.println("[" + curSection + "]");
        // Iterate through all preferences
        for (index = 0; index < game.getPreferences().size(); index++, option++)
        {
            // If new Section, print a new header
            if (!curSection.equals(game.getPreferences().get(index).getSection()))
            {
                curSection = game.getPreferences().get(index).getSection();
                writer.println("\n[" + curSection + "]");
            }
            writer.print("(" + option + ") " + game.getPreferences().get(index).getKey() 
                    + " = " + game.getPreferences().get(index).getValue() + "  ");
        }
        writer.println("\nYour choice?");
        choice = scanner.next();
        // If multiple preference choices selected
        if (choice.length() > 1)
        {
            game.setPreference(game.getPreferences().get(choice.charAt(0) - 'a'), 
                    game.getPreferences().get(choice.charAt(1) - 'a'));
        }
        else
        {
            // If only one preference choice selected, set other choice to default
            if (choice.charAt(0) - 'a' < game.getPreferences().size() / 2)
            {
                game.setPreference(game.getPreferences().get(choice.charAt(0) - 'a'), 
                        game.getPreferences().get(game.getPreferences().size() / 2));
            }
            else
            {
                game.setPreference(game.getPreferences().get(choice.charAt(0) - 'a'), 
                        game.getPreferences().get(0));
            }
        }
    }

    private void flag(String move)
    {
        flagMove = false;
        move = move.substring(1);
        game.flag(getLetterRow(move), Integer.parseInt(move.substring(1)) - 1);
        printBoard();
        printMenu();
    }

    private boolean checkInput(String command)
    {
        boolean proceed = true;

        // If command indicates a flag instead of move
        if (command.startsWith("."))
        {

            command = command.substring(1);
            // Ensure the rest of the command is valid
            if (!checkInput(command))
            {
                proceed = false;
            }
            else
            {
                flagMove = true;
            }
        }
        // Invalid letter command
        if (getLetterRow(command) >= game.getBoardSize() || getLetterRow(command) < 0)
        {
            proceed = false;
        }
        // Invalid row command
        try
        {
            // Checks if command is valid, if it is not, do nothing
            if ((Integer.parseInt(command.substring(1)) - 1) >= game.getBoardSize() || 
                    (Integer.parseInt(command.substring(1)) - 1) < 0)
            {
                proceed = false;
            }
        }
        catch (Exception e)
        {
            proceed = false;
        }

        return proceed;
    }

    private static boolean isInteger(String s) 
    {
        try 
        { 
            Integer.parseInt(s); 
        } 
        catch(NumberFormatException e) 
        { 
            return false; 
        }

        return true;
    }


    private void enterHighScore()
    {
        String name;

        writer.println("Game Won Notification: Game " + game.getBoardNum() 
                + " Cleared! ");
        writer.println("Save your time of " + game.getTimer() + "? (y/n)");

        // If user wants to enter in the hall of fame
        if (scanner.next().equalsIgnoreCase("y"))
        {
            scanner.nextLine();
            writer.println("Name Entry: Your score of " + game.getTimer() + 
                    " will be entered into the Hall of Fame.");
            writer.println("Enter your name:");
            name = scanner.nextLine();

            try 
            {
                game.addHighScore(name);
            } 
            catch (IOException e) 
            {
                System.out.println("COULD NOT ADD HIGH SCORE I/O ERROR");
            }
        }
    }

    private void move(String move)
    {
        // If game says command was a valid move, reprint board and menu
        game.move(getLetterRow(move), Integer.parseInt(move.substring(1)) - 1);

        // If the game is lost, notify user
        if (game.getGameLost() && !gameOver)
        {
            gameOver = true;
            writer.println("-- Game Over --");
            writer.println("You lost.");
        }
        // If the game is won, prompt for the high scores
        if (game.getGameWon() && !gameOver)
        {
            gameOver = true;
            enterHighScore();
        }
    }

    private void firstRun()
    {
        Random generator = new Random();

        // Generate random board until valid
        while (curBoardNum == 0)
        {
            curBoardNum = generator.nextInt(kBoardNumbers + 1);
        }
        game = new KaboomModel(curBoardNum);
        game.getKaboomBoard().addObserver(this);
        gameOver = false;
        printBoard();
        printMenu();
    }

    private void selectGame()
    {
        int check;

        writer.println("Select Game: Enter desired game number (1 - 5000):");
        // If input is a valid integer
        if (scanner.hasNextInt())
        {
            check = scanner.nextInt();
            // If integer is a valid board number
            if (check > 0 && check <= kBoardNumbers)
            {
                curBoardNum = check;
                game.selectGame(check);
                gameOver = false;
                game.getKaboomBoard().addObserver(this);
                printBoard();
                printMenu();
            }
        }
        else
        {
            scanner.next();
        }
    }

    private void newGame()
    {
        game.newGame();
        gameOver = false;
        game.getKaboomBoard().addObserver(this);
        printBoard();
        printMenu();
    }

    private void restartGame()
    {
        game.restart();
        gameOver = false;
        game.getKaboomBoard().addObserver(this);
        printBoard();
        printMenu();
    }

    private int getLetterRow(String input)
    {
        int index = 0, row = -1;
        char letter = input.toUpperCase().charAt(0);
        // Iterate through alphabet
        for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++)   
        {
            // Found the alphabet index
            if (letter == alphabet)
            {
                row = index;
            }
            index++;
        }
        return row;
    }

    private char getRowLetter(int row)
    {
        int index = 0;
        char letter = 'A';
        // Iterate through alphabet
        for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++)
        {
            // Found the corresponding letter
            if (row == index)
            {
                letter = alphabet;
            }
            index++;
        }
        return letter;
    }

    private void printMenu()
    {
        writer.println("1)Restart 2)New 3)Select 4)Scores 5)Peek "
                + "6)Cheat 7)About 8)Quit 9)Prefs ");
    }

    private void printBoard()
    {
        board = game.getBoard();
        writer.println("Kaboom - board " + game.getBoardNum());
        writer.println("Moves: " + game.getMoves() + "   Flags: " + game.getFlags() + "/"
                + game.getNumBombs() + "  " + game.getTimer());
        writer.print("   ");
        // Print column identifier
        for (int row = 1; row <= board.length; row++)
        {
            writer.print("  " + nm.format(row));
        }
        writer.println();
        // Print main game board
        for (int row = 0; row < board.length; row++)
        {
            writer.print(" " + getRowLetter(row) + ":");
            // Print corresponding symbol for Collapse Tile
            for (int col = 0; col < board[0].length; col++)
            {
                writer.print("  ");
                writer.print(board[row][col]);
            }
            writer.println();
        }
        writer.print(" --");
        // Print bottom board separator before menu
        for (int separators = 0; separators < game.getBoardSize() * kSeparatorFactor; 
                separators++)
        {
            writer.print("-");
        }
        writer.println();
    }

    /** Return a string representation of the top five high scores. 
     *  @return string is the top scores, one per line, with the
     *  score and name (in that order), separated by one or more blanks.
     *  Name is twenty characters max.  Leading blanks are allowed.
     */
    public String getHighScores()
    {  
        String output;
        LinkedList<HighScore> highscores = game.getHighScores();

        output = "-- High Scores --\n";
        // Print out all high scores
        for (int numHS = 0; numHS < highscores.size(); numHS++)
        {
            // If score is double digits, adjust spacing
            if (highscores.get(numHS).getTime() < kDoubleDigitScore)
            {
                output += "         " + highscores.get(numHS).getTimeString() + "    " + 
                        highscores.get(numHS).getName() + "\n";
            }
            else
            {
                output += "        " + highscores.get(numHS).getTimeString() + "    " + 
                        highscores.get(numHS).getName() + "\n";
            }
        }

        return output;
    }

    /**
     * Update called when board notifies observers that the board has changed
     * @param obs unused
     * @param obj unused
     */
    public void update(Observable obs, Object obj)
    {
        printBoard();
        printMenu();
    }       
}