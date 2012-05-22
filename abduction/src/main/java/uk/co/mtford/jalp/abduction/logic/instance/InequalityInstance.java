package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.equality.EqualityInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 21/05/2012
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class InequalityInstance implements IEqualityInstance {

    private EqualityInstance equalityInstance;

    public InequalityInstance(EqualityInstance equalityInstance) {
        this.equalityInstance = equalityInstance;
    }

    @Override
    public String toString() {
        return "not "+equalityInstance;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IASystemInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public Set<VariableInstance> getVariables() {
        throw new UnsupportedOperationException(); // TODO
    }
}
