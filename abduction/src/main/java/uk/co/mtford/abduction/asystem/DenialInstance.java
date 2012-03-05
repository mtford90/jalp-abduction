/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.abduction.logic.instance.VariableInstance;

/**
 *
 * @author mtford
 */
public class DenialInstance implements IASystemInferable  {
    
    private List<ILiteralInstance> body;
    private Map<String, VariableInstance> variables;

    public DenialInstance(List<ILiteralInstance> body, Map<String, VariableInstance> variables) {
        this.body = body;
        this.variables = variables;
    }
    
    public DenialInstance(ILiteralInstance logic) {
        body = new LinkedList<ILiteralInstance>();
        body.add(logic);
    }
    
    public DenialInstance() {
        body = new LinkedList<ILiteralInstance>();
    }
    
    public void addbody(ILiteralInstance p) {
        body.add(p);
    }
    
    public void removebody(ILiteralInstance p) {
        body.remove(p);
    }
    
    public boolean containsbody(ILiteralInstance p) {
        return body.contains(p);
    }
    
    public ILiteralInstance getbody(int i) {
        return body.get(i);
    }
    
    public ILiteralInstance removebody(int i) {
        return body.remove(i);
    }
    
    public ILiteralInstance pop() {
        return body.get(0);
    }
    
    public void push(ILiteralInstance p) {
        body.add(0, p);
    }
    
    @Override
    public Object clone() {
        DenialInstance clone = new DenialInstance();
        for (IASystemInferable logic:body) {
            clone.body.add((ILiteralInstance)logic.clone(variables));
        }
        return clone;
    }

    @Override
    public String toString() {
        String rep = "ic :- ";
        String bodyRep = body.toString();
        bodyRep = bodyRep.substring(1, bodyRep.length()-1);
        rep += bodyRep+".";
        return rep;
    }

    public boolean deepEquals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // TODO: This may not be the right thing to do....
    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        variablesSoFar.putAll(variables);
        DenialInstance clone = new DenialInstance();
        for (IASystemInferable logic:body) {
            clone.body.add((ILiteralInstance)logic.clone(variables));
        }
        return clone;
    }


}
