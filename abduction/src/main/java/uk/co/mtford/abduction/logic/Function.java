/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author mtford
 */
public class Function implements Term {
    String name;
    Term[] parameters;

    public Function(String name, Term ... parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Term[] getParameters() {
        return parameters;
    }

    public void setParameters(Term[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String paramList = "";
        for (Term v:parameters) {
            paramList += v+",";
        }
        paramList = paramList.substring(0, paramList.length()-1);
        return name + "(" + paramList + ")";
    }

    public Map<Variable, IUnifiable> unify(IUnifiable unifiable) {
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
        final Function other = (Function) obj;
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
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 71 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
    }
    
    
    
}