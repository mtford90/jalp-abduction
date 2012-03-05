/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.instance;

import java.util.LinkedList;
import java.util.List;
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        DenialInstance denialInstance = new DenialInstance();
        denialInstance.addbody(subFormula);
        newState.getGoals().add(0,this);
        possibleStates.add(newState);
        return possibleStates;
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
