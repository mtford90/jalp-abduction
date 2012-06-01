package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static choco.Choco.geq;
import static choco.Choco.gt;
import static choco.Choco.lt;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class LessThanConstraintInstance extends ConstraintInstance {
    public LessThanConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new LessThanConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (ITermInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new LessThanConstraintInstance((ITermInstance)left.deepClone(substitutions),(ITermInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new LessThanConstraintInstance((ITermInstance)left,(ITermInstance)right);
    }

    @Override
    public String toString () {
        return left + "<"+ right;
    }

    @Override
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables) {
        left.reduceToChoco(possSubst,chocoVariables);
        IntegerVariable leftVar = (IntegerVariable) chocoVariables.get(left);
        right.reduceToChoco(possSubst, chocoVariables);
        IntegerVariable rightVar = (IntegerVariable) chocoVariables.get(right);
        if (leftVar.getDomainSize()==1) {
            if (rightVar.getDomainSize()==1) {
                chocoConstraints.add(lt(leftVar,rightVar));
            }
            else {
                int rightLowB = rightVar.getLowB();
                int left = leftVar.getLowB();
                if (left+1>rightLowB) {
                    rightVar.setLowB(left + 1);
                }
            }
        }
        else {
            if (rightVar.getDomainSize()==1) {
                int right = rightVar.getLowB();
                int leftUppb = leftVar.getUppB();
                if (right-1<leftUppb) {
                    leftVar.setUppB(right - 1);
                }
            }
            else {
                chocoConstraints.add(lt(leftVar,rightVar));
            }
        }
        return true;
    }

    @Override
    public boolean reduceToNegativeChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables) {
        return new GreaterThanEqConstraintInstance(left,right).reduceToChoco(possSubst, chocoConstraints, chocoVariables);
    }
}
