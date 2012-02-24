/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.IAbductiveLogicProgrammingSystem;
import uk.co.mtford.abduction.logic.LogicalFormulaeInstance;

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
    public ASystemStore computeExplanation(List<LogicalFormulaeInstance> query, 
                                    AbductiveFramework abductiveFramework) {
        ASystemState state = new ASystemConcreteState(query, abductiveFramework);
        while (state.moveToNextState()) {
            // Keep going.
        }
        if (state.hasGoalsRemaining()) {
            return null;
        }
        return state.getStore();
    }
    
} 
