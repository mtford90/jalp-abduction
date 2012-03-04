/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import uk.co.mtford.abduction.logic.instance.VariableInstance;

/**
 *
 * @author mtford
 */
public class Variable {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public VariableInstance getVariableInstance() {
        return new VariableInstance(name);
    }
}
