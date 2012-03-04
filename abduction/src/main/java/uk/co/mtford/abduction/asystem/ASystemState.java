/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.AbductiveFramework;

/**
 *
 * @author mtford
 */
public abstract class ASystemState implements Cloneable {
    protected List<IASystemInferable> goals;
    protected AbductiveFramework abductiveFramework;
    protected ASystemStore store;
    
    public ASystemState(List<IASystemInferable> goals, AbductiveFramework abductiveFramework) {
        this.goals = goals;
        store = new ASystemStore();
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Resets this state with new goals.
     * 
     * @param goals 
     */
    public void reset(List<IASystemInferable> goals, AbductiveFramework abductiveFramework) {
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
        IASystemInferable goal = getNextGoal();
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
    protected abstract IASystemInferable getNextGoal();
    
    /** Implements a state transition strategy.
     * 
     * @param goal
     * @return 
     */
    protected abstract boolean stateTransition(IASystemInferable goal);
    
    @Override
    public abstract Object clone();

    public ASystemStore getStore() {
        return store;
    }

    public List<IASystemInferable> getGoals() {
        return goals;
    }

    @Override
    public String toString() {
        return "ASystemState{" + "goals=" + goals + ", store=" + store + '}';
    }
    
}
