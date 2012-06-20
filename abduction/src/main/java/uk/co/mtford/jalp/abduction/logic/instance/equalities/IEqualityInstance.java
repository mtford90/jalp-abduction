package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.Map;

/**
 * e.g. X = 1
 */
public interface IEqualityInstance extends IInferableInstance {
    public boolean equalitySolve(Map<VariableInstance,IUnifiableInstance> assignments);
}
