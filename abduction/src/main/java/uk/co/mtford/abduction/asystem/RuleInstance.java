/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.naming.OperationNotSupportedException;
import uk.co.mtford.abduction.logic.Predicate;
import uk.co.mtford.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.abduction.logic.instance.VariableInstance;

/**
 *
 * @author mtford
 */
public class RuleInstance {
    private PredicateInstance head;
    private List<ILiteralInstance> body;
    
    public RuleInstance(PredicateInstance head,List<ILiteralInstance> body) {
        this.body = body;
        this.head = head;
    }
    
    public void addLiteral(ILiteralInstance p) {
        body.add(p);
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
        return body.get(0);
    }
    
    public void pushLiteral(ILiteralInstance p) {
        body.add(0, p);
    }

    public PredicateInstance getHead() {
        return head;
    }

    public void setHead(PredicateInstance head) {
        this.head = head;
    }
    
    public List<ILiteralInstance> getBody() {
        return body;
    }
    
    public List<ILiteralInstance> unfold(IAtomInstance ... params) throws RuleUnfoldException {
        if (params.length!=head.getNumParams()) {
            throw new RuleUnfoldException("Wrong number of parameters when expanding rule: "+this);
        }
        RuleInstance clonedInstance = (RuleInstance) this.clone();
        for (int i=0;i<params.length;i++) {
            VariableInstance param = (VariableInstance) clonedInstance.head.getParameter(i);
            param.setValue(params[i]);
        }
        return clonedInstance.body;
    }

    @Override
    public String toString() {
        String ruleRep = "";
        ruleRep += head;
        if (body!=null) {
            String bodyRep = body.toString();
            bodyRep = bodyRep.substring(1, bodyRep.length()-1);
            ruleRep += " :- "+bodyRep;
        }
        ruleRep += ".";
        return ruleRep;
    }
    
    @Override
    protected Object clone() { 
        HashMap<String, VariableInstance> variables = new HashMap<String, VariableInstance>();
        for (IAtomInstance a:head.getParameters()) {
            VariableInstance v = (VariableInstance)a;
            variables.put(v.getName(), (VariableInstance)v.clone());
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
