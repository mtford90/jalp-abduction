package uk.co.mtford.jalp.abduction.logic.instance.term;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class ListInstance<E extends ITermInstance> implements ITermInstance {

    protected LinkedList<E> list;

    public ListInstance() {
        list = new LinkedList<E>();
    }

    public LinkedList<E> getList() {
        return list;
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        for (E term:list) {
            variables.addAll(term.getVariables());
        }
        return variables;
    }

    @Override
    public String toString () {
        return list.toString();
    }

    @Override
    public RuleNode getNegativeRootRuleNode(IInferableInstance newGoal, AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals) {
        throw new UnsupportedOperationException(); // TODO Nested lists.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListInstance)) return false;

        ListInstance that = (ListInstance) o;

        if (!list.equals(that.list)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
