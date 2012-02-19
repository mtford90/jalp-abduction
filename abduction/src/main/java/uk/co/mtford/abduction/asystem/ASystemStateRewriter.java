/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;

/**
 *
 * @author mtford
 */
public class ASystemStateRewriter extends StateRewriter {

    public ASystemStateRewriter(AbductiveFramework abductiveFramework) {
        super(abductiveFramework);
    }

    @Override
    protected Set<AbstractPredicate> getNextGoal(Set<Set<AbstractPredicate>> goals) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected State getNextState(Set<AbstractPredicate> goal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
