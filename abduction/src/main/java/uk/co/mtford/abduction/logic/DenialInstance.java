/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemState;
import uk.co.mtford.abduction.logic.LogicalFormulaeInstance;

/**
 *
 * @author mtford
 */
public class DenialInstance implements ASystemInferable  {
    
    private List<ASystemInferable> clause;

    public DenialInstance(List<ASystemInferable> clause) {
        this.clause = clause;
    }
    
    public void addClause(ASystemInferable p) {
        clause.add(p);
    }
    
    public void removeClause(ASystemInferable p) {
        clause.remove(p);
    }
    
    public boolean containsClause(ASystemInferable p) {
        return clause.contains(p);
    }
    
    public ASystemInferable getClause(int i) {
        return clause.get(i);
    }
    
    public ASystemInferable removeClause(int i) {
        return clause.remove(i);
    }
    
    public ASystemInferable pop() {
        return clause.get(0);
    }
    
    public void push(ASystemInferable p) {
        clause.add(0, p);
    }

    public boolean applyInferenceRule(ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
