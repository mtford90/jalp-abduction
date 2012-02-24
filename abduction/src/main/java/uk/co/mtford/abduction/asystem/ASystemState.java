/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.logic.LogicalFormulaeInstance;

/**
 *
 * @author mtford
 */
public abstract class ASystemState implements Cloneable {
    protected List<LogicalFormulaeInstance> goals;
    protected ASystemStore store;
    protected AbductiveFramework abductiveFramework;
    
    public ASystemState(List<LogicalFormulaeInstance> goals, AbductiveFramework abductiveFramework) {
        this.goals = goals;
        store = new ASystemStore();
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Resets this state with new goals.
     * 
     * @param goals 
     */
    public void reset(List<LogicalFormulaeInstance> goals, AbductiveFramework abductiveFramework) {
        this.goals = goals;
        store = new ASystemStore();
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Uses latest goal and inference rules to progress onto the next
     *  state.
     * 
     * @return 
     */
    public boolean moveToNextState() {
        if (goals.isEmpty()) return false;
        LogicalFormulaeInstance goal = getNextGoal();
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
        if (((ASystemState)this.clone()).moveToNextState()) {
            return true;
        }
        return false;
    }

    /** Implements a goal selection strategy.
     * 
     * @param goals
     * @return 
     */
    protected abstract LogicalFormulaeInstance getNextGoal();
    
    /** Implements a state transition strategy.
     * 
     * @param goal
     * @return 
     */
    protected abstract boolean stateTransition(LogicalFormulaeInstance goal);
    
    @Override
    public abstract Object clone();

    public ASystemStore getStore() {
        return store;
    }
    
}
