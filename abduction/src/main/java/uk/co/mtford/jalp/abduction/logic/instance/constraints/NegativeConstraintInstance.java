package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class NegativeConstraintInstance implements IConstraintInstance {
     private ConstraintInstance constraintInstance;

    public NegativeConstraintInstance(ConstraintInstance constraintInstance) {
        this.constraintInstance = constraintInstance;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO Again ASystem paper may suggest rules to deal with negatives constraints?
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO Again ASystem paper may suggest rules to deal with negatives constraints?
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new NegativeConstraintInstance((ConstraintInstance)constraintInstance.performSubstitutions(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new NegativeConstraintInstance((ConstraintInstance)constraintInstance.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new NegativeConstraintInstance(constraintInstance);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return constraintInstance.getVariables();
    }
}
