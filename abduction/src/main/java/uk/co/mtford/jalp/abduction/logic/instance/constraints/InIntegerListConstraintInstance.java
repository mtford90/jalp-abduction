package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetVariable;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantListInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static choco.Choco.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class InIntegerListConstraintInstance extends InListConstraintInstance {

    public InIntegerListConstraintInstance(ITermInstance left, IntegerConstantListInstance right) {
        super(left, right);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InIntegerListConstraintInstance((ITermInstance)left.performSubstitutions(substitutions), (IntegerConstantListInstance) right.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InIntegerListConstraintInstance((ITermInstance)left.deepClone(substitutions), (IntegerConstantListInstance)right.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new InIntegerListConstraintInstance((ITermInstance)left,(IntegerConstantListInstance)right);
    }

    @Override
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        left.reduceToChoco(possSubst,chocoVariables);
        IntegerVariable leftVar = (IntegerVariable) chocoVariables.get(left);
        right.reduceToChoco(possSubst, chocoVariables);
        SetVariable rightVar = (SetVariable) chocoVariables.get(right);
        if (leftVar.getDomainSize()==1) {
            Constraint c =  member(leftVar, rightVar);
            chocoConstraints.add(c);
            constraintMap.put(c, this);
        }
        else {
            int rightLowB = rightVar.getLowB();
            int rightUppB = rightVar.getUppB();
            int leftLowB = leftVar.getLowB();
            int leftUppB = leftVar.getUppB();
            if (rightLowB>leftLowB) {
                leftVar.setLowB(rightLowB);
            }
            if (rightUppB<leftUppB) {
                leftVar.setUppB(rightUppB);
            }
        }
        return true;
    }

    @Override
    public boolean reduceToNegativeChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        left.reduceToChoco(possSubst,chocoVariables);
        IntegerVariable leftVar = (IntegerVariable) chocoVariables.get(left);
        right.reduceToChoco(possSubst, chocoVariables);
        SetVariable rightVar = (SetVariable) chocoVariables.get(right);
        Constraint c = notMember(leftVar, rightVar);
        chocoConstraints.add(c);
        constraintMap.put(c,new NegativeConstraintInstance(this));
        return true;
    }




}
