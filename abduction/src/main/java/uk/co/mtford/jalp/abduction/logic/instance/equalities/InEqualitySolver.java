package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class InEqualitySolver implements IInEqualitySolver {

    // Deals with lists of disjunctions
    @Override
    public boolean executeSolver(Map<VariableInstance, IUnifiableAtomInstance> subst, List<InEqualityInstance> inEqualities) {
        // For each inequality.
            // Reduce the left and right of each inequality to a list of equalities.
            // Change this list of equalities into a disjunction of inequalities.
            // Reduce each new inequality until all have been reduced.
            // Only one of these needs to fail to unify.
        throw new UnsupportedOperationException(); // TODO
    }
}
