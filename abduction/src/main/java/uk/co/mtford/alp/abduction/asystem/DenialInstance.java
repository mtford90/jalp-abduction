/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

/**
 *
 * @author mtford
 */
public class DenialInstance implements IASystemInferable {
    
    private static final Logger LOGGER = Logger.getLogger(DenialInstance.class);
    
    private List<IASystemInferable> body;
    private Map<String, VariableInstance> universalVariables;

    public DenialInstance(List<IASystemInferable> body,
                          Map<String, VariableInstance> universalVariables) {
        this.body = body;
        this.universalVariables = universalVariables;
    }

    public Map<String, VariableInstance> getUniversalVariables() {
        return universalVariables;
    }

    public void setUniversalVariables(Map<String, VariableInstance> universalVariables) {
        this.universalVariables = universalVariables;
    }

    public DenialInstance(List<IASystemInferable> body) {
        this.body=body;
        this.universalVariables=new HashMap<String,VariableInstance>();
    }

    
    public DenialInstance() {
        body = new LinkedList<IASystemInferable>();
        universalVariables = new HashMap<String, VariableInstance>();
    }
    
    public void addLiteral(IASystemInferable p) {
        body.add(p);
    }
    
    public void addLiteral(int i, IASystemInferable p) {
        body.add(i,p);
    }
    
    public void addLiteral(int i, List<IASystemInferable> p) {
        body.addAll(i,p);
    }
    
    public void removeLiteral(IASystemInferable p) {
        body.remove(p);
    }
    
    public boolean containsLiteral(IASystemInferable p) {
        return body.contains(p);
    }
    
    public IASystemInferable getLiteral(int i) {
        return body.get(i);
    }
    
    public IASystemInferable removeLiteral(int i) {
        return body.remove(i);
    }
    
    public IASystemInferable popLiteral() {
        IASystemInferable i = body.get(0);
        body.remove(0);
        return i;
    }
    
    public IASystemInferable peekLiteral() {
        IASystemInferable i = body.get(0);
        return i;
    }
    
    
    public void pushLiteral(ILiteralInstance p) {
        body.add(0, p);
    }
    
    @Override
    public Object clone() {
        DenialInstance clone = new DenialInstance();
        for (IASystemInferable l:body) {
            clone.addLiteral(l);
        }
        for (String s:universalVariables.keySet()) {
            clone.universalVariables.put(s, (VariableInstance) universalVariables.get(s).clone());
        }
        return clone;
    }

    @Override
    public String toString() {
        String rep = "ic";
        if (!universalVariables.isEmpty()) {
            rep+="(";
            for (String s:universalVariables.keySet()) rep+=s+",";
            rep = rep.substring(0,rep.length()-1);
            rep+=")";
        }
        rep+=" :- ";
        String bodyRep = body.toString();
        bodyRep = bodyRep.substring(1, bodyRep.length()-1);
        rep += bodyRep+".";
        return rep;
    }

    public boolean deepEquals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inference rules to "+this);
        DenialInstance thisClone = (DenialInstance) s.getGoals().get(0); 
        if (!thisClone.getBody().isEmpty()) {
            IASystemInferable first = body.get(0);
            return first.applyDenialInferenceRule(framework, s);
        }
        else {
            s.popGoal(); 
        }
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        possibleStates.add(s);
        return possibleStates;
    }

    // TODO: Support nested denials?
    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<VariableInstance> getVariables() {
        LinkedList<VariableInstance> vars = new LinkedList<VariableInstance>();
        for (IASystemInferable inf:body) {
            vars.addAll(inf.getVariables());
        }
        return vars;
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        variablesSoFar.putAll(universalVariables);
        DenialInstance clone = new DenialInstance();
        for (IASystemInferable logic:body) {
            clone.body.add((IASystemInferable)logic.clone(variablesSoFar));
        }
        for (String s:universalVariables.keySet()) {
            clone.universalVariables.put(s, (VariableInstance) universalVariables.get(s).clone(variablesSoFar));
        }
        return clone;
    }

    public List<IASystemInferable> getBody() {
        return body;
    }
    
    public boolean isUniversallyQuantified(VariableInstance v) {
        return universalVariables.containsKey(v.getName()+"<"+v.getUniqueId()+">");
    }
    
    
    
}
