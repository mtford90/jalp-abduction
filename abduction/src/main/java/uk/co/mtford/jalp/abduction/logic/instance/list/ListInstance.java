package uk.co.mtford.jalp.abduction.logic.instance.list;

import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

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
}
