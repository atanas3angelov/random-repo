/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

/**
 * A custom made exception class for requiring positive integers.
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class PositiveIntegerRequiredException extends Exception {
    
    public PositiveIntegerRequiredException(String message) {
        super(message);
    }
}
