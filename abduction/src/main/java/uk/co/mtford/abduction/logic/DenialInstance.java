/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.LinkedList;
import java.util.List;
import uk.co.mtford.abduction.asystem.IASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemState;

/**
 *
 * @author mtford
 */
public class DenialInstance implements IASystemInferable, Cloneable  {
    
    private List<IASystemInferable> clause;

    public DenialInstance(List<IASystemInferable> clause) {
        this.clause = clause;
    }
    
    public DenialInstance(IASystemInferable inferable) {
        clause = new LinkedList<IASystemInferable>();
        clause.add(inferable);
    }
    
    public DenialInstance() {
        clause = new LinkedList<IASystemInferable>();
    }
    
    public void addClause(IASystemInferable p) {
        clause.add(p);
    }
    
    public void removeClause(IASystemInferable p) {
        clause.remove(p);
    }
    
    public boolean containsClause(IASystemInferable p) {
        return clause.contains(p);
    }
    
    public IASystemInferable getClause(int i) {
        return clause.get(i);
    }
    
    public IASystemInferable removeClause(int i) {
        return clause.remove(i);
    }
    
    public IASystemInferable pop() {
        return clause.get(0);
    }
    
    public void push(IASystemInferable p) {
        clause.add(0, p);
    }

    public List<ASystemState> applyInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyDenialInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object clone() {
        DenialInstance clone = new DenialInstance();
        for (IASystemInferable inferable:clause) {
            clone.clause.add((IASystemInferable)inferable.clone());
        }
        return clone;
    }

    @Override
    public String toString() {
        return "DenialInstance{" + "clause=" + clause + '}';
    }

    public boolean deepEquals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
