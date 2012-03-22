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
import uk.co.mtford.alp.abduction.asystem.DenialInstance;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;

/**
 *
 * @author mtford
 */
public class TrueInstance implements ILogicInstance, IASystemInferable {
    
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean deepEquals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object clone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        s.popGoal(); // carry on
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        possibleStates.add(s);
        return possibleStates;
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        DenialInstance d = (DenialInstance) s.popGoal();
        d.popLiteral();
        if (d.getBody().isEmpty()) return possibleStates; // Failed.
        s.putGoal(d);
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
        return "TRUE";
    }
    
    
    
}
