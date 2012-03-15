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
    
    public void addLiteral(ILiteralInstance p) {
        body.add(p);
    }
    
    public void addLiteral(int i, ILiteralInstance p) {
        body.add(i,p);
    }
    
    public void addLiteral(int i, List<ILiteralInstance> p) {
        body.addAll(i,p);
    }
    
    public void removeLiteral(ILiteralInstance p) {
        body.remove(p);
    }
    
    public boolean containsLiteral(ILiteralInstance p) {
        return body.contains(p);
    }
    
    public ILiteralInstance getLiteral(int i) {
        return body.get(i);
    }
    
    public ILiteralInstance removeLiteral(int i) {
        return body.remove(i);
    }
    
    public ILiteralInstance popLiteral() {
        ILiteralInstance i = body.get(0);
        body.remove(0);
        return i;
    }
    
    public ILiteralInstance peekLiteral() {
        ILiteralInstance i = body.get(0);
        return i;
    }
    
    
    public void pushLiteral(ILiteralInstance p) {
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
        DenialInstance thisClone = (DenialInstance) s.getGoals().get(0); 
        if (!thisClone.getBody().isEmpty()) {
            ILiteralInstance first = body.get(0);
            return first.applyDenialInferenceRule(framework, s);
        }
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        possibleStates.add(s);
        return possibleStates;
    }

    // TODO: Support nested denials?
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

    public List<ILiteralInstance> getBody() {
        return body;
    }
    
    


}
