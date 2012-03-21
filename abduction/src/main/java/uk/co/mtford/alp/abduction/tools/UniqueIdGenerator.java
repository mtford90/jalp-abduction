/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.tools;

/**
 *
 * @author mtford
 */
public class UniqueIdGenerator {
    private static int currentId = 1;
    public static int getUniqueId() {
        currentId++;
        return currentId;
    }
}
