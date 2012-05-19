/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.NegativeFalseRuleNode;
import uk.co.mtford.alp.abduction.rules.PositiveFalseRuleNode;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mtford
 */
public class FalseInstance implements IEqualitySolverResultInstance, IAtomInstance {

    @Override
    public String toString() {
        return "FALSE";
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferableInstance> goals) {
        return new PositiveFalseRuleNode(abductiveFramework, this, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferableInstance> goals) {
        return new NegativeFalseRuleNode(abductiveFramework, this, goals, nestedDenialList);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return this;
    }

    @Override
    public IFirstOrderLogicInstance clone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new FalseInstance();
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return new HashSet<VariableInstance>();
    }
}
