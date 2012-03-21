/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.IAbductiveLogicProgrammingSystem;

/**
 *
 * @author mtford
 */
public abstract class ASystemStateRewriter implements IAbductiveLogicProgrammingSystem {
    
    final private static Logger LOGGER = Logger.getLogger(ASystemStateRewriter.class);
   
    protected AbductiveFramework abductiveFramework;
        
    public ASystemStateRewriter(AbductiveFramework abductiveFramework) {
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Computes an abductive explanation in the form of an ASystem
     *  store. Returns null if no possible explanation.
     * 
     * @param query
     * @param abductiveFramework
     * @return 
     */
    public List<ASystemStore> computeExplanation(List<IASystemInferable> query) {
        List<ASystemStore> possibleExplanations = new LinkedList<ASystemStore>();
        ASystemState currentState = new ASystemState(query); // Initial state.
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Initial state is:\n"+currentState);     
        IASystemInferable chosenGoal = getNextGoal(currentState);
        while ((currentState = stateTransition(chosenGoal,(ASystemState)currentState.clone()))!=null) {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("State transition has occured.\n"+currentState);      
            if (currentState.goals.isEmpty()) {
                possibleExplanations.add(currentState.store);
            } 
            chosenGoal = getNextGoal(currentState);
        }
        return possibleExplanations;
    }
    
    public Iterator<ASystemState> getStateIterator(final List<IASystemInferable> query) {
        return new Iterator<ASystemState>() {
            private ASystemState currentState;
            
            public boolean hasNext() {
                if (currentState!=null)
                    return hasMoreStates() || currentState.hasGoalsRemaining();
                return true;
            }

            public ASystemState next() {
                if (currentState==null) {
                    currentState = new ASystemState(query);
                }
                else {
                    IASystemInferable chosenGoal = getNextGoal(currentState);
                    currentState = stateTransition(chosenGoal,(ASystemState)currentState.clone());
                }
                return currentState;
            }

            public void remove() { // Skips a state.
                IASystemInferable chosenGoal = getNextGoal(currentState);
                stateTransition(chosenGoal,(ASystemState)currentState.clone());
            }
            
            public ASystemState getCurrentState() {
                return currentState;
            }
            
        };
    }
    
    protected abstract IASystemInferable getNextGoal(ASystemState state);
    
    /** Returns the next state. Returns null if not possible to move to next.
     * 
     * @param goal
     * @return 
     */
    protected abstract ASystemState stateTransition(IASystemInferable goal, ASystemState state);
    
    protected abstract boolean hasMoreStates();
    
    public abstract void reset();

    public AbductiveFramework getAbductiveFramework() {
        return abductiveFramework;
    }
    
} 
