/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction;

import java.util.LinkedList;
import java.util.List;
import uk.co.mtford.abduction.asystem.DenialInstance;
import uk.co.mtford.abduction.asystem.RuleInstance;
import uk.co.mtford.abduction.logic.instance.PredicateInstance;

/**
 *
 * @author mtford
 */
public class AbductiveFramework {
    protected List<RuleInstance> P; // Logic program.
    protected List<String> A; // Abducibles.
    protected List<DenialInstance> IC; // Integrity constraints.
    
    public AbductiveFramework() {
        P = new LinkedList<RuleInstance>();
        A = new LinkedList<String>();
        IC = new LinkedList<DenialInstance>();
    }
 
    public AbductiveFramework(List<RuleInstance> P, List<String> A, List<DenialInstance> IC) {
        this.P = P;
        this.A = A;
        this.IC = IC;
    }

    public void setIC(List<DenialInstance> IC) {
        this.IC = IC;
    }

    public void setP(List<RuleInstance> P) {
        this.P = P;
    }
    
    public void setA(List<String> A) {
        this.A = A;
    }

    public List<String> getA() {
        return A;
    }

    public List<DenialInstance> getIC() {
        return IC;
    }

    public List<RuleInstance> getP() {
        return P;
    }
    
    public boolean isAbducible(PredicateInstance predicate) {
        return A.contains(predicate.getName());
    }
   
        
    
}
