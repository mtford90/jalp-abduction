package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class EqualitySolver implements IEqualitySolver {
    public boolean execute(Map<VariableInstance, IUnifiableInstance> subst, List<EqualityInstance> equalities) {
        for (IEqualityInstance equality:equalities) {
            if (equality.equalitySolve(subst)) continue;
            return false;
        }
        return true;
    }
}
