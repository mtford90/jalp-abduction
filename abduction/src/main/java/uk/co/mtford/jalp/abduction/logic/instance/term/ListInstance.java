package uk.co.mtford.jalp.abduction.logic.instance.term;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.*;

/** Abstract class for lists e.g. [1,2,3], [bob,carl]
 * @author Michael Ford
 */
public abstract class ListInstance<E extends ITermInstance> implements ITermInstance {

    protected LinkedList<E> list;

    public ListInstance() {
        list = new LinkedList<E>();
    }

    public LinkedList<E> getList() {
        return list;
    }

     
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        for (E term:list) {
            variables.addAll(term.getVariables());
        }
        return variables;
    }

     
    public String toString () {
        return list.toString();
    }

     
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO Nested lists.
    }

     
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListInstance)) return false;

        ListInstance that = (ListInstance) o;

        if (!list.equals(that.list)) return false;

        return true;
    }

     
    public int hashCode() {
        return list.hashCode();
    }
}
