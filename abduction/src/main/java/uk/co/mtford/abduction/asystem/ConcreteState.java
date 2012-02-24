/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.logic.AbstractPredicateInstance;

/**
 *
 * @author mtford
 */
public class ConcreteState extends State {

    public ConcreteState(List<AbstractPredicateInstance> goals, AbductiveFramework abductiveFramework) {
        super(goals, abductiveFramework);
    }

    @Override
    protected AbstractPredicateInstance getNextGoal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean stateTransition(AbstractPredicateInstance goal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object clone() {
        ConcreteState newState = new ConcreteState(goals,abductiveFramework);
        newState.store=(Store) store.clone();
        return newState;
    }
    
}
