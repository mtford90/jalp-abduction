/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.tools;

import java.util.HashMap;

/**
 *
 * @author mtford
 */
public class UniqueIdGenerator {
    private static HashMap<String, Integer> numberMap = new HashMap<String, Integer>();
    public static int getUniqueId(String name) {
        if (numberMap.containsKey(name)) {
            numberMap.put(name,numberMap.get(name)+1);
            return numberMap.get(name);
        }
        else {
            numberMap.put(name,1);
            return 1;
        }
    }
}
