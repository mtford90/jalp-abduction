package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.F1RuleNode;
import uk.co.mtford.jalp.abduction.rules.F2RuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new F1RuleNode(abductiveFramework,query,goals);
    }

    
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new F2RuleNode(abductiveFramework,query,goals);
    }

    
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        variables.addAll(left.getVariables());
        variables.addAll(right.getVariables());
        return variables;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstraintInstance)) return false;

        ConstraintInstance that = (ConstraintInstance) o;

        if (!left.equals(that.left)) return false;
        if (!right.equals(that.right)) return false;

        return true;
    }

    
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
