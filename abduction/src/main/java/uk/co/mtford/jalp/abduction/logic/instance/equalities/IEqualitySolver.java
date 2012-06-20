package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Interface representing functions that an equality solver should have.
 */
public interface IEqualitySolver {
    /** Executes equality solver on the supplied equalities. Places substitutions made in subst.
     *
     * @param subst
     * @param equalities
     * @return True if solution exists.
     */
    public boolean execute(Map<VariableInstance, IUnifiableInstance> subst, List<EqualityInstance> equalities);
}
