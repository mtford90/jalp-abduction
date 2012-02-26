/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.LinkedList;
import java.util.List;
import uk.co.mtford.abduction.asystem.IASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemState;

/**
 *
 * @author mtford
 */
public class NegationInstance implements IASystemInferable  {
    
    private IASystemInferable formula;

    public NegationInstance(IASystemInferable formula) {
        this.formula = formula;
    }

    public IASystemInferable getFormula() {
        return formula;
    }

    public void setFormula(IASystemInferable formula) {
        this.formula = formula;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NegationInstance other = (NegationInstance) obj;
        if (this.formula != other.formula && (this.formula == null || !this.formula.equals(other.formula))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.formula != null ? this.formula.hashCode() : 0);
        return hash;
    }

    public boolean deepEquals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NegationInstance other = (NegationInstance) obj;
        if (this.formula != other.formula && (this.formula == null || !this.formula.deepEquals(other.formula))) {
            return false;
        }
        return true;
    }

    public List<ASystemState> applyInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        ASystemState newState = (ASystemState) s.clone();
        currentGoals.add(0,new DenialInstance(formula));
        newState.getGoals().addAll(currentGoals);
        LinkedList<ASystemState> states = new LinkedList<ASystemState>();
        states.add(newState);
        return states;
    }

    public List<ASystemState> applyDenialInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        ASystemState newState = (ASystemState) s.clone();
        currentGoals.add(0,new DenialInstance(formula));
        List<IASystemInferable> newGoals = new LinkedList<IASystemInferable>();
        // Recreate the denial with the newly expanded negation.
        newGoals.add(new DenialInstance(currentGoals)); 
        newState.getGoals().addAll(newGoals);
        LinkedList<ASystemState> states = new LinkedList<ASystemState>();
        states.add(newState);
        return states;
    }
    
    public Object clone() {
        return new NegationInstance((IASystemInferable)formula.clone());
    }
    
}
