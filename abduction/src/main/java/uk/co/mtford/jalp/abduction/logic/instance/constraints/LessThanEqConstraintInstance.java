package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class LessThanEqConstraintInstance extends ConstraintInstance {
    public LessThanEqConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new LessThanEqConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (ITermInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new LessThanEqConstraintInstance((ITermInstance)left.deepClone(substitutions),(ITermInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new LessThanEqConstraintInstance((ITermInstance)left,(ITermInstance)right);
    }

    @Override
    public String toString () {
        return left + "<="+ right;
    }

    @Override
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints) {
        throw new UnsupportedOperationException(); // TODO
    }
}
