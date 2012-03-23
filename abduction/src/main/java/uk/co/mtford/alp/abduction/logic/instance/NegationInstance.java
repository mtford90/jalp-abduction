/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.asystem.ASystemState;
import uk.co.mtford.alp.abduction.asystem.DenialInstance;

/**
 *
 * @author mtford
 */
public class NegationInstance implements ILiteralInstance {
    
    private static final Logger LOGGER = Logger.getLogger(NegationInstance.class);
    
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
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inference rule N1 to "+this);
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        NegationInstance thisClone = (NegationInstance) s.popGoal();
        List<ILiteralInstance> literalList = new LinkedList<ILiteralInstance>();
        literalList.add(subFormula);
        DenialInstance d = new DenialInstance(literalList); // Terms in subformula are free variables.
        s.putGoal(d);
        possibleStates.add(s);
        return possibleStates;
    }

    /** Implements rule N2
     * 
     * @param framework
     * @param s
     * @return 
     */
    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inference rule N2 to "+this);
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        ASystemState clonedState = (ASystemState) s.clone();
        DenialInstance clonedDenial = (DenialInstance) clonedState.popGoal();
        NegationInstance clonedThis = (NegationInstance) clonedDenial.popLiteral();
        clonedState.putGoal(clonedThis.subFormula);
        possibleStates.add(clonedState);
        // OR
        clonedState = (ASystemState) s.clone();
        clonedDenial = (DenialInstance) clonedState.popGoal();
        clonedThis = (NegationInstance) clonedDenial.popLiteral();
        clonedState.putGoal(clonedDenial);
        clonedState.putGoal(clonedThis);
        possibleStates.add(clonedState);
        return possibleStates;
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        return new NegationInstance((ILiteralInstance)subFormula.clone(variablesSoFar));
    }
    
    
    
}
