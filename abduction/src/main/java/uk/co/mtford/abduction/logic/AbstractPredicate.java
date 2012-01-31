/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.Arrays;

/**
 *
 * @author mtford
 */
public abstract class AbstractPredicate implements IPredicate {
    protected String name;
    protected IUnifiable[] parameters;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractPredicate other = (AbstractPredicate) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.parameters, other.parameters)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 43 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
    }
    
}
