/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.logic.ILiteralInstance;
import uk.co.mtford.abduction.logic.PredicateInstance;

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
    
    
}
