package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ConstantListInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class InConstantListConstraintInstance  extends InListConstraintInstance {

    public InConstantListConstraintInstance(ITermInstance left, ConstantListInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InConstantListConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (ConstantListInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InConstantListConstraintInstance((ITermInstance)left.deepClone(substitutions), (ConstantListInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new InConstantListConstraintInstance((ITermInstance)left,(ConstantListInstance)right);
    }

    @Override
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables) {
        return left.inList((ConstantListInstance) right,possSubst);
    }

    @Override
    public boolean reduceToNegativeChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables) {
        List<Map<VariableInstance,IUnifiableAtomInstance>> newPossSubst = new LinkedList<Map<VariableInstance, IUnifiableAtomInstance>>(possSubst);
        boolean reduceSuccess = left.inList((ConstantListInstance) right,newPossSubst);
        boolean trueAndMadeSubst = reduceSuccess && newPossSubst.size()>possSubst.size();
        boolean notInList = trueAndMadeSubst || !reduceSuccess;
        return notInList;
    }

}
