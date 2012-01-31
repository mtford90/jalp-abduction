package uk.co.mtford.abduction.asystem;

import java.util.Set;
import uk.co.mtford.abduction.logic.IUnifiable;
import uk.co.mtford.abduction.logic.program.Denial;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author mtford
 */
public abstract class ALP implements iALP {
    
    protected State currentState;
    
    public ALP() {
        currentState = State.getBlankState();
    }

    public Set<IUnifiable> compute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean moveToNextState() {
        Denial goal = getNextGoal();
        State state = getNextState(goal);
        if (state == null || goal == null) {
            return false;
        }
        return true;
    }
    
    public abstract Denial getNextGoal(); // Goal selection strategy.
    public abstract State getNextState(Denial goal); // State transition strategy.
    
}
