/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.program;

import java.util.List;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public class Rule {
    private PredicateInstance head;
    private List<PredicateInstance> clause;
    
    public Rule(List<PredicateInstance> clause, PredicateInstance head) {
        this.clause = clause;
        this.head = head;
    }
    
    public void addClause(PredicateInstance p) {
        clause.add(p);
    }
    
    public void removeClause(PredicateInstance p) {
        clause.remove(p);
    }
    
    public boolean containsClause(PredicateInstance p) {
        return clause.contains(p);
    }
    
    public PredicateInstance getClause(int i) {
        return clause.get(i);
    }
    
    public PredicateInstance removeClause(int i) {
        return clause.remove(i);
    }
    
    public PredicateInstance pop() {
        return clause.get(0);
    }
    
    public void push(PredicateInstance p) {
        clause.add(0, p);
    }

    public PredicateInstance getHead() {
        return head;
    }

    public void setHead(PredicateInstance head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Rule{" + "head=" + head + ", clause=" + clause + '}';
    }
    
    
}
