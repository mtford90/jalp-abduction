/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.logic.PredicateInstance;
import uk.co.mtford.abduction.logic.DenialInstance;
import uk.co.mtford.abduction.logic.program.Rule;

/**
 *
 * @author mtford
 */
public class AbductiveFramework {
    protected List<Rule> P; // Logic program.
    protected List<PredicateInstance> A; // Abducibles.
    protected List<DenialInstance> IC; // Integrity constraints.
    
    public AbductiveFramework() {
        P = new LinkedList<Rule>();
        A = new LinkedList<PredicateInstance>();
        IC = new LinkedList<DenialInstance>();
    }

    public AbductiveFramework(List<Rule> P, List<PredicateInstance> A, List<DenialInstance> IC) {
        this.P = P;
        this.A = A;
        this.IC = IC;
    }

    public void setA(List<PredicateInstance> A) {
        this.A = A;
    }

    public void setIC(List<DenialInstance> IC) {
        this.IC = IC;
    }

    public void setP(List<Rule> P) {
        this.P = P;
    }

    public List<PredicateInstance> getA() {
        return A;
    }

    public List<DenialInstance> getIC() {
        return IC;
    }

    public List<Rule> getP() {
        return P;
    }
    
    
    
}
