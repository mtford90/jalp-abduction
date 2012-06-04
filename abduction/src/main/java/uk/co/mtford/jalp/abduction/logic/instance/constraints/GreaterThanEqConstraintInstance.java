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

import static choco.Choco.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class GreaterThanEqConstraintInstance extends ConstraintInstance {
    public GreaterThanEqConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new GreaterThanEqConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (ITermInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new GreaterThanEqConstraintInstance((ITermInstance)left.deepClone(substitutions),(ITermInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new GreaterThanEqConstraintInstance((ITermInstance)left,(ITermInstance)right);
    }

    @Override
    public String toString () {
        return left + ">="+ right;
    }

    @Override
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        left.reduceToChoco(possSubst,chocoVariables);
        IntegerVariable leftVar = (IntegerVariable) chocoVariables.remove(0);
        right.reduceToChoco(possSubst, chocoVariables);
        IntegerVariable rightVar = (IntegerVariable) chocoVariables.remove(0);
        if (leftVar.getDomainSize()==1) {
            if (rightVar.getDomainSize()==1) {
                Constraint c = geq(leftVar,rightVar);
                chocoConstraints.add(c);
                constraintMap.put(c,this);
            }
            else {
                int rightUppB = rightVar.getUppB();
                int left = leftVar.getUppB();
                if (left<rightUppB) {
                    rightVar.setUppB(left);
                }
            }
        }
        else {
            if (rightVar.getDomainSize()==1) {
                int right = rightVar.getLowB();
                int leftLowB = leftVar.getUppB();
                if (right>leftLowB) {
                    leftVar.setLowB(right);
                }
            }
            else {
                Constraint c = geq(leftVar,rightVar);
                chocoConstraints.add(c);
                constraintMap.put(c,this);
            }
        }
        return true;
    }

    @Override
    public boolean reduceToNegativeChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        HashMap<Constraint,IConstraintInstance> newConstraintMap = new HashMap<Constraint,IConstraintInstance>();
        ConstraintInstance c = new LessThanConstraintInstance(left,right);
        boolean success = c.reduceToChoco(possSubst, chocoConstraints, chocoVariables, newConstraintMap);
        assert(newConstraintMap.size()==1);
        constraintMap.put((Constraint) newConstraintMap.keySet().toArray()[0], new NegativeConstraintInstance(c));
        return success;
    }
}
