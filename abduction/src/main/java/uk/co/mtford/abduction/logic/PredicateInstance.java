/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.AbductiveFramework;
import uk.co.mtford.abduction.asystem.State;
import uk.co.mtford.abduction.asystem.Store;
import uk.co.mtford.abduction.logic.*;
import uk.co.mtford.abduction.logic.VariableInstance;
import uk.co.mtford.abduction.tools.NameGenerator;
import uk.co.mtford.unification.CouldNotUnifyException;
import uk.co.mtford.unification.Unifier;

/**
 *
 * @author mtford
 */
public class PredicateInstance extends AbstractPredicateInstance implements ASystemInferable {

    public PredicateInstance(String name, IUnifiableInstance ... parameters) {
        super(name,parameters);
    }
    
    public PredicateInstance(String name, String varName, IUnifiableInstance varValue) {
        super(name,new IUnifiableInstance[1]);
        parameters[0] = new VariableInstance(varName,varValue);
    }
    
    public PredicateInstance(String name, String varName) {
        super(name,new IUnifiableInstance[1]);
        parameters[0] = new VariableInstance(varName);
    }
    
    public PredicateInstance(String name) {
        super(name,null);
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
        List<AbstractPredicateInstance> abducibles = abductiveFramework.getA();
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

    
    
    
}
