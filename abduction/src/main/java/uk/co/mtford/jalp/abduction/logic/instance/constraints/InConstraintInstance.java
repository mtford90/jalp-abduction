package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class InConstraintInstance extends ConstraintInstance {
    public InConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (ITermInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InConstraintInstance((ITermInstance)left.deepClone(substitutions),(ITermInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new InConstraintInstance((ITermInstance)left,(ITermInstance)right);
    }


}
