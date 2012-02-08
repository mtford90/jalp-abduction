/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import uk.co.mtford.abduction.logic.*;
import uk.co.mtford.abduction.logic.Variable;

/**
 *
 * @author mtford
 */
public class Predicate extends AbstractPredicate {

    public Predicate(String name, IUnifiable ... parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IUnifiable[] getParameters() {
        return parameters;
    }

    public void setParameters(IUnifiable[] parameters) {
        this.parameters = parameters;
    }
    
     public int getNumParams() {
        return parameters.length;
    }

    @Override
    public String toString() {
        String paramList = "";
        for (IUnifiable v : parameters) {
            paramList += v + ",";
        }
        paramList = paramList.substring(0, paramList.length() - 1);
        return name + "(" + paramList + ")";
    }

    @Override
    public Object clone() {
        String clonedName = new String(name);
        IUnifiable[] clonedParams = new IUnifiable[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IUnifiable) parameters[i].clone();
        }
        return new Predicate(clonedName,clonedParams);
    }
    
    
}
