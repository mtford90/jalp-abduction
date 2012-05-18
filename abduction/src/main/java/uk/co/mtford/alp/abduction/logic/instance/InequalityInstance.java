/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.List;

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

    @Override
    public String toString() {
        return e.getLeft()+"=/="+e.getRight();
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        return null;  // TODO
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        return null;  // TODO
    }
}
