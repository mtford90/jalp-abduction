/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public class ConcreteState extends State {

    public ConcreteState(List<PredicateInstance> goals, AbductiveFramework abductiveFramework) {
        super(goals, abductiveFramework);
    }

    @Override
    protected PredicateInstance getNextGoal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean stateTransition(PredicateInstance goal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object clone() {
        ConcreteState newState = new ConcreteState(goals,abductiveFramework);
        newState.store=(Store) store.clone();
        return newState;
    }
    
}
