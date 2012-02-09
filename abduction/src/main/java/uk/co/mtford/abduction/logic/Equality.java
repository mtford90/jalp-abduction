/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

/**
 *
 * @author mtford
 */
public class Equality extends AbstractPredicate {

    public Equality(String name, IUnifiable param1, IUnifiable param2) {
        super(name, new IUnifiable[2]);
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
        return new Equality(clonedName,clonedParams[0],clonedParams[1]);
    }

    public IUnifiable[] getParameters() {
        return parameters;
    }
    
    public void setParameters(IUnifiable param1, IUnifiable param2) {
        parameters = new IUnifiable[2];
        parameters[0]=param1;
        parameters[1]=param2;
    }
    
}
