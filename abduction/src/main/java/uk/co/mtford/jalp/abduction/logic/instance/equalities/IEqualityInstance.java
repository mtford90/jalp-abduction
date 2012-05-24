package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 21/05/2012
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public interface IEqualityInstance extends IInferableInstance {
    public boolean equalitySolve(Map<VariableInstance,IUnifiableAtomInstance> assignments);
}
