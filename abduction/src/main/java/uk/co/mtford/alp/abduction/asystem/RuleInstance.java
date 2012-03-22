/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

/**
 *
 * @author mtford
 */
public class RuleInstance {
    
    private static final Logger LOGGER = Logger.getLogger(RuleInstance.class);
    
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
    
    public int getNumLiterals() {
        return body.size();
    }
    
    public boolean hasBody() {
        if (body==null) return false;
        if (body.size()==0) return false;
        return true;
    }
    
    public List<ILiteralInstance> unfold(IAtomInstance ... params) throws RuleUnfoldException {       
        if (LOGGER.isDebugEnabled()) {
            String message = "Unfolding "+this+" using (";
            for (IAtomInstance a:params) {
                message+=a+",";
            }
            message=message.substring(0, message.length()-1);
            message+=")";
            LOGGER.debug(message);
        }
        if (params.length!=head.getNumParams()) {
            throw new RuleUnfoldException("Wrong number of parameters when expanding rule: "+this);
        }
        LinkedList<EqualityInstance> equalities = new LinkedList<EqualityInstance>();
        RuleInstance clonedInstance = (RuleInstance) this.clone();
        for (int i=0;i<params.length;i++) {
            IAtomInstance param = (IAtomInstance) clonedInstance.head.getParameter(i);
            EqualityInstance equality = new EqualityInstance(param,params[i]);
            equalities.add(equality);
        }
        clonedInstance.body.addAll(0, equalities);
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Result of unfold is "+clonedInstance.body);
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
    public Object clone() { 
        if (hasBody()) { 
            HashMap<String, VariableInstance> variables = new HashMap<String, VariableInstance>();
            VariableInstance[] newParameters = new VariableInstance[head.getNumParams()];
            for (int i=0;i<head.getNumParams();i++) {
                newParameters[i]=(VariableInstance) head.getParameter(i).clone(variables);
            }
            LinkedList<ILiteralInstance> newBody = new LinkedList<ILiteralInstance>();
            for (int i=0;i<body.size();i++) {
                newBody.addLast((ILiteralInstance)body.get(i).clone(variables));
            }
            return new RuleInstance(new PredicateInstance(head.getName(),newParameters),newBody);
        } 
        else { // Is a fact.
            PredicateInstance headClone = (PredicateInstance) head.clone();
            return new RuleInstance(headClone,new LinkedList<ILiteralInstance>());
        }
            
    }
    
}