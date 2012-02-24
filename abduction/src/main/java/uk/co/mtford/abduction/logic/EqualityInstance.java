/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemState;
import uk.co.mtford.abduction.asystem.ASystemStore;

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

    public List<ASystemState> applyInferenceRule(List<LogicalFormulaeInstance> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyDenialInferenceRule(List<LogicalFormulaeInstance> currentGoals, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Object clone() {
        return new EqualityInstance((IUnifiableInstance)left.clone(),(IUnifiableInstance)right.clone());
    }

}
