import java.text.NumberFormat;

/**
 * HighScore represents an entry for a player in the hall of fame
 * @author Ryan Chang
 * @version 2013.2.21
 */
public class HighScore 
{
    private String name;
    private int time;
    private NumberFormat nm = NumberFormat.getInstance();
    private static final int kSeconds = 60;
    
    /**
     * Holds high score and name of player
     * @param name name of player
     * @param time time of player
     */
    public HighScore(String name, int time)
    {
        nm.setMinimumIntegerDigits(2);
        
        this.name = name;
        this.time = time;
    }
    
    /**
     * Getter for name of player
     * @return Name of player
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Getter for score of player
     * @return Score of player
     */
    public int getTime()
    {
        return time;
    }
    
    /**
     * Getter for string representation of time
     * @return Formatted string representation of player's time
     */
    public String getTimeString()
    {
        return time / kSeconds + ":" + nm.format(time % kSeconds);
    }
}
