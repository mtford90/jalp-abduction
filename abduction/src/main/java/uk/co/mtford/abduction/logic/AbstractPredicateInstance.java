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
public abstract class AbstractPredicateInstance implements IPredicateInstance {
    protected String name;
    protected IUnifiableInstance[] parameters;
    
    public AbstractPredicateInstance(String name, IUnifiableInstance[] parameters) {
        this.name=name;
        this.parameters=parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractPredicateInstance other = (AbstractPredicateInstance) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.parameters, other.parameters)) {
            return false;
        }
        return true;
    }
    
    public boolean contains(IUnifiableInstance parameter) {
        for (int i=0;i<parameters.length;i++) {
            if (parameters[i].equals(parameter)) return true;
        }
        return false;
    }
    
    public boolean replaceParameter(int num, VariableInstance parameter) {
        if (num<0||num>parameters.length) {
            return false;
        }
        parameters[num] = parameter;
        return true;
    }
    
    public boolean replaceParameter(VariableInstance toBeReplaced, VariableInstance toReplace) {
        for (int i=0; i<parameters.length; i++) {
            if (parameters[i].equals(toBeReplaced)) {
                parameters[i] = toReplace;
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 43 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
    }
    
    public int getNumParams() {
        return parameters.length;
    }

    public void setParameters(VariableInstance[] params) {
        this.parameters=params;
    }
    
    public IUnifiableInstance getParameter(int i) {
        if (i>parameters.length||i<0) return null;
        return parameters[i];
    }
    
    @Override
    public abstract Object clone();
    
}
