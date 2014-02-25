/**
 * Preference represents a preference option read in from the preferences.ini file
 * @author Ryan Chang
 * @version 2013.2.21
 */
public class Preference 
{
    private String section;
    private String key;
    private int value;
    
    /**
     * Constructs a Preference containing 3 ini values
     * @param section section of ini file
     * @param key key of section
     * @param value value of key
     */
    public Preference(String section, String key, int value)
    {
        this.section = section;
        this.key = key;
        this.value = value;
    }
    
    /**
     * Getter for name of section
     * @return name of section
     */
    public String getSection()
    {
        return section;
    }
    
    /**
     * Getter for section key
     * @return key of section
     */
    public String getKey()
    {
        return key;
    }
    
    /**
     * Getter for value of key
     * @return value of key
     */
    public int getValue()
    {
        return value;
    }
}
