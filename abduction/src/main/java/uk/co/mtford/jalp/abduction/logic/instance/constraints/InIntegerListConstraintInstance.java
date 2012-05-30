package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.list.IntegerListInstance;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class InIntegerListConstraintInstance extends InListConstraintInstance {

    public InIntegerListConstraintInstance(ITermInstance left, IntegerListInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InIntegerListConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (IntegerListInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InIntegerListConstraintInstance((ITermInstance)left.deepClone(substitutions), (IntegerListInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new InIntegerListConstraintInstance((ITermInstance)left,(IntegerListInstance)right);
    }
}
