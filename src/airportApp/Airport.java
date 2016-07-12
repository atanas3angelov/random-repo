/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

import java.util.ArrayList;
import java.util.List;

/**
 * The class allows the creation of Airport objects with an id,
 * identification (ident), name and country reference code (iso_country).
 * Additional attributes like type, latitude_deg, longitude_deg, elevation_ft,
 * continent, iso_region, municipality, scheduled_service, gps_code, iata_code,
 * local_code, home_link, wikipedia_link or keywords
 * are not included since they are not viable for the task
 * but the class can easily be extended. The class is immutable
 * (objects cannot be modified after creation except for adding runaways) 
 * but it allows access to its attributes.
 * The class uses an array to keep track of runways.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Airport {
    
    private final int id;
    private final String ident;
    private final String name;
    private final String iso_country;
    private List<Runway> runways;
    
    /**
     * The constructor for the airport, requires an id, an identification (ident),
     * a name and a country code (iso_country).
     * 
     * @param id Requires a positive integer. Be careful, the id is not necessarily unique.
     * @param ident The identification of the airport as a String.
     * @param name The name of the airport as a String.
     * @param iso_country The code of the country as a String.
     * @throws PositiveIntegerRequiredException Requires that the id is a positive integer.
     */
    public Airport(int id, String ident, String name, String iso_country) throws PositiveIntegerRequiredException {
        if(id < 0) throw new PositiveIntegerRequiredException("The id of a airport cannot be negative.");
        this.id = id;
        this.ident = ident;
        this.name = name;
        this.iso_country = iso_country;
        runways = new ArrayList<>();
    }
    
    /**
     * 
     * @return Returns the id of an airport.
     */
    public int getID() {
        return id;
    }
    
    /**
     * 
     * @return Returns the identification of an airport. Ex. 00AK
     */
    public String getIdentification() {
        return ident;
    }
    
    /**
     * 
     * @return Returns the name of an airport.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return Returns the country code of an airport. Ex. US
     */
    public String getCountryCode() {
        return iso_country;
    }
    
    /**
     * 
     * @return Returns a copy of the runways. The original array is kept safe.
     */
    public List<Runway> getRunways() {
        List<Runway> copy = new ArrayList<>();
        runways.stream().forEach((r) -> {
            copy.add(r);
        });
        return copy;
    }
    
    /**
     * Adds a runway to the list of runways for the airport.
     * @param r A runway object.
     */
    public void addRunway(Runway r) {
        runways.add(r);
    }
    
    /**
     * 
     * @return The default toString() method has been overwritten to return the country
     * in the format "id identification name country_code".
     */
    public String toString() {
        return "" + id + " " + ident + " " + name + " " + iso_country;
    }
}
