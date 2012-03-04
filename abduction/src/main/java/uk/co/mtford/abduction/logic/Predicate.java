/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import uk.co.mtford.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.abduction.logic.instance.VariableInstance;

/**
 *
 * @author mtford
 */
public class Predicate {
    protected String name;
    protected Variable[] parameters;

    public Predicate(String name, Variable[] parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Variable[] getParameters() {
        return parameters;
    }

    public void setParameters(Variable[] parameters) {
        this.parameters = parameters;
    }
    
    public PredicateInstance getPredicateInstance() {
        VariableInstance[] instanceVars = new VariableInstance[parameters.length]; 
        for (int i=0;i<parameters.length;i++) {
            instanceVars[i]=parameters[i].getVariableInstance();
        }
        return new PredicateInstance(name,instanceVars);
    }
}
