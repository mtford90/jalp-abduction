/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.HashSet;
import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;

/**
 *
 * @author mtford
 */
public abstract class StateRewriter implements IAbductiveLogicProgrammingSystem {
    
    protected State currentState;
    protected AbductiveFramework abductiveFramework;
    protected boolean success;

    public StateRewriter(AbductiveFramework abductiveFramework) {
        currentState = new State();
        this.abductiveFramework = abductiveFramework;
        success = false;
    }
    
    private void resetSystem() {
        currentState = new State();
        success = false;
    }
    
    public State computeExplanation(Set<AbstractPredicate> query) {
        Set<AbstractPredicate> goals = new HashSet<AbstractPredicate>();
        goals.addAll(query);
        while (moveToNextState(goals)) {
            // Keep going.
        }
        if (success) {
            resetSystem();
            return currentState;
        }
        return null;
    }

    private boolean moveToNextState(Set<AbstractPredicate> goals)  {
        State newState;
        if (goals == null || goals.isEmpty()) return false;
        Set<AbstractPredicate> currentGoal = getNextGoal(goals); 
        if (currentGoal==null) {
            success = true;
            return false;
        }
        newState = getNextState(currentGoal);
        if (newState==null) return false;
        currentState = newState;
        return true;
    }
    
    /** Implements a goal selection strategy.
     * 
     * @param goals
     * @return 
     */
    protected abstract Set<AbstractPredicate> getNextGoal(Set<AbstractPredicate> goals);
    
    /** Implements a state transition strategy.
     * 
     * @param goal
     * @return 
     */
    protected abstract State getNextState(Set<AbstractPredicate> goal);
    
    
} 
