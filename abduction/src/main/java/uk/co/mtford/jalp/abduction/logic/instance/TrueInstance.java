/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.NegativeTrueRuleNode;
import uk.co.mtford.jalp.abduction.rules.PositiveTrueRuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents logical truth i.e. 'top'
 *
 * @author mtford
 */
public class TrueInstance implements IInferableInstance, IFirstOrderLogicInstance {

    public String toString() {
        return "TRUE";
    }

    
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new PositiveTrueRuleNode(abductiveFramework, query, goals);
    }

    
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new NegativeTrueRuleNode(abductiveFramework, query, goals);
    }

    
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return this;
    }

    
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return new TrueInstance();
    }

    
    public IFirstOrderLogicInstance shallowClone() {
        return this;
    }

    
    public Set<VariableInstance> getVariables() {
        return new HashSet<VariableInstance>();
    }

    
    public boolean equals(Object other) {
        if (other instanceof TrueInstance) return true;
        return false;
    }
}
