/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem.rules;

import java.lang.Thread.State;
import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;

/**
 *
 * @author mtford
 */
public abstract class InferenceRule {
    protected InferenceRule next;

    public InferenceRule getNext() {
        return next;
    }

    public void setNext(InferenceRule next) {
        this.next = next;
    }
    
    public State[] applyRuleChain(Set<AbstractPredicate> goal, State s) throws InferenceRuleException {
        if (ruleApplies(goal)) {
            return applyRule(goal,s);
        }
        if (next!=null) {
            return next.applyRuleChain(goal, s);
        }
        throw new InferenceRuleException(); // No inference rule in the chain picked the goal up.
    }
    
    
    /** Returns whether or not this rule applies to the 
     *  given goal.
     * 
     * @param goal
     * @return 
     */
    protected abstract boolean ruleApplies(Set<AbstractPredicate> goal);
    
    /** Applies the rule and returns an array of possible states.
     * 
     * @param goal
     * @param s
     * @return 
     */
    protected abstract State[] applyRule(Set<AbstractPredicate> goal, State s);
}
