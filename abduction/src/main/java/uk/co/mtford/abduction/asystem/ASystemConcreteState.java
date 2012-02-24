/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import uk.co.mtford.abduction.AbductiveFramework;
import java.util.List;
import uk.co.mtford.abduction.logic.LogicalFormulaeInstance;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public class ASystemConcreteState extends ASystemState {

    public ASystemConcreteState(List<LogicalFormulaeInstance> goals, AbductiveFramework abductiveFramework) {
        super(goals, abductiveFramework);
    }

    @Override
    protected PredicateInstance getNextGoal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean stateTransition(LogicalFormulaeInstance goal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object clone() {
        ASystemConcreteState newState = new ASystemConcreteState(goals,abductiveFramework);
        newState.store=(ASystemStore) store.clone();
        return newState;
    }
    
}
