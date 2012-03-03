/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.*;
import uk.co.mtford.abduction.logic.PredicateInstance;


/** An ASystem store of collectables.
 *
 * @author mtford
 */
public class ASystemStore implements Comparable, Cloneable {
    
    private List<PredicateInstance> abducibles;
    private List<DenialInstance> denials;
    private List<EqualityInstance> inequalities;
    
    public ASystemStore() {
        abducibles = new LinkedList<PredicateInstance>();
        denials = new LinkedList<DenialInstance>();
        inequalities = new LinkedList<EqualityInstance>();
    }
    
    public void put(PredicateInstance abducible) {
        abducibles.add(abducible);
    }
    
    public void put(EqualityInstance equality) {
        inequalities.add(equality);
    }
    
    public void put(DenialInstance denial) {
        denials.add(denial);
    }
    
    public boolean contains(PredicateInstance abducible) {
        return abducibles.contains(abducible);
    }
    
    public boolean contains(DenialInstance denial) {
        return denials.contains(denial);
    }
    
    public boolean contains(EqualityInstance equality) {
        return inequalities.contains(equality);
    }
    
    public boolean remove(PredicateInstance abducible) {
        return abducibles.remove(abducible);
    }
    
    public boolean remove(EqualityInstance equality) {
        return inequalities.remove(equality);
    }
    
    public boolean remove(DenialInstance denial) {
        return denials.remove(denial);
    }
    
    public int numAbducibles() {
        return abducibles.size();
    }
    
    public int numDenials() {
        return denials.size();
    }
    
    public int numEqualities() {
        return inequalities.size();
    }
    
    /** Very simple useless heuristic. Orders by num of 
     *  abducibles collected.
     * @param t
     * @return 
     */
    public int compareTo(Object t) {
        if (t == this) return 0;
        return abducibles.size() - ((ASystemStore)t).numAbducibles();
    }
    
    @Override
    public Object clone() {
        ASystemStore newStore = new ASystemStore();
        for (PredicateInstance abducible:abducibles) {
            newStore.abducibles.add((PredicateInstance)abducible.clone());
        }
        for (DenialInstance denial:denials) {
            newStore.denials.add((DenialInstance)denial.clone());
        }
        List<EqualityInstance> equalities;
        return newStore;
    }

    @Override
    public String toString() {
        String output="S:{";
        output+="{";
        for (PredicateInstance abducible:abducibles) {
            output+=abducible+", ";
        }
        output=output.substring(0, output.length());
        output+="}, ";
        output+="{";
        for (DenialInstance denial:denials) {
            output+=denial+", ";
        }
        output=output.substring(0, output.length());
        output+="}, ";
        output+="{";
        for (EqualityInstance equality:inequalities) {
            output+=equality+", ";
        }
        output=output.substring(0, output.length());
        output+="}}";
        return output;
        
    }
    
    
    
}
