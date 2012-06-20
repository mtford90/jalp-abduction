/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.*;

import java.util.*;

/**
 * @author mtford
 */
public class EqualityInstance implements IEqualityInstance {

    private static Logger LOGGER = Logger.getLogger(EqualityInstance.class);

    protected IUnifiableInstance left;
    protected IUnifiableInstance right;

    public EqualityInstance(IUnifiableInstance left, IUnifiableInstance right) {
        this.left = left;
        this.right = right;
    }

    public IUnifiableInstance getLeft() {
        return left;
    }

    public void setLeft(IUnifiableInstance left) {
        this.left = left;
    }

    public IUnifiableInstance getRight() {
        return right;
    }

    public void setRight(IUnifiableInstance right) {
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


    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new E1RuleNode(abductiveFramework, query, goals);
    }

    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {

        List<EqualityInstance> reductionResult = left.reduce(right);
        DenialInstance currentGoal = (DenialInstance) goals.remove(0).shallowClone();
        EqualityInstance equalityDenialHead;
        goals.add(0,currentGoal);

        if (!currentGoal.getBody().get(0).equals(this)) { // Sanity check.
            throw new JALPException("Head of the denial isnt the object creating the root node!");
        }

        do {
            equalityDenialHead = (EqualityInstance) currentGoal.getBody().remove(0);
            reductionResult = left.reduce(right);
            if (reductionResult.isEmpty()) currentGoal.getBody().add(0,equalityDenialHead);
            else {
                currentGoal.getBody().addAll(0,reductionResult);
            }
        }   while (!reductionResult.isEmpty());


        if (currentGoal.getUniversalVariables().contains(left)) { // For all X = u
            return new E2RuleNode(abductiveFramework,query,goals);
        }

        else if (!(left instanceof VariableInstance) && currentGoal.getUniversalVariables().contains(right)) {  // For all u = X where u is not an existentially quantified variable.
            return new E2RuleNode(abductiveFramework,query,goals);
        }

        else if ((left instanceof VariableInstance) && currentGoal.getUniversalVariables().contains(right)) {  // E2c: Z = Y Z is existentially quantified and Y is unversally quantified.
            return new E2cRuleNode(abductiveFramework,query,goals);
        }

        else if (left instanceof VariableInstance) {  // E2b: Z = u, where Z is existentially quantified, and u could be anything but a universally quantified variable.
            return new E2bRuleNode(abductiveFramework,query,goals);
        }

        else { // c==d
            return new E2RuleNode(abductiveFramework,query,goals);
        }

    }

    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        IUnifiableInstance newLeft = (IUnifiableInstance) left.performSubstitutions(substitutions);
        IUnifiableInstance newRight = (IUnifiableInstance) right.performSubstitutions(substitutions);
        left = newLeft;
        right = newRight;
        return this;
    }

    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        IUnifiableInstance newLeft = (IUnifiableInstance) left.deepClone(substitutions);
        IUnifiableInstance newRight = (IUnifiableInstance) right.deepClone(substitutions);
        return new EqualityInstance(newLeft, newRight);
    }

    public IFirstOrderLogicInstance shallowClone() {
        return new EqualityInstance((IUnifiableInstance)left.shallowClone(), (IUnifiableInstance) right.shallowClone());
    }

    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        variables.addAll(left.getVariables());
        variables.addAll(right.getVariables());
        return variables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EqualityInstance)) return false;

        EqualityInstance that = (EqualityInstance) o;

        if (!left.equals(that.left)) return false;
        if (!right.equals(that.right)) return false;

        return true;
    }

    public List<EqualityInstance> reduceLeftRight() {
        return left.reduce(right);
    }

    public boolean unifyLeftRight(Map<VariableInstance, IUnifiableInstance> assignments)  {
        return left.unify(right,assignments);
    }

    public List<EqualityInstance> reduceRightLeft() {
        return right.reduce(left);
    }

    public boolean unifyRightLeft(Map<VariableInstance, IUnifiableInstance> assignments)  {
        return right.unify(left,assignments);
    }

    public boolean equalitySolve(Map<VariableInstance, IUnifiableInstance> equalitySolverAssignments) {
        return left.unify(right,equalitySolverAssignments);
    }
}
