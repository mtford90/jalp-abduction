/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.N1RuleNode;
import uk.co.mtford.alp.abduction.rules.N2RuleNode;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mtford
 */
public class NegationInstance implements ILiteralInstance {

    private static final Logger LOGGER = Logger.getLogger(NegationInstance.class);

    private ILiteralInstance subFormula;

    public NegationInstance(ILiteralInstance subFormula) {
        this.subFormula = subFormula;
    }

    public ILiteralInstance getSubFormula() {
        return subFormula;
    }

    public void setSubFormula(ILiteralInstance subFormula) {
        this.subFormula = subFormula;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        return new N1RuleNode(abductiveFramework, this, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferable> goals) {
        return new N2RuleNode(abductiveFramework, this, goals, nestedDenialList);
    }

    @Override
    public IFirstOrderLogic performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new NegationInstance((ILiteralInstance) subFormula.performSubstitutions(substitutions));
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return subFormula.getVariables();
    }
}
