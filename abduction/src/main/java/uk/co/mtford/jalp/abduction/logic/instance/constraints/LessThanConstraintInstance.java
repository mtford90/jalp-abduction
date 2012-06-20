package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static choco.Choco.geq;
import static choco.Choco.gt;
import static choco.Choco.lt;

/**
 * e.g. X < 1
 */
public class LessThanConstraintInstance extends ConstraintInstance {
    public LessThanConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        left = (ITermInstance) left.performSubstitutions(substitutions);
        right = (ITermInstance) right.performSubstitutions(substitutions);
        return this;
    }

    
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return new LessThanConstraintInstance((ITermInstance)left.deepClone(substitutions),(ITermInstance)right.deepClone(substitutions));
    }

    
    public IFirstOrderLogicInstance shallowClone() {
        return new LessThanConstraintInstance((ITermInstance)left.shallowClone(),(ITermInstance)right.shallowClone());
    }

    
    public String toString () {
        return left + "<"+ right;
    }
    
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        left.reduceToChoco(possSubst,chocoVariables);
        IntegerVariable leftVar = (IntegerVariable) chocoVariables.get(left);
        right.reduceToChoco(possSubst, chocoVariables);
        IntegerVariable rightVar = (IntegerVariable) chocoVariables.get(right);
        chocoConstraints.add(lt(leftVar,rightVar));
        return true;
    }

    
    public boolean reduceToNegativeChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) { // TODO messy
        ConstraintInstance c = new GreaterThanEqConstraintInstance(left,right);
        boolean success = c.reduceToChoco(possSubst, chocoConstraints, chocoVariables, constraintMap);
        return success;
    }
}
