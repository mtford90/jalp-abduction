/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.*;
import uk.co.mtford.abduction.logic.PredicateInstance;
import uk.co.mtford.abduction.logic.EqualityInstance;

import uk.co.mtford.abduction.logic.program.Denial;

/** An ASystem store of collectables.
 *
 * @author mtford
 */
public class Store implements Comparable, Cloneable {
    
    private List<PredicateInstance> abducibles;
    private List<Denial> denials;
    private List<EqualityInstance> equalities;
    
    
    public Store() {
        abducibles = new LinkedList<PredicateInstance>();
        denials = new LinkedList<Denial>();
        equalities = new LinkedList<EqualityInstance>();
    }
    
    public void put(PredicateInstance abducible) {
        abducibles.add(abducible);
    }
    
    public void put(EqualityInstance equality) {
        equalities.add(equality);
    }
    
    public void put(Denial denial) {
        denials.add(denial);
    }
    
    public boolean contains(PredicateInstance abducible) {
        return abducibles.contains(abducible);
    }
    
    public boolean contains(Denial denial) {
        return denials.contains(denial);
    }
    
    public boolean contains(EqualityInstance equality) {
        return equalities.contains(equality);
    }
    
    public boolean remove(PredicateInstance abducible) {
        return abducibles.remove(abducible);
    }
    
    public boolean remove(EqualityInstance equality) {
        return equalities.remove(equality);
    }
    
    public boolean remove(Denial denial) {
        return denials.remove(denial);
    }
    
    public int numAbducibles() {
        return abducibles.size();
    }
    
    public int numDenials() {
        return denials.size();
    }
    
    public int numEqualities() {
        return equalities.size();
    }
    
    /** Very simple useless heuristic. Orders by num of 
     *  abducibles collected.
     * @param t
     * @return 
     */
    public int compareTo(Object t) {
        if (t == this) return 0;
        return abducibles.size() - ((Store)t).numAbducibles();
    }
    
    @Override
    public Object clone() {
        Store newStore = new Store();
        newStore.abducibles.addAll(abducibles);
        newStore.denials.addAll(denials);
        newStore.equalities.addAll(equalities);
        return newStore;
    }
    
}
