/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.asystem.DenialInstance;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;
import uk.co.mtford.alp.abduction.asystem.RuleInstance;
import uk.co.mtford.alp.abduction.asystem.RuleUnfoldException;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;

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
    
    protected List<RuleInstance> P; // Logic program.
    protected HashMap<String, Integer> A; // Abducibles.
    protected List<DenialInstance> IC; // Integrity constraints.
    
    public AbductiveFramework() {
        P = new LinkedList<RuleInstance>();
        A = new HashMap<String, Integer>();
        IC = new LinkedList<DenialInstance>();
    }
 
    public AbductiveFramework(List<RuleInstance> P, HashMap<String,Integer> A, List<DenialInstance> IC) {
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

    public HashMap<String, Integer> getA() {
        return A;
    }

    public void setA(HashMap<String, Integer> a) {
        A = a;
    }

    public List<DenialInstance> getIC() {
        return IC;
    }

    public List<RuleInstance> getP() {
        return P;
    }
    
    /** Returns true if the passed predicate is an abducible in P.
     * 
     * @param predicate
     * @return 
     */
    public boolean isAbducible(PredicateInstance predicate) {
        Integer n = null;
        n = A.get(predicate);
        if (n!=null) {
            return n.equals(predicate.getNumParams());
        }
        return false;
    }
   
    /** Unfolds predicate head, if in fact it is the head of a rule.
     *  Otherwise returns an empty list.
     * 
     * @param head
     * @return
     * @throws RuleUnfoldException 
     */
    public List<List<IASystemInferable>> unfoldRule (PredicateInstance head) throws RuleUnfoldException {
        List<List<IASystemInferable>> possibleUnfolds = new LinkedList<List<IASystemInferable>>();
        for (RuleInstance r:P) {
            if (r.getHead().equals(head)) {
                if (LOGGER.isDebugEnabled()) LOGGER.debug("Found a match "+r);
                possibleUnfolds.add(r.unfold(head.getParameters()));
            }
        }
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Found "+possibleUnfolds.size()+" possible rule unfolds.");
        return possibleUnfolds;
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
