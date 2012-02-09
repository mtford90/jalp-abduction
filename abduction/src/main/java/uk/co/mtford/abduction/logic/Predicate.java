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
import uk.co.mtford.abduction.tools.NameGenerator;

/**
 *
 * @author mtford
 */
public class Predicate extends AbstractPredicate {

    public Predicate(String name, IUnifiable ... parameters) {
        super(name,parameters);
    }
    
    public Predicate(String name, String varName, IUnifiable varValue) {
        super(name,new IUnifiable[1]);
        parameters[0] = new Variable(varName,varValue);
    }
    
    public Predicate(String name, String varName) {
        super(name,new IUnifiable[1]);
        parameters[0] = new Variable(varName);
    }
    
    public Predicate(String name) {
        super(name,null);
    }

    public IUnifiable[] getParameters() {
        return parameters;
    }

    public void setParameters(IUnifiable[] parameters) {
        this.parameters = parameters;
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
