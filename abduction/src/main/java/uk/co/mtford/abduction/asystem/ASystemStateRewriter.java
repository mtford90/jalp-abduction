/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;
import uk.co.mtford.abduction.logic.program.Denial;

/**
 *
 * @author mtford
 */
public class ASystemStateRewriter extends StateRewriter {

    public ASystemStateRewriter(AbductiveFramework abductiveFramework) {
        super(abductiveFramework);
    }

    @Override
    protected AbstractPredicate getNextGoal(Set<AbstractPredicate> goals) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected State getNextState(AbstractPredicate goal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public State applyInferenceRule(Denial denial) {
        return null;
    }
    
}
