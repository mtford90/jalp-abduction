/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.tools;

/**
 *
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
