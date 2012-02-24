/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemState;

/**
 *
 * @author mtford
 */
public class EqualityInstance implements ASystemInferable  {

    private IUnifiableInstance left;
    private IUnifiableInstance right;

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

    /** Solves the equality, and adds it to the store.
     * 
     * @param s
     * @return 
     */
    public boolean applyInferenceRule(ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
