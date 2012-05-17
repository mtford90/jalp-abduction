/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;


/** An ASystem store of collectables.
 *
 * @author mtford
 */
public class ASystemStore implements Comparable, Cloneable {
    
    private static final Logger LOGGER = Logger.getLogger(ASystemStore.class);
    
    private List<PredicateInstance> abducibles;
    private List<DenialInstance> denials;
    private List<IEqualityInstance> equalities;
    
    public ASystemStore() {
        abducibles = new LinkedList<PredicateInstance>();
        denials = new LinkedList<DenialInstance>();
        equalities = new LinkedList<IEqualityInstance>();
    }
    
    public void put(PredicateInstance abducible) {
        abducibles.add(abducible);
    }
    
    public void put(InequalityInstance equality) {
        equalities.add(equality);
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
        return equalities.contains(equality);
    }
    
    public boolean remove(PredicateInstance abducible) {
        return abducibles.remove(abducible);
    }
    
    public boolean remove(EqualityInstance equality) {
        return equalities.remove(equality);
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
        return equalities.size();
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
        Map<String,VariableInstance> variablesSoFar = new HashMap<String,VariableInstance>();
        ASystemStore newStore = new ASystemStore();
        for (PredicateInstance abducible:abducibles) {
            newStore.abducibles.add((PredicateInstance)abducible.clone(variablesSoFar));
        }
        for (DenialInstance denial:denials) {
            newStore.denials.add((DenialInstance)denial.clone(variablesSoFar));
        }
        for (IEqualityInstance equality:equalities) {
            newStore.equalities.add((IEqualityInstance)equality.clone(variablesSoFar));
        }
        return newStore;
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        ASystemStore newStore = new ASystemStore();
        for (PredicateInstance abducible:abducibles) {
            newStore.abducibles.add((PredicateInstance)abducible.clone(variablesSoFar));
        }
        for (DenialInstance denial:denials) {
            newStore.denials.add((DenialInstance)denial.clone(variablesSoFar));
        }
        for (IEqualityInstance equality:equalities) {
            newStore.equalities.add((IEqualityInstance)equality.clone(variablesSoFar));
        }
        return newStore;
    }
    
    public List<PredicateInstance> getAbducibles() {
        return abducibles;
    }

    public List<DenialInstance> getDenials() {
        return denials;
    }

    public List<IEqualityInstance> getEqualities() {
        return equalities;
    }
    
    

    @Override
    public String toString() {
        String output="{";
        output+="{";
        if (!abducibles.isEmpty()) {
            for (PredicateInstance abducible:abducibles) {
                output+=abducible+", ";
            }
            output=output.substring(0, output.length()-2);
        }
        output+="}, ";
        output+="{";
        if (!denials.isEmpty()) {
            for (DenialInstance denial:denials) {
                output+=denial+", ";
            }
            output=output.substring(0, output.length()-2);           
        }
        output+="}, ";
        output+="{";
        if (!equalities.isEmpty()) {
            for (IEqualityInstance equality:equalities) {
                output+=equality+", ";
            }
            output=output.substring(0, output.length()-2);
        }
        output+="}}";
        return output;
        
    }
    
    
    
}
