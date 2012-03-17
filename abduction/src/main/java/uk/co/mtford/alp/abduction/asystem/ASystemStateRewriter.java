/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

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
    public ASystemStore computeExplanation(List<IASystemInferable> query) {
        ASystemState currentState = new ASystemState(query); // Initial state.
        IASystemInferable chosenGoal;
        while ((chosenGoal = getNextGoal(currentState))!=null) {
            currentState = stateTransition(chosenGoal,(ASystemState)currentState.clone());
            if (currentState==null) {
                return null; // Failed to move to another state.
            }
        }
        return currentState.getStore();
    }
    
    protected abstract IASystemInferable getNextGoal(ASystemState state);
    
    /** Returns the next state. Returns null if not possible to move to next.
     * 
     * @param goal
     * @return 
     */
    protected abstract ASystemState stateTransition(IASystemInferable goal, ASystemState state);
    
    public abstract void reset();

    public AbductiveFramework getAbductiveFramework() {
        return abductiveFramework;
    }
    
    

    
} 
