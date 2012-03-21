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
        NegationInstance thisClone = (NegationInstance) s.popGoal();
        s.putGoal(new DenialInstance(thisClone.subFormula));
        LinkedList<ASystemState> list = new LinkedList<ASystemState>();
        list.add(s);
        return list;
    }

    /** Implements rule N2
     * 
     * @param framework
     * @param s
     * @return 
     */
    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        ASystemState clonedState = (ASystemState) s.clone();
        DenialInstance denial = (DenialInstance) clonedState.popGoal();
        NegationInstance thisClone = (NegationInstance) denial.removeLiteral(0);
        clonedState.putGoal(thisClone.subFormula);
        possibleStates.add(0,clonedState);
        clonedState = (ASystemState) s.clone();
        denial = (DenialInstance) clonedState.popGoal();
        thisClone = (NegationInstance) denial.removeLiteral(0);
        clonedState.putGoal(denial);
        clonedState.putGoal(thisClone);
        possibleStates.add(0,clonedState);
        return possibleStates; 
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        return new NegationInstance((ILiteralInstance)subFormula.clone(variablesSoFar));
    }
    
    
    
}
