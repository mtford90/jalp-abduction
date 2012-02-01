/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;
import uk.co.mtford.abduction.logic.Equality;
import uk.co.mtford.abduction.logic.program.Denial;

/**
 *
 * @author mtford
 */
public class State implements Comparable {
    
    private Collection<AbstractPredicate> abducibles;
    private Collection<Denial> denials;
    private Collection<Equality> equalities;
    
    public State() {
        abducibles = new HashSet<AbstractPredicate>();
        denials = new HashSet<Denial>();
        equalities = new HashSet<Equality>();
    }
    
    public void put(AbstractPredicate abducible) {
        abducibles.add(abducible);
    }
    
    public void put(Equality equality) {
        equalities.add(equality);
    }
    
    public void put(Denial denial) {
        denials.add(denial);
    }
    
    public boolean contains(AbstractPredicate abducible) {
        return abducibles.contains(abducible);
    }
    
    public boolean contains(Denial denial) {
        return denials.contains(denial);
    }
    
    public boolean contains(Equality equality) {
        return equalities.contains(equality);
    }
    
    public boolean remove(AbstractPredicate abducible) {
        return abducibles.remove(abducible);
    }
    
    public boolean remove(Equality equality) {
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
        return abducibles.size() - ((State)t).numAbducibles();
    }
    
}
