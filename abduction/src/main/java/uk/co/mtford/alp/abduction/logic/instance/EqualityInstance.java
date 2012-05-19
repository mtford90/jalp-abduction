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

import java.util.List;

/**
 * @author mtford
 */
public class EqualityInstance implements IEqualitySolverResult, ILiteralInstance {

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
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        return new E1RuleNode(abductiveFramework, this, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferable> goals) {
        return new E2RuleNode(abductiveFramework, this, goals, nestedDenialList);
    }
}
