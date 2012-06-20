package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NegativeConstraintInstance implements IConstraintInstance {
     private ConstraintInstance constraintInstance;

    public NegativeConstraintInstance(ConstraintInstance constraintInstance) {
        this.constraintInstance = constraintInstance;
    }

    
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO Again ASystem paper may suggest rules to deal with negatives constraints?
    }

    
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO Again ASystem paper may suggest rules to deal with negatives constraints?
    }

    
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        constraintInstance = (ConstraintInstance)constraintInstance.performSubstitutions(substitutions);
        return this;
    }

    
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return new NegativeConstraintInstance((ConstraintInstance)constraintInstance.deepClone(substitutions));
    }

    
    public IFirstOrderLogicInstance shallowClone() {
        return new NegativeConstraintInstance((ConstraintInstance) constraintInstance.shallowClone());
    }

    
    public Set<VariableInstance> getVariables() {
        return constraintInstance.getVariables();
    }

    
    public String toString () {
        return "not " + constraintInstance;
    }

    
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        return constraintInstance.reduceToNegativeChoco(possSubst, chocoConstraints,chocoVariables,constraintMap);
    }

    
    public boolean reduceToNegativeChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, List<Constraint> chocoConstraints, HashMap<ITermInstance, Variable> chocoVariables, HashMap<Constraint,IConstraintInstance> constraintMap) {
        return constraintInstance.reduceToChoco(possSubst, chocoConstraints,chocoVariables,constraintMap);
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NegativeConstraintInstance)) return false;

        NegativeConstraintInstance that = (NegativeConstraintInstance) o;

        if (!constraintInstance.equals(that.constraintInstance)) return false;

        return true;
    }

    
    public int hashCode() {
        return constraintInstance.hashCode();
    }
}
