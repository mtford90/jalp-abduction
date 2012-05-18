/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.tools.UniqueIdGenerator;

/**
 *
 * @author mtford
 */
public class VariableInstance implements ITermInstance {
    
    private static final Logger LOGGER = Logger.getLogger(VariableInstance.class);
    private int uniqueId = UniqueIdGenerator.getUniqueId();
    
    String name;

    public int getUniqueId() {
        return uniqueId;
    }

    public VariableInstance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name+"<"+uniqueId+">";
    }

    /** Returns true if variable names at the same. Not concerned with value. */
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /** Returns true if same name.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableInstance other = (VariableInstance) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
