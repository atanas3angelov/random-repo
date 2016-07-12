/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

import java.util.HashMap;
import java.util.Map;

/**
 * The class allows the creation of Country objects with an id, code, name and 
 * one additional HashMap to keep track of surfaces based on task requirement 2.2.
 * Additional attributes like continent, wikipedia_link or keywords
 * are not included since they are not viable for the task
 * but the class can easily be extended. The class is immutable
 * (objects cannot be modified after creation except for adding surfaces)
 * but it allows access to its attributes.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class Country {
    
    private final int id;
    private final String code;
    private final String name;
    
    private Map<String,Boolean> surfaces;
    
    /**
     * The constructor for the country, requires an id, a code and a name.
     * @param id Requires a positive integer. Be careful, the id is not necessarily unique.
     * @param code The code of the country as a String.
     * @param name The name of the country as a String.
     * @throws PositiveIntegerRequiredException Requires that the id is a positive integer.
     */
    public Country(int id, String code, String name) throws PositiveIntegerRequiredException {
        if(id < 0) throw new PositiveIntegerRequiredException("The id of a country cannot be negative.");
        this.id = id;
        this.code = code;
        this.name = name;
        surfaces = new HashMap<>();
    }
    
    /**
     * 
     * @return Returns the id of the country.
     */
    public int getID() {
        return id;
    }
    
    /**
     * 
     * @return Returns the code of the country. Ex. US
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 
     * @return Returns the name of the country.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a surface to the map of surfaces for the country.
     * @param surface A surface as a String.
     */
    public void addSurface(String surface) {
        if(!surfaces.containsKey(surface))
            surfaces.put(surface, Boolean.TRUE);
    }
    
    /**
     * 
     * @return Returns a copy of the map of surfaces. The original map is kept safe.
     */
    public Map<String, Boolean> getSurfaces() {
        return new HashMap<>(surfaces);
    }
    
    /**
     * 
     * @return The default toString() method has been overwritten to return the country
     * in the format "id code name".
     */
    public String toString() {
        return id + " " + code + " " + name;
    }
}
