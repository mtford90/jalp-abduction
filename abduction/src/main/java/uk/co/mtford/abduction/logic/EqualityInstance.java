/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemStateRewriter;
import uk.co.mtford.abduction.asystem.State;
import uk.co.mtford.abduction.asystem.Store;

/**
 *
 * @author mtford
 */
public class EqualityInstance extends AbstractPredicateInstance implements ASystemInferable  {

    public EqualityInstance(IUnifiableInstance param1, IUnifiableInstance param2) {
        super("Equals", new IUnifiableInstance[2]);
        parameters[0]=param1;
        parameters[1]=param2;
    }

    @Override
    public Object clone() {
        String clonedName = new String(name);
        IUnifiableInstance[] clonedParams = new IUnifiableInstance[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IUnifiableInstance) parameters[i].clone();
        }
        return new EqualityInstance(clonedParams[0],clonedParams[1]);
    }

    public IUnifiableInstance[] getParameters() {
        return parameters;
    }
    
    public void setParameters(IUnifiableInstance param1, IUnifiableInstance param2) {
        parameters = new IUnifiableInstance[2];
        parameters[0]=param1;
        parameters[1]=param2;
    }

    // TODO: Equality solver implementation.
    public boolean solveEquality() {
        return false;
    }

    public boolean applyInferenceRule(List<LogicalFormulae> goals, State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
