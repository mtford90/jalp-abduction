package uk.co.mtford.jalp.abduction.logic.instance;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class ListInstance<E extends ITermInstance> implements ITermInstance {

    private LinkedList<E> list;

    public ListInstance() {
        list = new LinkedList<E>();
    }

    public LinkedList<E> getList() {
        return list;
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        ListInstance<E> newListInstance = new ListInstance<E>();
        for (E term:list) {
            newListInstance.getList().add((E) term.performSubstitutions(substitutions));
        }
        return newListInstance;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        ListInstance<E> newListInstance = new ListInstance<E>();
        for (E term:list) {
            newListInstance.getList().add((E) term.deepClone(substitutions));
        }
        return newListInstance;
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        ListInstance<E> newListInstance = new ListInstance<E>();
        for (E term:list) {
            newListInstance.getList().add((E) term);
        }
        return newListInstance;
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
}
