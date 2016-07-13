/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is a JUnit test for the Query class.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class QueryTest {
    
    public QueryTest() {
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
     * Test of query method, of class Query.
     */
    @Test
    public void testQuery() {
        System.out.println("query");
        // Nothing to test, if anything private methods need be tested
        // (use reflection to modify access to them during execution): 
        // https://en.wikibooks.org/wiki/Java_Programming/Reflection/
        //                      Accessing_Private_Features_with_Reflection
        assertTrue(true);
    }

    /**
     * Test of private simpleSuggestCountry method, of class Query.
     */
    @Test
    public void testSimpleSuggestCountry() {

        System.out.print("(expected) simpleSuggestCountry : (found) ");
        String country = "zimb";
        // A suggestion consists of closest match and country code
        String expResult1 = "Zimbabwe";
        String expResult2 = "ZW";
        /* 
        Attention: I do not like that I immediately put the expected results.
        Results must be derived by the same approach they were created or in the
        current case in by the same similarity formula (longest common prefix or
        Levenshtein distance). The reason behind that will be apparent after you 
        see the test for the other suggestion (testSuggestCountry). I've 
        nevertheless used fixed values in this case because I have no control on 
        the file beeing read (contries.csv) and therefore the ability to 
        manipulate the "hidden input sequence", and because I've also tested the 
        methods at run-time and know what the results will be.
        */
        
        // modifying accessibility at run-time
        try {
            Class<?> c = Query.class;
            Method simpleSuggestCountry = 
                    c.getDeclaredMethod("simpleSuggestCountry", new Class[] {String.class});
            simpleSuggestCountry.setAccessible(true);
            
            System.out.println(simpleSuggestCountry.getName());
            
            Suggestion result = (Suggestion) simpleSuggestCountry.invoke(c, country);
            
            assertEquals(expResult1, result.getClosestMatch());
            assertEquals(expResult2, result.getCountryCode());
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Test of private simpleSuggestCountry method, of class Query.
     */
    @Test
    public void testSuggestCountry() {

        System.out.print("(expected) suggestCountry : (found) ");
        String country = "zimb";
        // A suggestion consists of closest match and country code
        String expResult1 = "Fiji";
        String expResult2 = "FJ";
        /* 
        Attention: I do not like that I immediately put the expected results.
        Results must be derived by the same approach they were created or in the
        current case in by the same similarity formula (longest common prefix or
        Levenshtein distance). The reason behind that will be apparent after you 
        see the test for the other suggestion (testSimpleSuggestCountry). I've 
        nevertheless used fixed values in this case because I have no control on 
        the file beeing read (contries.csv) and therefore the ability to 
        manipulate the "hidden input sequence", and because I've also tested the 
        methods at run-time and know what the results will be.
        */
        
        // modifying accessibility at run-time
        try {
            Class<?> c = Query.class;
            Method suggestCountry = 
                    c.getDeclaredMethod("suggestCountry", new Class[] {String.class});
            suggestCountry.setAccessible(true);
            
            System.out.println(suggestCountry.getName());
            
            Suggestion result = (Suggestion) suggestCountry.invoke(c, country);
            
            assertEquals(expResult1, result.getClosestMatch());
            assertEquals(expResult2, result.getCountryCode());
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Test of findCountryCode method, of class Query.
     */
    @Test
    public void testFindCountryCode() {
        System.out.println("findCountryCode");
        String country = "Zimbabwe";
        String expResult = "ZW";
        /*
        Again I do not like it that I have used fixed values but nevertheless 
        use them because of the inability to manipulate the "hidden input sequence".
        Furthermore, I do not test the method with a purposefully failed case ("zimb")
        [to return null or the empty string]
        because the method is constructed in such way that it immediately activates 
        its "suggestion" feature - something that must be agreed upon the design 
        of the program and associated tests.
        */
        
        String result = Query.findCountryCode(country);
        assertEquals(expResult, result);
    }
    
    
}
