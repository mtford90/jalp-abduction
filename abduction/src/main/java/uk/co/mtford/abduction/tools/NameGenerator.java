/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.tools;

/**
 *
 * @author mtford
 */
public abstract class NameGenerator {
    
    public static LowerCaseNameGenerator lowerCaseNameGen = new LowerCaseNameGenerator();
    public static UpperCaseNameGenerator upperCaseNameGen = new UpperCaseNameGenerator();
    public static PrimeNameGenerator primeNameGen = new PrimeNameGenerator();
    
    public abstract String getNextName();
   
}
