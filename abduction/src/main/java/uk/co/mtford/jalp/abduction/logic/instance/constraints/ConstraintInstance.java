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
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class ConstraintInstance implements IConstraintInstance {
    private ITermInstance left;
    private ITermInstance right;

    public ConstraintInstance(ITermInstance left, ITermInstance right) {
        this.left = left;
        this.right = right;
    }

    public ITermInstance getLeft() {
        return left;
    }

    public void setLeft(ITermInstance left) {
        this.left = left;
    }

    public ITermInstance getRight() {
        return right;
    }

    public void setRight(ITermInstance right) {
        this.right = right;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals) {
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
