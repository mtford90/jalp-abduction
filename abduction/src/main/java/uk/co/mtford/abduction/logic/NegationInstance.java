/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemState;
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

    public List<ASystemState> applyInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyDenialInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    
    
    
}
