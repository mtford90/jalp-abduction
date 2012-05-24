package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.rules.F1RuleNode;
import uk.co.mtford.jalp.abduction.rules.F2RuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.HashSet;
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
public abstract class ConstraintInstance implements IConstraintInstance {
    protected ITermInstance left;
    protected ITermInstance right;

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
        return new F1RuleNode(abductiveFramework,this,goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals) {
        return new F2RuleNode(abductiveFramework,this,goals,nestedDenials);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        variables.addAll(left.getVariables());
        variables.addAll(right.getVariables());
        return variables;
    }
}
