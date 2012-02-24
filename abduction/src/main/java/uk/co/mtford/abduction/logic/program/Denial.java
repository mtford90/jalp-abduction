/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.program;

import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.State;
import uk.co.mtford.abduction.asystem.Store;
import uk.co.mtford.abduction.logic.AbstractPredicateInstance;
import uk.co.mtford.abduction.logic.LogicalFormulae;

/**
 *
 * @author mtford
 */
public class Denial implements IRule, ASystemInferable  {
    
    private List<AbstractPredicateInstance> clause;

    public Denial(List<AbstractPredicateInstance> clause) {
        this.clause = clause;
    }
    
    public void addClause(AbstractPredicateInstance p) {
        clause.add(p);
    }
    
    public void removeClause(AbstractPredicateInstance p) {
        clause.remove(p);
    }
    
    public boolean containsClause(AbstractPredicateInstance p) {
        return clause.contains(p);
    }
    
    public AbstractPredicateInstance getClause(int i) {
        return clause.get(i);
    }
    
    public AbstractPredicateInstance removeClause(int i) {
        return clause.remove(i);
    }
    
    public AbstractPredicateInstance pop() {
        return clause.get(0);
    }
    
    public void push(AbstractPredicateInstance p) {
        clause.add(0, p);
    }

    public boolean applyInferenceRule(List<LogicalFormulae> goals, State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
