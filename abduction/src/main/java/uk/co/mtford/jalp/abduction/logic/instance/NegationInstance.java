/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.rules.N1RuleNode;
import uk.co.mtford.jalp.abduction.rules.N2RuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mtford
 */
public class NegationInstance implements ILiteralInstance {

    private static final Logger LOGGER = Logger.getLogger(NegationInstance.class);

    private IASystemInferableInstance subFormula;

    public NegationInstance(IASystemInferableInstance subFormula) {
        this.subFormula = subFormula;
    }

    public IASystemInferableInstance getSubFormula() {
        return subFormula;
    }

    public void setSubFormula(IASystemInferableInstance subFormula) {
        this.subFormula = subFormula;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferableInstance> goals) {
        return new N1RuleNode(abductiveFramework, this, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferableInstance> goals) {
        return new N2RuleNode(abductiveFramework, this, goals, nestedDenialList);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        subFormula = (ILiteralInstance) subFormula.performSubstitutions(substitutions);
        return this;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new NegationInstance((IASystemInferableInstance) subFormula.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new NegationInstance(subFormula);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return subFormula.getVariables();
    }

    @Override
    public String toString() {
        return "not(" +
                 subFormula +
                ')';
    }
}
