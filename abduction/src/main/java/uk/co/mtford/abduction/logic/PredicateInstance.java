/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.Arrays;
import java.util.List;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.AbductiveFramework;
import uk.co.mtford.abduction.asystem.State;
import uk.co.mtford.unification.CouldNotUnifyException;
import uk.co.mtford.unification.Unifier;

/**
 *
 * @author mtford
 */
public class PredicateInstance implements IPredicateInstance, ASystemInferable {
    protected String name;
    protected IUnifiableInstance[] parameters;
    
    public PredicateInstance(String name, IUnifiableInstance ... parameters) {
        this.name=name;
        this.parameters=parameters;
    }
    
    public PredicateInstance(String name, String varName, IUnifiableInstance varValue) {
        this.name=name;
        this.parameters=new IUnifiableInstance[1];
        parameters[0] = new VariableInstance(varName,varValue);
    }
    
    public PredicateInstance(String name, String varName) {
        this.name=name;
        this.parameters=new IUnifiableInstance[1];
        parameters[0] = new VariableInstance(varName);
    }
    
    public PredicateInstance(String name) {
        this.name = name;
        this.parameters=null;
    }

    public IUnifiableInstance[] getParameters() {
        return parameters;
    }

    public void setParameters(IUnifiableInstance[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String paramList = "";
        for (IUnifiableInstance v : parameters) {
            paramList += v + ",";
        }
        paramList = paramList.substring(0, paramList.length() - 1);
        return name + "(" + paramList + ")";
    }

    @Override
    public Object clone() {
        String clonedName = new String(name);
        IUnifiableInstance[] clonedParams = new IUnifiableInstance[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IUnifiableInstance) parameters[i].clone();
        }
        return new PredicateInstance(clonedName,clonedParams);
    }

    public boolean applyInferenceRule(List<LogicalFormulaeInstance> goals, State s) {
        AbductiveFramework abductiveFramework = s.getAbductiveFramework();
        List<PredicateInstance> abducibles = abductiveFramework.getA();
        return false;
    }

    public LogicalFormulaeInstance equalitySolve(IUnifiableInstance other) {
        try {
            Unifier.unify(this, other);
        } catch (CouldNotUnifyException ex) {
            return new LogicalFalseInstance();
        }
        return new LogicalTrueInstance();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Returns true if same name and same num parameters.
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
        final PredicateInstance other = (PredicateInstance) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.parameters.length!=other.parameters.length) {
            return false;
        }
        return true;
    }
    
    /** Returns true if same name and parameters are the same.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean deepEquals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PredicateInstance other = (PredicateInstance) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.parameters.length!=other.parameters.length) {
            return false;
        }
        for (int i=0;i<parameters.length;i++) {
            if (!parameters[i].deepEquals(other.parameters[i])) {
                return false;
            }
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
    
    
}
