/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.FalseInstance;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.alp.abduction.logic.instance.ILogicInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mtford
 */
public class DenialInstance implements IASystemInferable {
    
    private static final Logger LOGGER = Logger.getLogger(DenialInstance.class);
    
    private List<IASystemInferable> body;
    private List<VariableInstance> universalVariables;

    public DenialInstance(List<IASystemInferable> body,
                          List<VariableInstance> universalVariables) {
        this.body = body;
        this.universalVariables = universalVariables;
    }



    public List<VariableInstance> getUniversalVariables() {
        return universalVariables;
    }

    public void setUniversalVariables(List<VariableInstance> universalVariables) {
        this.universalVariables = universalVariables;
    }

    public DenialInstance() {
        body = new LinkedList<IASystemInferable>();
        universalVariables=new LinkedList<VariableInstance>();
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
    public String toString() {
        String rep = "ic";
       /* if (!universalVariables.isEmpty()) {
            rep+="(";
            for (VariableInstance v:universalVariables) rep+=v+",";
            rep = rep.substring(0,rep.length()-1);
            rep+=")";
        } */
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
        else { // The denial fails. All attempts to make it true have failed.
            s.putGoal(new FalseInstance());
        }
        List<ASystemState> possibleStates = new LinkedList<ASystemState>();
        possibleStates.add(s);
        return possibleStates;
    }

    // TODO: Don't think actually neccessary?
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

    public ILogicInstance clone(Map<String, VariableInstance> variablesSoFar) {
        DenialInstance clone = new DenialInstance();
        for (VariableInstance v:universalVariables) {
            clone.getUniversalVariables().add((VariableInstance) v.clone(variablesSoFar));
        }
        for (IASystemInferable logic:body) {
            clone.body.add((IASystemInferable)logic.clone(variablesSoFar));
        }
        
        return clone;
    }

    public List<IASystemInferable> getBody() {
        return body;
    }
    
    public boolean isUniversallyQuantified(VariableInstance v) {
        for (VariableInstance var:universalVariables) {
            if ((v.getName()+"<"+v.getUniqueId()+">").equals(var.toString())) return true;
        }
        return false;
    }
    
    
    
}
