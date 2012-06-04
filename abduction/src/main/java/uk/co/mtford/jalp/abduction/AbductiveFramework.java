/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Describes an abductive framework <P,A,IC> where:
 * - P is a logic program.
 * - A is a set of abducible predicates derived from P.
 * - IC is a set of integrity constraints on P.
 * <p/>
 * Objects from this class are passed to the state rewriter where queries
 * are computed against it.
 * <p/>
 * AbductiveFramework objects are constructed and modified by Main (the
 * console interface).
 *
 * @author mtford
 */
public class AbductiveFramework implements Cloneable {

    private static final Logger LOGGER = Logger.getLogger(AbductiveFramework.class);

    protected List<Definition> P; // Logic program.
    protected HashMap<String, Integer> A; // Abducibles.
    protected List<DenialInstance> IC; // Integrity constraints.

    public AbductiveFramework() {
        P = new LinkedList<Definition>();
        A = new HashMap<String, Integer>();
        IC = new LinkedList<DenialInstance>();
    }

    public AbductiveFramework(List<Definition> P, HashMap<String, Integer> A, List<DenialInstance> IC) {
        this.P = P;
        this.A = A;
        this.IC = IC;
    }

    public void setIC(List<DenialInstance> IC) {
        this.IC = IC;
    }

    public void setP(List<Definition> P) {
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

    public List<Definition> getP() {
        return P;
    }

    /**
     * Returns true if the passed predicate is an abducible in P.
     *
     * @param predicate
     * @return
     */
    public boolean isAbducible(PredicateInstance predicate) {
        Integer n = null;
        n = A.get(predicate.getName());
        if (n != null) {
            return n.equals(predicate.getNumParams());
        }
        return false;
    }

    public List<List<IInferableInstance>> unfoldDefinitions(PredicateInstance definedPredicate) throws DefinitionException {
        if (isAbducible(definedPredicate))
            throw new DefinitionException("Attempted to unfold an abducible predicate " + definedPredicate);
        LinkedList<List<IInferableInstance>> possibleUnfolds = new LinkedList<List<IInferableInstance>>();
        for (Definition definition : P) {
            if (definition.getHead().isSameFunction(definedPredicate)) {
                possibleUnfolds.add(definition.unfoldDefinition(definedPredicate.getParameters()));
            }
        }
        return possibleUnfolds;
    }

    @Override
    public String toString() {
        return
                "P  = " + P + "\n" +
                "A  = " + A + "\n" +
                "IC = " + IC;
    }

}
