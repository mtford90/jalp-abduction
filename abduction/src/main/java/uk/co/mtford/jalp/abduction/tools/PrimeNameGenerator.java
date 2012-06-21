/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.tools;

/**
 * Generates prime names i.e. A,A',A'',A'''
 * @author mtford
 */
public class PrimeNameGenerator extends NameGenerator {
    private String name;
    
    public PrimeNameGenerator() {
        name = "";
    }
    public PrimeNameGenerator(String name) {
        this.name = name;
    }

    @Override
    public String getNextName() {
        name += "'";
        return name;
    }
    
    public static String getNextName(String name) {
        name += "'";
        return name;
    }
    
    
}
