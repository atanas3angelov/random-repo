/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportApp;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is the main class that provides a command line interface for the task.
 * The program provides a main menu with two options:
 * - make a query
 * - ask for a report.
 * 
 * @author Atanas Angelov (atanas3angelov@gmail.com)
 */
public class LunatechTask {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Ready command line input
        Scanner in = new Scanner(System.in);
        
        // Command line menu
        while(true){
            
            System.out.println("Select option: \n"
                    + "1: Query \n"
                    + "2: Reports \n"
                    + "3: exit");
            int num = -1;
            
            try {
                num = in.nextInt();
            }
            catch (InputMismatchException ex) {
                in.nextLine();
                System.out.println("You must enter a number. Try again.");
            }
                
            switch(num){
                case 1: queryOption(); break;
                case 2: reportsOption(); break;
                case 3: System.exit(0);
                default : break;
            }
        }
    }
    
    /**
     * Calls a query with the user input.
     */
    private static void queryOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter country name or code: ");
        String country = input.nextLine();
        Query.query(country);
        
        System.out.println();
    }
    
    /**
     * Calls a report for:
     * - 10 countries with highest number of airports (with count)
     *      and 10 countries with lowest number of airports (with count)
     * - type of runways (based on surface) per country
     *      (all countries, not just the top 10 categories)
     * - the top 10 most common elements in "le_ident" column in file runways.csv
     */
    private static void reportsOption() {
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Select report: \n"
                + "1: Top 10 countries with most and least airports.\n"
                + "2: Type of runways (surface) per country.\n"
                + "3: Top 10 most common runway le_ident elements.\n"
                + "4: Return to main menu.");
        int num = -1;
        
        try {
            num = input.nextInt();
        }
        catch (InputMismatchException ex) {
            input.nextLine();
            System.out.println("Invalid input.");
        }
        
        switch(num){
            case 1: Reports.printTop10CountriesWithMaxAndMinAirports(); break;
            //case 2: Reports.printTypeOfRunwaysPerCountryForLimitedSpace("Bulgaria"); break; - used for a specific country
            case 2: Reports.printTypeOfRunwaysPerCountryForLimitedSpace(null); break;
            case 3: Reports.printTop10MostCommonElements(); break;
            default : break;
        }
        
        System.out.println();
    }
    
}
