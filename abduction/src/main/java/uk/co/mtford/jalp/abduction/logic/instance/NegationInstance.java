/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.N1RuleNode;
import uk.co.mtford.jalp.abduction.rules.N2RuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mtford
 */
public class NegationInstance implements IInferableInstance, IFirstOrderLogicInstance {

    private static final Logger LOGGER = Logger.getLogger(NegationInstance.class);

    private IInferableInstance subFormula;

    public NegationInstance(IInferableInstance subFormula) {
        this.subFormula = subFormula;
    }

    public IInferableInstance getSubFormula() {
        return subFormula;
    }

    public void setSubFormula(IInferableInstance subFormula) {
        this.subFormula = subFormula;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new N1RuleNode(abductiveFramework, query, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new N2RuleNode(abductiveFramework, query, goals);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        subFormula = (IInferableInstance) subFormula.performSubstitutions(substitutions);
        return this;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return new NegationInstance((IInferableInstance) subFormula.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new NegationInstance((IInferableInstance) subFormula.shallowClone());
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
