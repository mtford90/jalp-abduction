/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import uk.co.mtford.abduction.asystem.ASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemStateRewriter;
import uk.co.mtford.abduction.asystem.State;

/**
 *
 * @author mtford
 */
public class Equality extends AbstractPredicate implements ASystemInferable  {

    public Equality(IUnifiable param1, IUnifiable param2) {
        super("Equals", new IUnifiable[2]);
        parameters[0]=param1;
        parameters[1]=param2;
    }

    @Override
    public Object clone() {
        String clonedName = new String(name);
        IUnifiable[] clonedParams = new IUnifiable[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IUnifiable) parameters[i].clone();
        }
        return new Equality(clonedParams[0],clonedParams[1]);
    }

    public IUnifiable[] getParameters() {
        return parameters;
    }
    
    public void setParameters(IUnifiable param1, IUnifiable param2) {
        parameters = new IUnifiable[2];
        parameters[0]=param1;
        parameters[1]=param2;
    }

    // TODO
    public State[] applyInferenceRule(State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
