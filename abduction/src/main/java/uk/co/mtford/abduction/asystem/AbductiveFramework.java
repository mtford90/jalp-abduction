/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.HashSet;
import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;
import uk.co.mtford.abduction.logic.program.Denial;
import uk.co.mtford.abduction.logic.program.Rule;

/**
 *
 * @author mtford
 */
public abstract class AbductiveFramework {
    protected Set<Rule> P; // Logic program.
    protected Set<AbstractPredicate> A; // Abducibles.
    protected Set<Denial> IC; // Integrity constraints.
    
    public AbductiveFramework() {
        P = new HashSet<Rule>();
        A = new HashSet<AbstractPredicate>();
        IC = new HashSet<Denial>();
    }

    public AbductiveFramework(Set<Rule> P, Set<AbstractPredicate> A, Set<Denial> IC) {
        this.P = P;
        this.A = A;
        this.IC = IC;
    }

    public void setA(Set<AbstractPredicate> A) {
        this.A = A;
    }

    public void setIC(Set<Denial> IC) {
        this.IC = IC;
    }

    public void setP(Set<Rule> P) {
        this.P = P;
    }
    
}
