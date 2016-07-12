/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

/**
 * The class is used to create a Suggestion object consisting of the closest word
 * to the user input and a country code associated with it. Returning both values
 * while checking for the closest matching word saves us another pass through the
 * countries.csv file (saves O(n) time, where n is the number of country entries).
 * The class is immutable (objects cannot be modified after creation)
 * but it allows access to its attributes.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Suggestion {
    
    private final String closestMatch;
    private final String countryCode;
    
    /**
     * The constructor for the suggestion, requires a closest matching word and 
     * a country code.
     * 
     * @param closestMatch The closest matching word.
     * @param countryCode The country code.
     */
    public Suggestion(String closestMatch, String countryCode) {
        
        this.closestMatch = closestMatch;
        this.countryCode = countryCode;
        
    }
    
    /**
     * 
     * @return Returns the closest matching word.
     */
    public String getClosestMatch() {
        return closestMatch;
    }
    
    /**
     * 
     * @return Returns the country code.
     */
    public String getCountryCode() {
        return countryCode;
    }
    
    /**
     * 
     * @return The default toString() method has been overwritten to return the suggestion
     * in the format "closestMatchingString countryCode".
     */
    public String toString() {
        return closestMatch + " " + countryCode;
    }
}
