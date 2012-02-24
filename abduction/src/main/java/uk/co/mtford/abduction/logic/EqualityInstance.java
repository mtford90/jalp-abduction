/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.State;

/**
 *
 * @author mtford
 */
public class EqualityInstance implements ASystemInferable  {

    PredicateInstance left;
    PredicateInstance right;

    // TODO: Equality solver implementation.
    public LogicalFormulaeInstance solveEquality() {
        return left.equalitySolve(right);
    }

    public boolean applyInferenceRule(List<LogicalFormulaeInstance> goals, State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
