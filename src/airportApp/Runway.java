/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

/**
 * The class allows the creation of Runway objects with an id,
 * airport reference (airport_ref), airport identification (airport_ident), 
 * surface, and location identification (le_ident).
 * Additional attributes like length_ft, width_ft, lighted, closed, 
 * le_latitude_deg, le_longitude_deg, le_elevation_ft, le_heading_deg, 
 * le_displaced_treshold_ft, he_ident, he_latitude_deg, he_longitude_deg, 
 * he_elevation_ft, he_heading_deg or he_displaced_treshold_ft
 * are not included since they are not viable for the task
 * but the class can easily be extended. The class is immutable
 * (objects cannot be modified after creation) 
 * but it allows access to its attributes.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Runway {
    
    private final int id;
    private final int airport_ref;
    private final String airport_ident;
    private final String surface;
    private final String le_ident;
   
    /**
     * The constructor for the runway, requires an id, 
     * an airport reference (airport_ref), an airport identification (airport_ident),
     * a surface and a location identification (le_ident).
     * @param id Requires a positive integer. Be careful, the id is not necessarily unique.
     * @param airport_ref An airport reference as an integer.
     * @param airport_ident An airport identification as a String.
     * @param surface A surface as a String.
     * @param le_ident A location identification as a String.
     * @throws PositiveIntegerRequiredException Requires that the id is a positive integer.
     */
    public Runway(int id, int airport_ref, String airport_ident, String surface, String le_ident) throws PositiveIntegerRequiredException {
        if( (id < 0) || (airport_ref < 0) ) throw new PositiveIntegerRequiredException("The id of a runway cannot be negative.");
        this.id = id;
        this.airport_ref = airport_ref;
        this.airport_ident = airport_ident;
        this.surface = surface;
        this.le_ident = le_ident;
    }
    
    /**
     * 
     * @return Returns the runway id.
     */
    public int getID() {
        return id;
    }
    
    /**
     * 
     * @return Returns the airport reference.
     */
    public int getAirportReference() {
        return airport_ref;
    }
    
    /**
     * 
     * @return Returns the airport identification.
     */
    public String getAirportIdentification() {
        return airport_ident;
    }
    
    /**
     * 
     * @return Returns the surface.
     */
    public String getSurface() {
        return surface;
    }
    
    /**
     * 
     * @return Returns the location identification.
     */
    public String getLocationIdentification() {
        return le_ident;
    }
    
    /**
     * 
     * @return The default toString() method has been overwritten to return the runway
     * in the format "id airportReference airportIdentification surface".
     */
    public String toString() {
        return "" + id + " " + airport_ref + " " + airport_ident + " " + surface;
    }
}
