/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.program;

import java.util.List;
import uk.co.mtford.abduction.logic.AbstractPredicateInstance;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public class Rule implements IRule {
    private List<AbstractPredicateInstance> clause;
    private PredicateInstance head;

    public Rule(List<AbstractPredicateInstance> clause, PredicateInstance head) {
        this.clause = clause;
        this.head = head;
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

    public PredicateInstance getHead() {
        return head;
    }

    public void setHead(PredicateInstance head) {
        this.head = head;
    }
    
    
    
}
