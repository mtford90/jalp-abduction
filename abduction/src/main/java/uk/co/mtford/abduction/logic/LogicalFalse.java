/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.asystem.State;
import uk.co.mtford.abduction.asystem.Store;

/**
 *
 * @author mtford
 */
public class LogicalFalse implements TruthValue {


    public boolean applyInferenceRule(List<LogicalFormulae> goals, State s) {
        return false;
    }


    
}
