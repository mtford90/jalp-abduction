/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import uk.co.mtford.abduction.logic.PredicateInstance;
import uk.co.mtford.abduction.logic.IUnifiableInstance;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public abstract class State implements Cloneable {
    protected List<PredicateInstance> goals;
    protected Store store;
    protected AbductiveFramework abductiveFramework;
    
    public State(List<PredicateInstance> goals, AbductiveFramework abductiveFramework) {
        this.goals = goals;
        store = new Store();
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Resets this state with new goals.
     * 
     * @param goals 
     */
    public void reset(List<PredicateInstance> goals, AbductiveFramework abductiveFramework) {
        this.goals = goals;
        store = new Store();
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Uses latest goal and inference rules to progress onto the next
     *  state.
     * 
     * @return 
     */
    public boolean moveToNextState() {
        if (goals.isEmpty()) return false;
        PredicateInstance goal = getNextGoal();
        if (goal == null) return false;
        if (stateTransition(goal)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public AbductiveFramework getAbductiveFramework() {
        return abductiveFramework;
    }
    
    public boolean hasGoalsRemaining() {
        return goals.size()>0;
    }
    
    /** Returns true if this state can be progressed.
     * 
     * @return 
     */
    public boolean canMoveToAnotherState() {
        if (((State)this.clone()).moveToNextState()) {
            return true;
        }
        return false;
    }

    /** Implements a goal selection strategy.
     * 
     * @param goals
     * @return 
     */
    protected abstract PredicateInstance getNextGoal();
    
    /** Implements a state transition strategy.
     * 
     * @param goal
     * @return 
     */
    protected abstract boolean stateTransition(PredicateInstance goal);
    
    @Override
    public abstract Object clone();

    public Store getStore() {
        return store;
    }
    
    
    
}
