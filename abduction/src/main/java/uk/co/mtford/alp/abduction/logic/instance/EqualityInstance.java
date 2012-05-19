/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.E1RuleNode;
import uk.co.mtford.alp.abduction.rules.E2RuleNode;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mtford
 */
public class EqualityInstance implements IEqualitySolverResultInstance, ILiteralInstance {

    private static Logger LOGGER = Logger.getLogger(EqualityInstance.class);

    private IAtomInstance left;
    private IAtomInstance right;

    public EqualityInstance(IAtomInstance left, IAtomInstance right) {
        this.left = left;
        this.right = right;
    }

    public IAtomInstance getLeft() {
        return left;
    }

    public void setLeft(IAtomInstance left) {
        this.left = left;
    }

    public IAtomInstance getRight() {
        return right;
    }

    public void setRight(IAtomInstance right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.left != null ? this.left.hashCode() : 0);
        hash = 89 * hash + (this.right != null ? this.right.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String leftString = left.toString();
        String rightString = right.toString();

        return leftString + "==" + rightString;
    }


    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferableInstance> goals) {
        return new E1RuleNode(abductiveFramework, this, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferableInstance> goals) {
        return new E2RuleNode(abductiveFramework, this, goals, nestedDenialList);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        IAtomInstance newLeft = (IAtomInstance) left.performSubstitutions(substitutions);
        IAtomInstance newRight = (IAtomInstance) right.performSubstitutions(substitutions);
        left = newLeft;
        right = newRight;
        return this;
    }

    @Override
    public IFirstOrderLogicInstance clone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        IAtomInstance newLeft = (IAtomInstance) left.clone(substitutions);
        IAtomInstance newRight = (IAtomInstance) right.clone(substitutions);
        return new EqualityInstance(newLeft, newRight);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        variables.addAll(left.getVariables());
        variables.addAll(right.getVariables());
        return variables;
    }
}
