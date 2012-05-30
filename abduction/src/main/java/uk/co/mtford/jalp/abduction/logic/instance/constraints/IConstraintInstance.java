package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public interface IConstraintInstance extends IInferableInstance {
    boolean reduceToChoco(List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints);
    boolean reduceToNegativeChoco(List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints);
}
