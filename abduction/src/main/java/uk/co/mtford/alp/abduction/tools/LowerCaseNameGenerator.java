/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.tools;

/**
 *
 * @author mtford
 */
public class LowerCaseNameGenerator extends NameGenerator {
    
    private char currentLetter;
    private int currentNumLetters;
    
    public LowerCaseNameGenerator() {
        super();
        currentLetter = 'a';
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
        if (currentLetter=='z') {
            currentLetter='a';
            currentNumLetters++;
        } 
        else {
            currentLetter++;
        }
        return returnLetter;
    }
    
}
