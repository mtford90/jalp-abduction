/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.Map;

/**
 *
 * @author mtford
 */
public class ConstantInstance implements TermInstance {
    private String value;

    public ConstantInstance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof VariableInstance) { // Special case whereby variables have been assigned a constant value.
            VariableInstance var = (VariableInstance)obj;
            IUnifiableInstance varValue = var.getValue();
            if (varValue instanceof ConstantInstance) {
                return this.equals(varValue);
            }
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConstantInstance other = (ConstantInstance) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    @Override
    public Object clone(){
        return new ConstantInstance(value);
    }
    
}
