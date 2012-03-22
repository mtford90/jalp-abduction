/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.asystem.ASystemState;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;

/**
 *
 * @author mtford
 */
public class FalseInstance implements ILogicInstance, IASystemInferable {
     @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deepEquals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object clone() {
        return new FalseInstance();
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        return new TrueInstance();
    }

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        return new LinkedList<ASystemState>(); // Fail.
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        s.popGoal(); // Denial suceeded.
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        possibleStates.add(s);
        return possibleStates;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public String toString() {
        return "FALSE";
    }
    
    
    
}
