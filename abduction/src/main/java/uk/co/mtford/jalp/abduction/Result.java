package uk.co.mtford.jalp.abduction;

import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

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
    private Store store;
    private Map<VariableInstance, IUnifiableAtomInstance> assignments;  // Theta

    public Result(Store store,Map<VariableInstance, IUnifiableAtomInstance> assignments, List<IInferableInstance> query) {
        this.assignments = assignments;
        this.store = store;
        this.query = query;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Map<VariableInstance, IUnifiableAtomInstance> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        this.assignments = assignments;
    }

    public List<IInferableInstance> getQuery() {
        return query;
    }

    public void setQuery(List<IInferableInstance> query) {
        this.query = query;
    }
}
