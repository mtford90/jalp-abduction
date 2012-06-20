package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a constraint e.g. X<Y
 */
public interface IConstraintInstance extends IInferableInstance {
    /**
     * Performs conversion to choco representation of this constraint, or solves the constraint natively.
     *
     * @param possSubst Substitutions.
     * @param chocoConstraints Currently collected choco constraint representations.
     * @param chocoVariables Current collection choco variable representations.
     * @param constraintMap Maps internal representations onto choco representations.
     * @return
     */
    boolean reduceToChoco(List<Map<VariableInstance,IUnifiableInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap);
    /**
     * Performs conversion to negative choco representation of this constraint, or solves the constraint natively.
     *
     * e.g. X<Y would be X>=Y
     *
     * @param possSubst Substitutions.
     * @param chocoConstraints Currently collected choco constraint representations.
     * @param chocoVariables Current collection choco variable representations.
     * @param constraintMap Maps internal representations onto choco representations.
     * @return
     */
    boolean reduceToNegativeChoco(List<Map<VariableInstance,IUnifiableInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap);
}
