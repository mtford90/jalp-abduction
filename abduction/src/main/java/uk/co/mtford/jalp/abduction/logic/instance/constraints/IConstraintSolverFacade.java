package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * A facade or interface to a constraint solver.
 */
public interface IConstraintSolverFacade {
    public List<Map<VariableInstance,IUnifiableInstance>> execute(Map<VariableInstance,IUnifiableInstance> subst, List<IConstraintInstance> listConstraints);
}
