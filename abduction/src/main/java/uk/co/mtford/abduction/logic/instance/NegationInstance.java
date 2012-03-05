/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.instance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.asystem.ASystemState;
import uk.co.mtford.abduction.asystem.DenialInstance;
import uk.co.mtford.abduction.asystem.IASystemInferable;

/**
 *
 * @author mtford
 */
public class NegationInstance implements ILiteralInstance {
    
    private ILiteralInstance subFormula;

    public NegationInstance(ILiteralInstance subFormula) {
        this.subFormula = subFormula;
    }

    public ILiteralInstance getSubFormula() {
        return subFormula;
    }

    public void setSubFormula(ILiteralInstance subFormula) {
        this.subFormula = subFormula;
    }
    
    @Override
    public Object clone() {
        return new NegationInstance((ILiteralInstance)subFormula.clone());
    }
    

    public boolean deepEquals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "not "+subFormula;
    }

    /** Implements rule N1
     * 
     * @param framework
     * @param s
     * @return 
     */
    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        ASystemState newState = (ASystemState) s.clone();
        NegationInstance thisCloned = (NegationInstance) s.getGoals().get(0);
        s.getGoals().remove(0);
        DenialInstance denialInstance = new DenialInstance();
        newState.getGoals().add(0,(NegationInstance)this.clone());
        possibleStates.add(newState);
        return possibleStates;
    }

    /** Implements rule N2
     * 
     * @param framework
     * @param s
     * @return 
     */
    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        // Branch 1. Assumes that the denial that negation was part of is
        // still at front of goal stack. (even if it has empty body).
        s.getGoals().remove(0);
        ASystemState clonedState = (ASystemState) s.clone();
        clonedState.getGoals().add(0,(ILiteralInstance)subFormula.clone());
        possibleStates.add(clonedState);  
        // Branch 2
        clonedState = (ASystemState) s.clone();
        clonedState.getGoals().add(0,(NegationInstance)this.clone());
        possibleStates.add(clonedState);
        return possibleStates;
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        return new NegationInstance((ILiteralInstance)subFormula.clone(variablesSoFar));
    }
    
    
    
}
