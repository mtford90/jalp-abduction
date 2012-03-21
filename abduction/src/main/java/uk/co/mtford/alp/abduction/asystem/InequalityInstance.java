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
import uk.co.mtford.alp.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

/** A wrapper for equality instance.
 *
 * @author mtford
 */
public class InequalityInstance implements IEqualityInstance {
    
    private static final Logger LOGGER = Logger.getLogger(InequalityInstance.class);
    
    private EqualityInstance e;
    
    public InequalityInstance (IAtomInstance left, IAtomInstance right) {
        e = new EqualityInstance(left,right);
    }

    public InequalityInstance(EqualityInstance e) {
        this.e = e;
    }

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        // Must fail.
        List<ASystemState> possibleStates = e.applyInferenceRule(framework, (ASystemState)s.clone());
        if (possibleStates.isEmpty()) { // has failed.
            InequalityInstance thisClone = (InequalityInstance) s.popGoal();
            s.getStore().put(thisClone);
            possibleStates.add(s);
            return possibleStates; 
        }
        else {
            return new LinkedList<ASystemState>(); // Empty list.
        }
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InequalityInstance other = (InequalityInstance) obj;
        if (this.e != other.e && (this.e == null || !this.e.equals(other.e))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.e != null ? this.e.hashCode() : 0);
        return hash;
    }

    public boolean deepEquals(Object obj) {
        return e.deepEquals(obj);
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        return new InequalityInstance((EqualityInstance)e.clone(variablesSoFar));
    }
    
    @Override
    public Object clone() {
        return new InequalityInstance((EqualityInstance)e.clone());
    }

    @Override
    public String toString() {
        return e.getLeft()+"=/="+e.getRight();
    }
    
}
