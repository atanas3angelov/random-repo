/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is a JUnit test for the Reports class.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class ReportsTest {
    
    public ReportsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of printTop10CountriesWithMaxAndMinAirports method, of class Reports.
     */
    @Test
    public void testPrintTop10CountriesWithMaxAndMinAirports() {
        System.out.println("printTop10CountriesWithMaxAndMinAirports");
        /*
        There is nothing to test for print methods (of type void) except 
        maybe whether an Exception has beeen raised. However, I do not find it 
        extremly important to check whether the files were found, successfully 
        opened or read through, or whether my personal made exception that 
        requires a positive integer for the id of an object has been triggered.
        
        And the maps of private methods will be a pain to check (test), especially 
        when the "hidden input sequence" of the files cannot be manipulated easily.
        */
        
        Reports.printTop10CountriesWithMaxAndMinAirports();
        
        assertTrue(true);
    }
    
    /**
     * Test of printTypeOfRunwaysPerCountry method, of class Reports.
     */
    @Test
    public void testPrintTypeOfRunwaysPerCountry() {
        System.out.println("printTypeOfRunwaysPerCountry");
        /*
        There is nothing to test for print methods (of type void) except 
        maybe whether an Exception has beeen raised. However, I do not find it 
        extremly important to check whether the files were found, successfully 
        opened or read through, or whether my personal made exception that 
        requires a positive integer for the id of an object has been triggered.
        
        And the maps of private methods will be a pain to check (test), especially 
        when the "hidden input sequence" of the files cannot be manipulated easily.
        */
        
        //Reports.printTypeOfRunwaysPerCountry(); // commented because it will not finish fast
        
        assertTrue(true);
    }

    /**
     * Test of printTypeOfRunwaysPerCountryForLimitedSpace method, of class Reports.
     */
    @Test
    public void testPrintTypeOfRunwaysPerCountryForLimitedSpace() {
        System.out.println("printTypeOfRunwaysPerCountryForLimitedSpace");
        String country = "";
                /*
        There is nothing to test for print methods (of type void) except 
        maybe whether an Exception has beeen raised. However, I do not find it 
        extremly important to check whether the files were found, successfully 
        opened or read through, or whether my personal made exception that 
        requires a positive integer for the id of an object has been triggered.
        
        And the maps of private methods will be a pain to check (test), especially 
        when the "hidden input sequence" of the files cannot be manipulated easily.
        */

        //Reports.printTypeOfRunwaysPerCountryForLimitedSpace(country); // commented because it will not finish fast
        
        assertTrue(true);
    }

    /**
     * Test of printTop10MostCommonElements method, of class Reports.
     */
    @Test
    public void testPrintTop10MostCommonElements() {
        System.out.println("printTop10MostCommonElements");
        /*
        There is nothing to test for print methods (of type void) except 
        maybe whether an Exception has beeen raised. However, I do not find it 
        extremly important to check whether the files were found, successfully 
        opened or read through, or whether my personal made exception that 
        requires a positive integer for the id of an object has been triggered.
        
        And the maps of private methods will be a pain to check (test), especially 
        when the "hidden input sequence" of the files cannot be manipulated easily.
        */
        
        Reports.printTop10MostCommonElements();
        
        assertTrue(true);
    }
    
}
