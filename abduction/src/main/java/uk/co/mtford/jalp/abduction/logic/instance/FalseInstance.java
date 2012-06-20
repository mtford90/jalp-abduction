/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.NegativeFalseRuleNode;
import uk.co.mtford.jalp.abduction.rules.PositiveFalseRuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents logical falsity or 'bottom'
 *
 * @author mtford
 */
public class FalseInstance implements IAtomInstance, IInferableInstance {

    @Override
    public String toString() {
        return "FALSE";
    }

    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new PositiveFalseRuleNode(abductiveFramework, query, goals);
    }

    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new NegativeFalseRuleNode(abductiveFramework, query, goals);
    }

    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return this;
    }

    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return new FalseInstance();
    }

    public IFirstOrderLogicInstance shallowClone() {
        return this;
    }

    public Set<VariableInstance> getVariables() {
        return new HashSet<VariableInstance>();
    }

    public boolean equals(Object other) {
        if (other instanceof FalseInstance) return true;
        return false;
    }
}
