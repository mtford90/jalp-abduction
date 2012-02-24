/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemState;

/**
 *
 * @author mtford
 */
public class LogicalTrueInstance implements TruthValueInstance {

    public boolean applyInferenceRule(List<LogicalFormulaeInstance> goals, ASystemState s) {
        return true; // No need to modify anything.
    }
    
     public boolean deepEquals(Object obj) {
        return this.equals(obj);
    }

    public boolean applyInferenceRule(ASystemState s) {
        return false;
    }
    
}
