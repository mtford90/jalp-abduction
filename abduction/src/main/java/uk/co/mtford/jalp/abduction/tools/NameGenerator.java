/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.tools;

/**
 * Abstract class that defines a type of class that generates unique names sequentially.
 * Also presents static instantiations of some implementations of name generators.
 * @author mtford
 */
public abstract class NameGenerator {
    
    public static LowerCaseNameGenerator lowerCaseNameGen = new LowerCaseNameGenerator();
    public static UpperCaseNameGenerator upperCaseNameGen = new UpperCaseNameGenerator();
    public static PrimeNameGenerator primeNameGen = new PrimeNameGenerator();
    
    public abstract String getNextName();
   
}
