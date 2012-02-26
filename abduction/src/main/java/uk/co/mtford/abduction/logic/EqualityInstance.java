/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.IASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemState;
import uk.co.mtford.abduction.asystem.ASystemStore;

/**
 *
 * @author mtford
 */
public class EqualityInstance implements IASystemInferable  {

    private IASystemInferable left;
    private IASystemInferable right;

    public EqualityInstance(IASystemInferable left, IASystemInferable right) {
        this.left = left;
        this.right = right;
    }

    public IASystemInferable getLeft() {
        return left;
    }

    public void setLeft(IASystemInferable left) {
        this.left = left;
    }

    public IASystemInferable getRight() {
        return right;
    }

    public void setRight(IASystemInferable right) {
        this.right = right;
    }

    public List<ASystemState> applyInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyDenialInferenceRule(List<IASystemInferable> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Object clone() {
        return new EqualityInstance((IASystemInferable)left.clone(),(IASystemInferable)right.clone());
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.left != null ? this.left.hashCode() : 0);
        hash = 89 * hash + (this.right != null ? this.right.hashCode() : 0);
        return hash;
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

}
