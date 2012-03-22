/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.*;
import uk.co.mtford.alp.unification.Unifier;

/**
 *
 * @author mtford
 */
public class EqualityInstance implements IEqualityInstance  {
    
    private static Logger LOGGER = Logger.getLogger(EqualityInstance.class);

    private IAtomInstance left;
    private IAtomInstance right;

    public EqualityInstance(IAtomInstance left, IAtomInstance right) {
        this.left=left;
        this.right=right;
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
    public Object clone() {
        return new EqualityInstance((IAtomInstance)left.clone(),(IAtomInstance)right.clone());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EqualityInstance other = (EqualityInstance) obj;
        if (this.left != other.left && (this.left == null || !this.left.equals(other.left))) {
            return false;
        }
        if (this.right != other.right && (this.right == null || !this.right.equals(other.right))) {
            return false;
        }
        return true;
    }
    
     public boolean deepEquals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EqualityInstance other = (EqualityInstance) obj;
        if (this.left != other.left && (this.left == null || !this.left.deepEquals(other.left))) {
            return false;
        }
        if (this.right != other.right && (this.right == null || !this.right.deepEquals(other.right))) {
            return false;
        }
        return true;
    }
 
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.left != null ? this.left.hashCode() : 0);
        hash = 89 * hash + (this.right != null ? this.right.hashCode() : 0);
        return hash;
    }

    /** Implements inference rule E1.
     * 
     * @param framework
     * @param s
     * @return 
     */
    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException();
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException();
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        return new EqualityInstance((IAtomInstance)left.clone(variablesSoFar),(IAtomInstance)right.clone(variablesSoFar));
    }

    @Override
    public String toString() {
        String leftString = left.toString();
        String rightString = right.toString();
        
        return leftString + "==" + rightString;
    }

   

}
