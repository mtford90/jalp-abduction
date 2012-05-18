/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.DefinitionInstance;
import uk.co.mtford.alp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/** Describes an abductive framework <P,A,IC> where:
 *      - P is a logic program.
 *      - A is a set of abducible predicates derived from P.
 *      - IC is a set of integrity constraints on P.
 * 
 *  Objects from this class are passed to the state rewriter where queries
 *  are computed against it. 
 * 
 *  AbductiveFramework objects are constructed and modified by Main (the 
 *  console interface).
 *
 * @author mtford
 */
public class AbductiveFramework implements Cloneable {
    
    private static final Logger LOGGER = Logger.getLogger(AbductiveFramework.class);
    
    protected List<DefinitionInstance> P; // Logic program.
    protected HashMap<String, Integer> A; // Abducibles.
    protected List<DenialInstance> IC; // Integrity constraints.
    
    public AbductiveFramework() {
        P = new LinkedList<DefinitionInstance>();
        A = new HashMap<String, Integer>();
        IC = new LinkedList<DenialInstance>();
    }
 
    public AbductiveFramework(List<DefinitionInstance> P, HashMap<String,Integer> A, List<DenialInstance> IC) {
        this.P = P;
        this.A = A;
        this.IC = IC;
    }

    public void setIC(List<DenialInstance> IC) {
        this.IC = IC;
    }

    public void setP(List<DefinitionInstance> P) {
        this.P = P;
    }

    public HashMap<String, Integer> getA() {
        return A;
    }

    public void setA(HashMap<String, Integer> a) {
        A = a;
    }

    public List<DenialInstance> getIC() {
        return IC;
    }

    public List<DefinitionInstance> getP() {
        return P;
    }
    
    /** Returns true if the passed predicate is an abducible in P.
     * 
     * @param predicate
     * @return 
     */
    public boolean isAbducible(PredicateInstance predicate) {
        Integer n = null;
        n = A.get(predicate.getName());
        if (n!=null) {
            return n.equals(predicate.getNumParams());
        }
        return false;
    }

    @Override
    public String toString() {
        return "AbductiveFramework" + "\n" +
               "{" + "\n" +
               "P  = " + P + "\n" +
               "A  = " + A + "\n" +
               "IC = " + IC + "\n" +
               "}";
    }

}
