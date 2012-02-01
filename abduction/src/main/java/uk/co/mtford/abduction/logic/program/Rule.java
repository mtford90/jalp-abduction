/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.program;

import java.util.HashSet;
import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;

/**
 *
 * @author mtford
 */
public class Rule implements IRule {
    private Set<AbstractPredicate> predicates;
    
    public Rule() {
        predicates = new HashSet<AbstractPredicate>();
    }
    
    public Rule(Set<AbstractPredicate> predicates) {
        this.predicates = predicates;
    }

    public Set<AbstractPredicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(Set<AbstractPredicate> predicates) {
        this.predicates = predicates;
    }
    
    public void addPredicate(AbstractPredicate predicate) {
        predicates.add(predicate);
    }
    
    public boolean removePredicate(AbstractPredicate predicate) {
        return predicates.remove(predicate);
    }
    
    public boolean containsPredicate(AbstractPredicate predicate) {
        return predicates.contains(predicate);
    }
    
}
