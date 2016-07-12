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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is used for calling reports.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Reports {
    
    static BufferedReader countriesReader;
    static BufferedReader airportsReader;
    static BufferedReader runwaysReader;
    
    /**
     * This method prints:
     * - 10 countries with highest number of airports (with count)
     *      and 10 countries with lowest number of airports (with count).
     * 
     * There are around 250 entries for countries in the file countries.csv and
     * therefore the memory we are going to utilize to count the airports for
     * all of them will not be much. We will use a HashMap to hash the different
     * countries (country codes) and the count of airports. The creation of the
     * HashMap will take time O(n), where n is the number of entries in the file
     * countries.csv. The counting will take time O(m), where m is the number
     * of entries in the file airports.csv. Total time to create the hash table:
     * O(n + m).
     * 
     * Then we have to choose from either:
     * - sorting the hash map (turned into a list) and show the first 
     * and last 10 elements [takes at least O(n) + O(n * logn) time] or
     * - creating 2 arrays of 10 elements and substituting the minimum/maximum
     * element in them as we go through the hash map entries
     * [takes at least O(n * 10) time]. In addition we will need also 2 other arrays
     * to keep track of the labels of the cities.
     * 
     * Even though the elements in the hash map are not many, for this method
     * the later option is implemented because it is theoretically faster.
     */
    @SuppressWarnings("unchecked")
    public static void printTop10CountriesWithMaxAndMinAirports() {
        
        // Ready (open) the files to be read
        readyFiles();
        
        /* 
        create the hash map with country codes
        (and a default 0 count for the airports number)
        */
        HashMap<String,Integer> country_airports = createMapCountriesAirportsCount();
        
        /*
        pass through the entries in the file airports.csv and
        increase the count of airports based on the country code
        */
        countAirports(country_airports);
        
        // create maximum/minimum arrays (and associated labels)
        int[] top10maximum = new int[10];
        String[] top10maximumLabels = new String[10];
        
        int[] top10minimum = new int[10];
        String[] top10minimumLabels = new String[10];
        
        // finding the maximum/minimum elements
        int i = 0;
        Iterator it = country_airports.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,Integer> entry = (Map.Entry) it.next();
            // for the first 10 entries in the hash map, just overwrite the values in the arrays
            if(i < 10) {
                top10maximum[i] = entry.getValue();
                top10maximumLabels[i] = entry.getKey();
                top10minimum[i] = entry.getValue();
                top10minimumLabels[i] = entry.getKey();
                
                i++;
            }
            // for the rest entries, find min/max in arrays and substitute if necessary
            else { 
                int min = top10maximum[0];
                int minIndex = 0;
                int max = top10minimum[0];
                int maxIndex = 0;
                
                for(int j = 1; j < 10; j++) {
                    if( min > top10maximum[j]) {
                        min = top10maximum[j];
                        minIndex = j;
                    }
                    if( max < top10minimum[j]) {
                        max = top10minimum[j];
                        maxIndex = j;
                    }
                }
                
                if(entry.getValue() < max) {
                    top10minimum[maxIndex] = entry.getValue();
                    top10minimumLabels[maxIndex] = entry.getKey();
                }
                if(entry.getValue() > min) {
                    top10maximum[minIndex] = entry.getValue();
                    top10maximumLabels[minIndex] = entry.getKey();
                }
            }
        }
        
        // print the top10 maximum/minimum countries with the associated count
        System.out.println("The top 10 countries with most airports are:");
        for(int j = 0; j < 10; j++) {
            System.out.println("---" + top10maximumLabels[j] + ": " + top10maximum[j]);
        }
        
        System.out.println("The top 10 countries with least airports are:");
        for(int j = 0; j < 10; j++) {
            System.out.println("---" + top10minimumLabels[j] + ": " + top10minimum[j]);
        }
        
        // Close buffered readers
        closeFiles();
    }
    
    /**
     * Creates a hash map of country codes and default 0 count for number of airports.
     * 
     * @return The populated hash map with country codes and default 0 count.
     */
    private static HashMap<String,Integer> createMapCountriesAirportsCount() {
        
        HashMap<String,Integer> country_airports = new HashMap<>();
        
        try {
            String line; // pointer (line reader) used with the countries
            
            countriesReader.readLine(); // skip first line (column names)
            
            while((line = countriesReader.readLine()) != null) {
                
                Country c = Utils.readCountry(line);
                
                country_airports.put(c.getCode(), 0);
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
        
        return country_airports;
    }
    
    /**
     * Passes through the entries in the file airports.csv and
     * increases the count of airports based on the country code.
     * Works directly on the given hash map!
     * 
     * @param country_airports The hash map of country codes and a default 0 count.
     */
    private static void countAirports(HashMap<String,Integer> country_airports) {
        
        try {
            String line; // pointer (line reader) used with the airports
            
            airportsReader.readLine(); // skip first line (column names)
            
            while((line = airportsReader.readLine()) != null) {
                
                String[] arguments = line.split(",",-1);
                Airport a = new Airport(Integer.parseInt(arguments[0]), arguments[1].replace("\"", ""), arguments[3].replace("\"", ""), arguments[8].replace("\"", ""));
                
                // guard from inconsistent data between countries.csv and airports.csv
                if(country_airports.containsKey(a.getCountryCode())) {
                    int count = country_airports.get(a.getCountryCode()) + 1;
                    country_airports.put(a.getCountryCode(), count);
                }
                else {
                    country_airports.put(a.getCountryCode(), 1);
                }
                
            }
        }
        catch(PositiveIntegerRequiredException | IOException ex) {
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
        
    }
    
    /**
     * This method prints:
     * - type of runways (based on surface) per country 
     * (all countries, not just the top 10 categories).
     * 
     * The way this method finds the countries associated to surfaces
     * is by going in the reverse direction (runway, airport, country).
     * There would not be a problem with the space complexity as long as
     * there is enough memory to save O(k) different surfaces to O (n) different
     * countries (i.e. total space complexity of O(k * n) ).
     * 
     * There are around 250 country entries and assuming that there would not be
     * more than a few (100) different surfaces, this method will produce an
     * acceptable solution for a theoretical time of O(x * y), where x are the 
     * entries in the runways file and y are the entries in the airports file.
     * 
     * Unfortunately, in practice that time is still rather long and the
     * algorithm did not finish executing in the near 15 min. To test whether
     * the method works correctly, it was tested on the first 1000 runway 
     * entries for which the algorithm finished in the next minute and less.
     */
    public static void printTypeOfRunwaysPerCountry() {
        
        // Ready (open) the files to be read
        readyFiles();
        
        /*
        The country code is the only way to link airports and cities. Therefore,
        we would use it as the key in a map to find the Country object.
        */
        Map<String, Country> countries = createMapCountryCodeCountry();
        
        try {
            
            String line1; // pointer (line reader) used with the runways
            String line2; //pointer (line reader) used with the airports
            
            runwaysReader.readLine(); // skip first line (column names)
            
            // int firstKrunways = 1000; // - correctness check
            
            while(((line1 = runwaysReader.readLine()) != null) /* && firstKrunways > 0 */ ) {
                // firstKrunways--; // - correctness check
                
                Runway r = Utils.readRunway(line1);
                
                // find associated airport
                airportsReader = new BufferedReader(new FileReader("resources/airports.csv")); //reopen the airports file
                airportsReader.readLine(); // skip first line (column names)
                
                while((line2 = airportsReader.readLine()) != null) {
                    
                    Airport a = Utils.readAirport(line2);
                    
                    if(r.getAirportReference() == a.getID()) {
                        // found associated airport
                        
                        // find the airport's country by the country code and add the surface
                        
                        /*
                        guard from inconsistency in the data by ignoring country codes
                        not present in the countries.csv file
                        */
                        if(countries.containsKey(a.getCountryCode()))
                            countries.get(a.getCountryCode()).addSurface(r.getSurface());
                        
                        break; // skip searching for airports with that runway
                    }
                    
                }
                
            }
            
            // print the results
            printCountriesSurfaces(countries);
            
        }
        catch(IOException ex) {
            System.err.println("Error: "+ ex.getMessage());
        }
        finally {
            // Close buffered readers
            closeFiles();
        }
        
    }
    
    /**
     * Creates a map for the countries based on country code as a key.
     * 
     * @return A map for countries, where 
     * key: country code and value: Country object.
     */
    private static Map<String, Country> createMapCountryCodeCountry() {
        
        Map<String, Country> countries = new HashMap<>();
        
        try {
            
            String line; // pointer (line reader) used with the countries
            
            countriesReader.readLine(); // skip first line (column names)
            
            while((line = countriesReader.readLine()) != null) {
                
                Country c = Utils.readCountry(line);
                
                countries.put(c.getCode(), c);
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
        
        return countries;
    }
    
    /**
     * Prints the map of countries and their assigned surfaces. The printing is
     * in the format:
     * 
     * Country: name
     * ---surface1
     * ---surface2
     * ---...
     * 
     * @param countries A map of countries, where the key is the country code and
     * the value is the Country object. The surfaces must have been added to the 
     * Country objects.
     */
    @SuppressWarnings("unchecked")
    private static void printCountriesSurfaces(Map<String,Country> countries) {
        
       Iterator it = countries.entrySet().iterator();
       while(it.hasNext()) {
           
           Map.Entry<String, Country> entry = (Map.Entry) it.next();
           Country c = entry.getValue();
           
           System.out.println("Country: " + c.getName());
           
           Iterator it2 = c.getSurfaces().entrySet().iterator();
           while(it2.hasNext()) {
               Map.Entry<String, Boolean> surface = (Map.Entry) it2.next();
               System.out.println("---" + surface.getKey());
           }
           
           System.out.println();
       }
        
    }
    
    /**
     * This method prints:
     * - the top 10 most common elements in "le_ident" column in file runways.csv.
     * 
     * There are too many runway entries (approximately 40 000). Therefore, 
     * a naive algorithm that takes space O(n), where n is the number of different 
     * "le_ident" elements, to hash the counts can run out of memory. 
     * 
     * A naive algorithm that takes time O(10 * m ^ m), where m is the number of 
     * runway entries, to keep track of the top 10 most frequent elements while 
     * traversing the file for each element and traversing the file to find its 
     * frequency will take unacceptable time.
     * 
     * By observing entries in the file, there do not appear to be too many 
     * different "le_ident" elements. Therefore, this method was first implemented 
     * (tested in run-time) with a hash map to store all the elements and their 
     * frequencies. Luckily, the test did not result in an OutOfMemory exception 
     * and thus we do not have to concern ourselves with a smarter solution for 
     * now.
     * 
     * However, if there were too many different elements, an approximate solution 
     * was going to be needed from the field of Streaming and Counting algorithms. 
     * Of course, one could also consider the Map-Reduce framework but that 
     * requires several machines.
     */
    public static void printTop10MostCommonElements() {
        
        // Ready (open) the files to be read
        readyFiles();
        
        Map<String,Integer> frequencies = createMapRunwayElementFrequency();
        
        // create maximum arrays (and associated labels)
        int[] top10maximum = new int[10];
        String[] top10maximumLabels = new String[10];
        
        // finding the maximum elements
        int i = 0;
        Iterator it = frequencies.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,Integer> entry = (Map.Entry) it.next();
            // for the first 10 entries in the hash map, just overwrite the values in the arrays
            if(i < 10) {
                top10maximum[i] = entry.getValue();
                top10maximumLabels[i] = entry.getKey();
                
                i++;
            }
            // for the rest entries, find min in arrays and substitute if necessary
            else { 
                int min = top10maximum[0];
                int minIndex = 0;
                
                for(int j = 1; j < 10; j++) {
                    if( min > top10maximum[j]) {
                        min = top10maximum[j];
                        minIndex = j;
                    }
                }
                
                if(entry.getValue() > min) {
                    top10maximum[minIndex] = entry.getValue();
                    top10maximumLabels[minIndex] = entry.getKey();
                }
            }
        }
        
        // print the top10 maximum elements with the associated count
        System.out.println("The top 10 elements are:");
        for(int j = 0; j < 10; j++) {
            System.out.println("---" + top10maximumLabels[j] + ": " + top10maximum[j]);
        }
        
        System.out.println();
        
        // Close buffered readers
        closeFiles();
        
    }

    private static Map<String, Integer> createMapRunwayElementFrequency() {
        
        Map<String, Integer> frequencies = new HashMap<>();
        
        try {
            
            String line; // pointer (line reader) used with the countries
            
            runwaysReader.readLine(); // skip first line (column names)
            
            while((line = runwaysReader.readLine()) != null) {
                
                Runway r = Utils.readRunway(line);
                
                if(frequencies.containsKey(r.getLocationIdentification())) {
                    int f = frequencies.get(r.getLocationIdentification()) + 1;
                    frequencies.put(r.getLocationIdentification(), f);
                }
                else {
                    frequencies.put(r.getLocationIdentification(), 1);
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
        
        return frequencies;
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
