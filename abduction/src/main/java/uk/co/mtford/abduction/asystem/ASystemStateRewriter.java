/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public class ASystemStateRewriter implements IAbductiveLogicProgrammingSystem {
    
    protected boolean success;

    public ASystemStateRewriter() {
        success = false;
    }
    
    private void resetSystem() {
        success = false;
    }
    
    /** Computes an abductive explanation in the form of an ASystem
     *  store.
     * 
     * @param query
     * @param abductiveFramework
     * @return 
     */
    public Store computeExplanation(List<PredicateInstance> query, 
                                    AbductiveFramework abductiveFramework) {
        State state = new ConcreteState(query, abductiveFramework);
        while (state.moveToNextState()) {
            // Keep going.
        }
        if (state.hasGoalsRemaining()) {
            return null;
        }
        return state.getStore();
    }
    
} 
