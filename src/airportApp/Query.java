/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 * This class is used for making queries.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Query {
    
    static BufferedReader countriesReader;
    static BufferedReader airportsReader;
    static BufferedReader runwaysReader;
    
    /**
     * The Query Option will ask the user for the country name or code and
     * print the airports and runways at each airport by calls to respective methods.
     * 
     * @param country The input can be a country code or a country name.
     */
    public static void query(String country) {
        
        // Ready (open) the files to be read
        readyFiles();
        
        
        /*The file of the airports can only be cross-referenced with the
        countries through the iso_country column that points to a country code.
        Therefore, if the input is a country name, we first need to find its code.
        */
        String country_code = findCountryCode(country);
            
        /*
        Now we will search for an airport and when we find one,
        we will immediately search for its runways, print the results
        and free the memory. Then we will continue with the next airport.
        
        Unfortunately, this will take time O(n x m), where n is the number of
        airports and m is the number of runways. I cannot think of a faster algorithm
        in the current state of how the data is structured. There might be a faster
        solution, if the airports and runways are separated into separate files based 
        on location (latitude and longitude) but that will require a certain time
        of pre-processing and we also need to know the approximate location region
        of the selected country, (N,S,E,W)-most coordinates of the country.
        */
        printAirportsWithRunways(country_code);
        
        // Close buffered readers
        closeFiles();
    }
    
    /**
     * This method finds a country code based on user input. If the user input does
     * not match any country (i.e. the input is fuzzy), it returns a country code
     * of a country that is "closest" to a country in the existing set of countries.
     * The concept of "closeness" is described in another method that the current
     * one uses. See methods simpleSuggestCountry(String country) and 
     * suggestCountry(String country).
     * 
     * There is no faster solution than O(n), where n is the number of countries
     * stored in the countries.csv, unless we are willing to preprocess and store
     * data into memory through a hash map but that will consume valuable
     * memory. Therefore, we will sacrifice time and go through all the entries
     * of countries.csv until we find a match or reach end of file. The suggestion
     * in case of a fuzzy input takes additionally O(n) time to go through all the
     * entries again. So the total time of this method is O(2*n) or theoretical O(n).
     * 
     * @param country The user input can be either a country name or country code.
     * @return Returns a country code based on user input.
     */
    private static String findCountryCode(String country) {
        
        String country_code = null;
        
        try {
            
            String line; // pointer (line reader) used with the countries
            
            countriesReader.readLine(); // skip first line (column names)
            
            while((line = countriesReader.readLine()) != null) {
                
                Country c = Utils.readCountry(line);
                
                if(country.equalsIgnoreCase(c.getCode()) || country.equalsIgnoreCase(c.getName())) {
                    country_code = c.getCode();
                    break; // stop searching immediately after we find a match
                }
            }
            
            /* If we do not find a country, we will inform the user and do the
            search for a country that most closely resembles the input
            (i.e. zimb = Zimbabwe) based on the longest common prefix. 
            I've created and alternative method to provide support if the match 
            should be based on a user misspelling the country (based on the 
            Levenshtein distance). The suggestions are mutually exclusive.
            */
            if(country_code == null) {
                System.out.println("Country "+ country + " not found.");
                
                // Based on the Longest Common Prefix (suggestion as one writes)
                Suggestion s = simpleSuggestCountry(country);
                
                // Based on Levenstein's distance (misspelling)
                //Suggestion s = suggestCountry(country);
                
                if( s != null ) {
                    
                    System.out.println("The search is done for a country that most "
                        + "closely resembles your input: "+ s.getClosestMatch());
                    country_code = s.getCountryCode();
                    
                }
                else { // if for any reason the match fails
                    
                    country_code = "";
                    
                }
                
            }
                
        }
        catch(IOException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        finally {
            if(countriesReader != null) {
                try {
                    countriesReader.close();
                } catch (IOException ex) {
                    System.err.println("Error: "+ ex.getMessage());
                }
            }
        }
        
        return country_code;
    }
    
    /**
     * This method suggests the closest matching word and country code.
     * 
     * The matching is done based on the Levenshtein distance: the minimum number 
     * of single-character edits (i.e. insertions, deletions or substitutions) 
     * required to change one word into the other. A disadvantage of the Levenshtein 
     * distance is that any change (edit) has an equal influence on the matching 
     * distance, whereas an algorithm that penalizes the different errors in a 
     * different degree (i.e. insertions penalty = 1; deletions penalty = 2; 
     * substitutions penalty = 3) can produce more accurate results in some cases.
     * 
     * To demonstrate the above statement, let us consider the input "Zimb" compared 
     * to the two strings "Zimbabwe" and "Fiji".
     * The Levenshtein distance between "Zimb" and "Zimbabwe" is 4; while between 
     * "Zimb" and "Fiji" it is 3. Therefore, "Fiji" is the closest match. However, 
     * if the input is "Zimbab", the distance between "Zimbab" and "Zimbabwe" is 
     * 2; while between "Zimbab" and "Fiji" is 5. Therefore, "Zimbabwe" is the 
     * closest match.
     * 
     * The Levenshtein distance is not necessarily a bad similarity test. It all 
     * depends on our preferences of how we would like to do the comparisons. In 
     * other cases than the one mentioned above, the Levenshtein distance can 
     * produce more accurate results. There are also many other similarity matching 
     * algorithms (some based on longest common subsequence).
     * 
     * Attention! This method uses a third-party library from commons.apache.org. 
     * Make sure that the library is added to the compilation libraries of your 
     * IDE. The library's Levenshtein algorithm is updated with a newer version 
     * (by Chas Emerick) that avoids OutOfMemory errors that can occur for very 
     * large Strings. The library also contains implementations for finding the 
     * longest common prefix, the Jaro-Winkler distance and another Fuzzy distance. 
     * So they are all alternatives, if one is not satisfied with the Levenshtein's 
     * one, and can also possibly be combined in different ways. Assuming the user 
     * does not misspell the beginning of the country, an algorithm counting the 
     * length of the longest common prefix towards the length of the larger 
     * sequence (a check larger or equal to 25% ) can precede a check for the 
     * Levenshtein's distance.
     * 
     * @param country User input for which a match will be returned.
     * @return Returns a Suggestion object (the closest match to the input 
     * argument and its associated country code) or null , if the match fails 
     * for any reason.
     */
    private static Suggestion suggestCountry(String country) {
        
        Suggestion suggestion = null;
        String suggestionMatch = "";
        String suggestionCode = "";
        int suggestionDistance = -1;
        
        try {
            countriesReader = new BufferedReader(new FileReader("resources/countries.csv"));
            
            String line; // pointer (line reader) used with the countries
            
            countriesReader.readLine(); // skip first line (column names)
            
            while((line = countriesReader.readLine()) != null) {
                
                Country c = Utils.readCountry(line);
                
                // use the smaller distance of code name and country name
                int codeDistance = StringUtils.getLevenshteinDistance(country, c.getCode());
                int nameDistance = StringUtils.getLevenshteinDistance(country, c.getName());
                int distance = ((codeDistance < nameDistance)? codeDistance : nameDistance);
                
                // keep track of the associated match
                String match = ((codeDistance < nameDistance)? c.getCode() : c.getName());
                
                // keep track of the associated country code
                String code = c.getCode();
                
                // for the first country just overwrite global values
                if (suggestionDistance == -1) {
                    
                    suggestionDistance = distance;
                    suggestionMatch = match;
                    suggestionCode = code;
                }
                else { // for the rest use the global comaprison
                    
                    if (distance < suggestionDistance) {
                        
                        suggestionDistance = distance;
                        suggestionMatch = match;
                        suggestionCode = code;
                    }
                }
                
                suggestion = new Suggestion(suggestionMatch,suggestionCode);
                
            }
            
        } catch (IllegalArgumentException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
         catch (IOException ex) {
             System.err.println("Error: "+ ex.getMessage());
         }
        
        return suggestion;
    }
    
    /**
     * This method suggests the closest matching word and country code.
     * 
     * The matching is done based on the longest common prefix. The method will 
     * work acceptably, if the user does not misspell the beginning of the 
     * country (i.e. it will work well with "zimb" = "Zimbabwe").
     * 
     * @param country User input for which a match will be returned.
     * @return Returns a Suggestion object (the closest match to the input 
     * argument and its associated country code) or null , if the match fails 
     * for any reason.
     */
    private static Suggestion simpleSuggestCountry(String country) {
        
        Suggestion suggestion = null;
        String suggestionMatch = "";
        String suggestionCode = "";
        int longestCommonPrefix = -1;
        
        try {
            countriesReader = new BufferedReader(new FileReader("resources/countries.csv"));
            
            String line; // pointer (line reader) used with the countries
            
            countriesReader.readLine(); // skip first line (column names)
            
            while((line = countriesReader.readLine()) != null) {
                
                Country c = Utils.readCountry(line);
                
                // use the longest common prefix of code name and country name
                int codePrefix = StringUtils.getCommonPrefix(
                        new String[] {country.toLowerCase(), c.getCode().toLowerCase()}).length();
                int namePrefix = StringUtils.getCommonPrefix(
                        new String[] {country.toLowerCase(), c.getName().toLowerCase()}).length();
                int lcp = ((codePrefix > namePrefix)? codePrefix : namePrefix);
                
                // keep track of the associated match
                String match = ((codePrefix > namePrefix)? c.getCode() : c.getName());
                
                // keep track of the associated country code
                String code = c.getCode();
                
                // for the first country just overwrite global values
                if (longestCommonPrefix == -1) {
                    
                    longestCommonPrefix = lcp;
                    suggestionMatch = match;
                    suggestionCode = code;
                }
                else { // for the rest use the global comaprison
                    
                    if (lcp > longestCommonPrefix) {
                        
                        longestCommonPrefix = lcp;
                        suggestionMatch = match;
                        suggestionCode = code;
                    }
                }
                
                suggestion = new Suggestion(suggestionMatch,suggestionCode);
                
            }
            
        } catch (IllegalArgumentException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
         catch (IOException ex) {
             System.err.println("Error: "+ ex.getMessage());
         }
        
        return suggestion;
    }
    
    
    /**
     * This method searches for an airport and when it finds one,
     * it immediately searches for its runways, prints the results by calling
     * a print method (printAirport(Airport)) by passing the airport object
     * and frees the memory. Then it continues with the next airport.
     * 
     * Unfortunately, this takes time O(n * m), where n is the number of
     * airports and m is the number of runways. I cannot think of a faster algorithm
     * in the current state of how the data is structured. There might be a faster
     * solution, if the airports and runways are separated into separate files based 
     * on location (latitude and longitude) but that will require a certain time
     * of pre-processing and we will also need to know the approximate location region
     * of the selected country, (N,S,E,W)-most coordinates of the country.
     * 
     * @param country_code A country_code for which the method should find and print
     * airports and their runways.
     */
    private static void printAirportsWithRunways(String country_code) {

        String line1; // pointer (line reader) used with the airports
        String line2; // pointer (line reader) used with the runways
        
        try {
            if(!country_code.equals("")) { // guards from a failed match
                
                airportsReader.readLine(); // skip first line (column names)
                
                while((line1 = airportsReader.readLine()) != null) {
                    
                    Airport a = Utils.readAirport(line1);
                    
                    runwaysReader = new BufferedReader(new FileReader("resources/runways.csv")); //reopen the runways file
                    
                    if(country_code.equalsIgnoreCase(a.getCountryCode())) {
                        
                        // Finding runways for this airport
                        runwaysReader.readLine(); // skip first line (column names)
                        
                        while((line2 = runwaysReader.readLine()) != null) {
                            
                            Runway r = Utils.readRunway(line2);
                            
                            if(a.getID() == r.getAirportReference()) {
                                a.addRunway(r);
                            }
                        }
                        
                        // Print the airport with its runways
                        printAirport(a);
                    }
                }
            }
        }
        catch(IOException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        finally {
            if(airportsReader != null) {
                try {
                    airportsReader.close();
                } catch (IOException ex) {
                    System.err.println("Error: "+ ex.getMessage());
                }
            }
            if(runwaysReader != null) {
                try {
                    runwaysReader.close();
                } catch (IOException ex) {
                    System.err.println("Error: "+ ex.getMessage());
                }
            }
        }
    }
    
    /**
     * This method prints an airport with its associated runways to the command line
     * in the format:
     * 
     * Airport: airport_id identification name
     * ---runway: runway_id airport_id airport_identification surface
     * ---runway: ...
     * 
     * @param a An Airport object that contains runways.
     */
    private static void printAirport(Airport a) {
        System.out.println("Airport: " + a.getID() + " " + a.getIdentification() +
                " " + a.getName());
        a.getRunways().stream().forEach((r) -> {
            System.out.println("---runway: " + r.getID() + " " + r.getAirportReference() +
                    " " + r.getAirportIdentification() + " " + r.getSurface());
        });
        System.out.println();
    }

    /**
     * This method opens the buffer readers for the three files.
     */
    private static void readyFiles() {
        try {
            countriesReader = new BufferedReader(new FileReader("resources/countries.csv"));
            airportsReader = new BufferedReader(new FileReader("resources/airports.csv"));
            runwaysReader = new BufferedReader(new FileReader("resources/runways.csv"));
        } catch (FileNotFoundException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
    }
    
    /**
     * This method closes the buffer readers for the three files.
     */
    private static void closeFiles() {
        
        if(countriesReader != null) {
            try {
                countriesReader.close();
            } catch (IOException ex) {
                System.err.println("Error: "+ ex.getMessage());
            }
        }
        
        if(airportsReader != null) {
            try {
                airportsReader.close();
            } catch (IOException ex) {
                System.err.println("Error: "+ ex.getMessage());
            }
        }
        
        if(runwaysReader != null) {
            try {
                runwaysReader.close();
            } catch (IOException ex) {
                System.err.println("Error: "+ ex.getMessage());
            }
        }
    }
    
    
}
