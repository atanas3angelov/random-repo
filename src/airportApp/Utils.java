/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

/**
 * This class is used for reading an object from a csv line and possibly other utilities.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Utils {
    
    /**
     * @param line The line as a String.
     * @return Returns a Country object based on the csv line from the 
     * countries.csv file.
     */
    public static Country readCountry(String line) {
        Country c = null;
        String[] arguments = line.split(",",-1);
        
        try {
            c = new Country(Integer.parseInt(arguments[0]), arguments[1].replace("\"", ""), arguments[2].replace("\"", ""));
        }
        catch(PositiveIntegerRequiredException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        return c;
    }
    
    /**
     * @param line The line as a String.
     * @return Returns an Airport object based on the csv line from the 
     * airports.csv file.
     */
    public static Airport readAirport(String line) {
        Airport a = null;
        String[] arguments = line.split(",",-1);
        
        try {
        a = new Airport(Integer.parseInt(arguments[0]), arguments[1].replace("\"", ""), arguments[3].replace("\"", ""), arguments[8].replace("\"", ""));
        }
        catch(PositiveIntegerRequiredException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        return a;
    }
    
    /**
     * @param line The line as a String.
     * @return Returns a Runway object based on the csv line from the 
     * runways.csv file.
     */
    public static Runway readRunway(String line) {
        Runway r = null;
        String[] arguments = line.split(",",-1);
        
        try {
            r = new Runway(Integer.parseInt(arguments[0]),Integer.parseInt(arguments[1]),arguments[2].replace("\"", ""),arguments[5].replace("\"", ""),arguments[8].replace("\"", ""));
        }
        catch(PositiveIntegerRequiredException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        return r;
    }
    
    
}
