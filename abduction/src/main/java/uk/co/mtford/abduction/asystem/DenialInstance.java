/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.LinkedList;
import java.util.List;
import uk.co.mtford.abduction.logic.instance.ILiteralInstance;

/**
 *
 * @author mtford
 */
public class DenialInstance implements ILiteralInstance  {
    
    private List<ILiteralInstance> body;

    public DenialInstance(List<ILiteralInstance> body) {
        this.body = body;
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
            clone.body.add((ILiteralInstance)logic.clone());
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
    
}
