import java.io.*;
import java.util.Scanner;

/**
 * Comprehensive test cases for the KaboomBoard, KaboomModel, and KaboomConsole classes
 * @author Ryan Chang
 * @version 2013.2.21
 */
public class KaboomConsoleTest extends junit.framework.TestCase
{
    /**
     * Tests only quitting
     */
    public void testJustQuit()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
    }

    /**
     * Tests restarting the same board
     */
    public void testRestart()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "1\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected1, actual[13].trim());
    }

    /**
     * Tests if new game increments by one
     */
    public void testNewGame()
    {
        int gameNum1, gameNum2;

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "2\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        gameNum1 = Integer.parseInt(expected1.substring("Kaboom - board ".length()));
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[13].trim();
        gameNum2 = Integer.parseInt(expected7.substring("Kaboom - board ".length()));
        assertEquals(gameNum1 + 1, gameNum2);
    }

    /**
     * Tests to see if game is selected
     */
    public void testSelectGame()
    {
        int gameNum2;

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "3\n2\n6\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[14].trim();
        gameNum2 = Integer.parseInt(expected7.substring("Kaboom - board ".length()));
        assertEquals(2, gameNum2);
    }

    /**
     * Tests invalid input for select game
     */
    public void testBadSelectGame()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "3\na\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[13].trim();

        assertEquals("Select Game: Enter desired game number (1 - 5000):", expected7);
    }

    /**
     * Tests invalid game number in select game
     */
    public void testBadNumSelectGame()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "3\n5001\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[13].trim();

        assertEquals("Select Game: Enter desired game number (1 - 5000):", expected7);
    }

    /**
     * Tests negative board number select game
     */
    public void testBadNum2SelectGame()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "3\n-1\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[13].trim();

        assertEquals("Select Game: Enter desired game number (1 - 5000):", expected7);
    }

    /**
     * Test new game rollover
     */
    public void testNewGame5000()
    {
        int gameNum2;

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "3\n5000\n2\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[27].trim();
        gameNum2 = Integer.parseInt(expected7.substring("Kaboom - board ".length()));
        assertEquals(1, gameNum2);
    }

    /**
     * Test viewing high scores
     */
    public void testViewHighScores()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "4\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        String expected1 = actual[0].trim();
        assertEquals(err, expected1, actual[0].trim());
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        String expected7 = actual[13].trim();
        assertEquals("-- High Scores --", expected7);
    }

    /**
     * Tests a whole game playthrough
     * @throws IOException if in file cannot be read
     */
    public void testPlayBoard7() throws IOException
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String[] expected = new String[200];
        FileReader in = new FileReader("kaboom/testPlayBoard7.in");
        Scanner scanner = new Scanner(in);
        int numLines = 0;

        for (int line = 0; scanner.hasNext(); line++)
        {
            expected[line] = scanner.nextLine().trim();
            numLines = line;
        }

        String inputData = "3\n7\na8\nh8\na1\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";

        for (int line = 12; line < numLines; line++)
        {
            assertEquals(err, expected[line], actual[line].trim());
        }

        in.close();
    }

    /**
     * Tests adding a high score
     * @throws IOException if in file cannot be read
     */
    public void testAddHighScore() throws IOException
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String[] expected = new String[200];
        FileReader in = new FileReader("kaboom/testPlayBoard7.in");
        Scanner scanner = new Scanner(in);
        int numLines = 0;

        for (int line = 0; scanner.hasNext(); line++)
        {
            expected[line] = scanner.nextLine().trim();
            numLines = line;
        }

        String inputData = "3\n7\na8\nh8\na1\ny\nBillium\n8"
                + "\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";

        for (int line = 12; line < numLines; line++)
        {
            assertEquals(err, expected[line], actual[line].trim());
        }
        assertEquals("Name Entry: Your score of 0:00 will be entered into "
                + "the Hall of Fame.", actual[numLines + 1]);
        assertEquals("Enter your name:", actual[numLines + 2]);

        in.close();
    }

    /**
     * Tests making a move on an empty cell
     */
    public void testEmptyCell()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String inputData = "6\nb1\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
    }

    /**
     * Tests if cheating works
     */
    public void testCheat()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String inputData = "6\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
    }

    /**
     * Tests if moving is possible after cheating
     */
    public void testMoveAfterCheat()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String inputData = "6\na2\nn\na1\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
    }

    /**
     * Tests making an invalid move
     */
    public void testBadMove()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "z9\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
    }

    /**
     * Tests making another invalid move
     */
    public void testBadMove2()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "a-1\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
    }

    /**
     * Tests making another invalid move
     */
    public void testBadMove3()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "$9\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
    }

    public void testBadMove4()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = ".z1\n6\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
    }

    public void testLoseGame()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String expected13 = "Moves: 1   Flags: ";
        String expected14 = "1  2  3  4  5  6  7  8";
        String expected15 = "A:  *  1";
        String expected16 = "H:";
        String expected17 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected18 = "--------------------------";
        String expected19 = "-- Game Over --";
        String expected20 = "You lost.";
        String inputData = "6\na1\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
        assertEquals(err, expected1, actual[26].substring(0, expected1.length()));
        assertEquals(err, expected13, actual[27].substring(0, expected7.length()));
        assertEquals(err, expected14, actual[28].trim());
        assertEquals(err, expected15, actual[29].trim());
        assertEquals(err, expected16, actual[36].trim().substring(0, 2));
        assertEquals(err, expected18, actual[37].trim());
        assertEquals(err, expected17, actual[38].trim());
        assertEquals(err, expected19, actual[39].trim());
        assertEquals(err, expected20, actual[40].trim());
    }

    /**
     * Tests making another invalid move
     */
    public void testLongName() throws IOException
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String[] expected = new String[200];
        FileReader in = new FileReader("kaboom/testPlayBoard7.in");
        Scanner scanner = new Scanner(in);
        int numLines = 0;

        for (int line = 0; scanner.hasNext(); line++)
        {
            expected[line] = scanner.nextLine().trim();
            numLines = line;
        }

        String inputData = "3\n7\na8\nh8\na1\ny\n"
                + "BilliumFancySudsTheFifthJuniorSenior\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";

        for (int line = 12; line < numLines; line++)
        {
            assertEquals(err, expected[line], actual[line].trim());
        }
        assertEquals("Name Entry: Your score of 0:00 will be entered into "
                + "the Hall of Fame.", actual[numLines + 1]);
        assertEquals("Enter your name:", actual[numLines + 2]);

        in.close();
    }

    /**
     * Tests if high scores sort properly
     * @throws IOException if halloffame cannot be read
     */
    public void testHighScoreOrder() throws IOException
    {
        File highscores = new File("kaboom/halloffame.ser");
        highscores.delete();
        FileWriter writer = new FileWriter(highscores);

        writer.write("John                =3\n"
                + "Ryan                =2\n"
                + "Billium             =3\n"
                + "Mark                =7\n"
                + "SpongeBob           =10\n");
        writer.close();

        testViewHighScores();

    }

    /**
     * Tests kicking someone off high scores
     * @throws IOException if halloffame cannot be read
     */
    public void testHighScoreKick() throws IOException
    {
        File highscores = new File("kaboom/halloffame.ser");
        highscores.delete();
        FileWriter writer = new FileWriter(highscores);

        writer.write("John                =2\n"
                + "Ryan                =3\n"
                + "Billium             =3\n"
                + "Mark                =7\n"
                + "SpongeBob           =10\n");
        writer.close();

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String inputData = "6\na2\ny\nCheetors\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());

    }

    /**
     * Tests kicking off by name
     * @throws IOException if high scores cannot be read
     */
    public void testHighScoreNameKick() throws IOException
    {
        File highscores = new File("kaboom/halloffame.ser");
        highscores.delete();
        FileWriter writer = new FileWriter(highscores);

        writer.write("John                =0\n"
                + "Ryan                =0\n"
                + "Billium             =0\n"
                + "Mark                =0\n"
                + "Billium           =0\n");
        writer.close();

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String inputData = "6\na2\ny\nCheetors\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
    }

    public void testHighScoreNameKickTwo() throws IOException
    {
        File highscores = new File("kaboom/halloffame.ser");
        highscores.delete();
        FileWriter writer = new FileWriter(highscores);

        writer.write("John                =0\n"
                + "Ryan                =0\n"
                + "Billium             =0\n"
                + "Mark                =0\n"
                + "Billium           =0\n");
        writer.close();

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected9 = "A:  -  -";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String inputData = "6\na2\ny\nArianna\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected9, actual[16].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
    }

    /**
     * Tests creating new high scores file
     * @throws IOException if high scores file cannot be read
     */
    public void testNewHighScoreFile() throws IOException
    {
        File highscores = new File("kaboom/halloffame.ser");

        assertTrue(highscores.delete());

        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String[] expected = new String[200];
        FileReader in = new FileReader("kaboom/testPlayBoard7.in");
        Scanner scanner = new Scanner(in);
        int numLines = 0;

        for (int line = 0; scanner.hasNext(); line++)
        {
            expected[line] = scanner.nextLine().trim();
            numLines = line;
        }



        String inputData = "3\n7\na8\nh8\na1\ny\n"
                + "Squidward\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";

        for (int line = 12; line < numLines; line++)
        {
            assertEquals(err, expected[line], actual[line].trim());
        }
        assertEquals("Name Entry: Your score of 0:00 will be entered into "
                + "the Hall of Fame.", actual[numLines + 1]);
        assertEquals("Enter your name:", actual[numLines + 2]);

        in.close();
    }

    public void testPlayBoard5() throws IOException
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String[] expected = new String[1000];
        FileReader in = new FileReader("kaboom/testPlayBoard5.in");
        Scanner scanner = new Scanner(in);
        int numLines = 0;

        for (int line = 0; line < 340; line++)
        {
            expected[line] = scanner.nextLine().trim();
            numLines = line;
        }



        String inputData = "3\n5\nd1\nf5\nb7\n.f2\nf1\nd3\ne8\nh7\nh8\n.g7\ng8\nf8\n.c3\n"
                + ".a5\n.d4\n.d7\n.d8\n.f7\nb5\nc5\nc4\nd5\nd6\ne7\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";

        for (int line = 12; line < numLines; line++)
        {
            assertEquals(err, expected[line], actual[line].trim());
        }
        assertEquals("Save your time of 0:00? (y/n)", actual[numLines + 1]);

        in.close();
    }

    public void testFlagTest()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Moves: 0   Flags: ";
        String expected8 = "1  2  3  4  5  6  7  8";
        String expected10 = "H:";
        String expected11 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected12 = "--------------------------";
        String expected13 = "Moves: 0   Flags: ";
        String expected14 = "1  2  3  4  5  6  7  8";
        String expected16 = "H:";
        String expected17 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected18 = "--------------------------";
        String inputData = ".a1\n.a1\n6\n.a3\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[14].substring(0, expected7.length()));
        assertEquals(err, expected8, actual[15].trim());
        assertEquals(err, expected10, actual[23].trim().substring(0, 2));
        assertEquals(err, expected12, actual[24].trim());
        assertEquals(err, expected11, actual[25].trim());
        assertEquals(err, expected13, actual[27].substring(0, expected13.length()));
        assertEquals(err, expected14, actual[28].trim());
        assertEquals(err, expected16, actual[36].trim().substring(0, 2));
        assertEquals(err, expected18, actual[37].trim());
        assertEquals(err, expected17, actual[38].trim());
    }

    public void testPeek()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";

        String inputData = "5\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
    }

    public void testClearHoF()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";

        String inputData = "0\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());

    }

    public void testAbout()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "Kaboom by Ryan Chang";

        String inputData = "7\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[13].trim());
    }

    public void testPreferencesOne()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "[Board Size]";
        String expected8 = "(a) small = 8  (b) medium = 10  (c) large = 12";
        String expected9 = "[Difficulty]";
        String expected10 = "(d) easy = 8  (e) moderate = 6  (f) hard = 4";  
        String expected11 = "Your choice?";
        String expected12 = "Kaboom - board ";
        String expected13 = "Moves: 0   Flags: ";
        String expected14 = "1  2  3  4  5  6  7  8  9  0";

        String inputData = "9\nb\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[13].trim());
        assertEquals(err, expected8, actual[14].trim());
        assertEquals(err, expected9, actual[15].trim());
        assertEquals(err, expected10, actual[16].trim());
        assertEquals(err, expected11, actual[17].trim());
        assertEquals(err, expected12, actual[18].substring(0, expected12.length()));
        assertEquals(err, expected13, actual[19].substring(0, expected13.length()));
        assertEquals(err, expected14, actual[20].trim());
    }

    public void testPreferencesTwo()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "[Board Size]";
        String expected8 = "(a) small = 8  (b) medium = 10  (c) large = 12";
        String expected9 = "[Difficulty]";
        String expected10 = "(d) easy = 8  (e) moderate = 6  (f) hard = 4";  
        String expected11 = "Your choice?";
        String expected12 = "Kaboom - board ";
        String expected13 = "Moves: 0   Flags: ";
        String expected14 = "1  2  3  4  5  6  7  8";

        String inputData = "9\nd\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[13].trim());
        assertEquals(err, expected8, actual[14].trim());
        assertEquals(err, expected9, actual[15].trim());
        assertEquals(err, expected10, actual[16].trim());
        assertEquals(err, expected11, actual[17].trim());
        assertEquals(err, expected12, actual[18].substring(0, expected12.length()));
        assertEquals(err, expected13, actual[19].substring(0, expected13.length()));
        assertEquals(err, expected14, actual[20].trim());
    }

    public void testPreferencesThree()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);

        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "[Board Size]";
        String expected8 = "(a) small = 8  (b) medium = 10  (c) large = 12";
        String expected9 = "[Difficulty]";
        String expected10 = "(d) easy = 8  (e) moderate = 6  (f) hard = 4";  
        String expected11 = "Your choice?";
        String expected12 = "Kaboom - board ";
        String expected13 = "Moves: 0   Flags: ";
        String expected14 = "1  2  3  4  5  6  7  8  9  0";

        String inputData = "9\nbe\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected7, actual[13].trim());
        assertEquals(err, expected8, actual[14].trim());
        assertEquals(err, expected9, actual[15].trim());
        assertEquals(err, expected10, actual[16].trim());
        assertEquals(err, expected11, actual[17].trim());
        assertEquals(err, expected12, actual[18].substring(0, expected12.length()));
        assertEquals(err, expected13, actual[19].substring(0, expected13.length()));
        assertEquals(err, expected14, actual[20].trim());
    }

    public void testPreferencesFour() throws IOException
    {
        PrintWriter writer = null;
        File file = new File("kaboom/preferences.ini");
        
        assertTrue(file.delete());
        writer = new PrintWriter(new FileWriter("kaboom/preferences.ini"));
        writer.println("#Sample Kaboom preferences.ini");
        writer.println();
        writer.println("[Difficulty]");
        writer.println("easy = 8");
        writer.println("moderate = 6");
        writer.println("hard = 4");
        writer.println();
        writer.println("[Board Size]");
        writer.println("small = 8");
        writer.println("medium = 10");
        writer.println("large = 12");

        writer.close();


        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:  -";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String expected7 = "[Board Size]";
        String expected8 = "(d) small = 8  (e) medium = 10  (f) large = 12";
        String expected9 = "[Difficulty]";
        String expected10 = "(a) easy = 8  (b) moderate = 6  (c) hard = 4";  
        String expected11 = "Your choice?";
        String expected12 = "Kaboom - board ";
        String expected13 = "Moves: 0   Flags: ";
        String expected14 = "1  2  3  4  5  6  7  8  9  0";

        String inputData = "9\nbe\n8\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, expected1a.length()));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, expected3.length()));
        assertEquals(err, expected4, actual[10].trim().substring(0, expected4.length()));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, expected9, actual[13].trim());
        assertEquals(err, expected10, actual[14].trim());
        assertEquals(err, expected7, actual[15].trim());
        assertEquals(err, expected8, actual[16].trim());
        assertEquals(err, expected11, actual[17].trim());
        assertEquals(err, expected12, actual[18].substring(0, expected12.length()));
        assertEquals(err, expected13, actual[19].substring(0, expected13.length()));
        assertEquals(err, expected14, actual[20].trim());

        file.delete();
        writer = new PrintWriter(new FileWriter("kaboom/preferences.ini"));
        writer.println("#Sample Kaboom preferences.ini");
        writer.println();
        writer.println("[Board Size]");
        writer.println("small = 8");
        writer.println("medium = 10");
        writer.println("large = 12");
        writer.println();
        writer.println("[Difficulty]");
        writer.println("easy = 8");
        writer.println("moderate = 6");
        writer.println("hard = 4");

        writer.close();

    }

    public void testTableModel()
    {
        String prefs = "[Board Size]\nsmall=8\n";
        writePreferences(prefs);
        String expected1 = "Kaboom - board ";
        String expected1a = "Moves: 0   Flags: ";
        String expected2 = "1  2  3  4  5  6  7  8";
        String expected3 = "A:";
        String expected4 = "H:";
        String expected5 = "1)Restart 2)New 3)Select 4)Scores 5)Peek 6)Cheat 7)About 8)Quit 9)Prefs";
        String expected6 = "--------------------------";
        String inputData = "6\n";
        StringReader sr = new StringReader(inputData);
        StringWriter sw = new StringWriter();
        KaboomConsole console = new KaboomConsole();
        console.setIOsources(sr, sw);
        console.run();
        String[] actual = sw.toString().split("\\n");
        String err = "The text of a menu or label was incorrect";
        assertEquals(err, expected1, actual[0].substring(0, expected1.length()));
        assertEquals(err, expected1a, actual[1].substring(0, 18));
        assertEquals(err, expected2, actual[2].trim());
        assertEquals(err, expected3, actual[3].trim().substring(0, 2));
        assertEquals(err, expected4, actual[10].trim().substring(0, 2));
        assertEquals(err, expected6, actual[11].trim());
        assertEquals(err, expected5, actual[12].trim());
        assertEquals(err, 8, console.getModel().getColumnCount());
        assertEquals(err, 8, console.getModel().getRowCount());
        assertEquals(err, "", console.getModel().getColumnName(0));
        assertEquals(err, "-", console.getModel().getValueAt(0, 0));
    }

    private void writePreferences(String prefdata)
    {
        //        PrintWriter writer = null;
        //        try 
        //        {
        //            writer = new PrintWriter(new FileWriter("kaboom/preferences.ini"));
        //            writer.println(prefdata);
        //            writer.close();
        //        } 
        //        catch (Exception e) 
        //        {
        //            e.printStackTrace();
        //        }  
    }

}
