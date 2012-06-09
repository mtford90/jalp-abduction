package uk.co.mtford.jalp.abduction;

import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.ChocoConstraintSolverFacade;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 27/05/2012
 * Time: 09:30
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    private List<IInferableInstance> query;
    private RuleNode root;
    private Store store;
    private Map<VariableInstance, IUnifiableAtomInstance> assignments;  // Theta

    public Result(Store store,Map<VariableInstance, IUnifiableAtomInstance> assignments, List<IInferableInstance> query, RuleNode root) {
        this.assignments = assignments;
        this.store = store;
        this.query = query;
        this.root = root;
    }

    public List<IInferableInstance> getQuery() {
        return query;
    }

    public RuleNode getRoot() {
        return root;
    }

    public Store getStore() {
        return store;
    }

    public Map<VariableInstance, IUnifiableAtomInstance> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        this.assignments = assignments;
    }

    public void setQuery(List<IInferableInstance> query) {
        this.query = query;
    }

    public void setRoot(RuleNode root) {
        this.root = root;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public String toString() {
        String message =
                        "Assignments = " + assignments + "\n\n" +
                        "Abducibles = " + store.abducibles + "\n" +
                        "Integrity Constraints = " + store.denials + "\n" +
                        "Equalities = " + store.equalities + "\n" +
                        "InEqualities = " + store.inequalities +"\n" +
                        "fd = " + store.constraints;
        return message;
    }

}
