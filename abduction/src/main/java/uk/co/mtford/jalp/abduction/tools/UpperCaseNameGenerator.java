/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.tools;

/**
 * Generates upper case names. i.e. a,b,...,z,aa,ab,...
 * @author mtford
 */
public class UpperCaseNameGenerator extends NameGenerator {
    
    private char currentLetter;
    private int currentNumLetters;
    
    public UpperCaseNameGenerator() {
        super();
        currentLetter = 'A';
        currentNumLetters = 1;
    }

    public String getNextName() {
        String name = "";
        for (int i=0;i<currentNumLetters;i++) {
            name+=getNextLetter();
        }
        return name;
    }
    
    private char getNextLetter() {
        char returnLetter = currentLetter;
        if (currentLetter=='Z') {
            currentLetter='A';
            currentNumLetters++;
        } 
        else {
            currentLetter++;
        }
        return returnLetter;
    }
    
}
