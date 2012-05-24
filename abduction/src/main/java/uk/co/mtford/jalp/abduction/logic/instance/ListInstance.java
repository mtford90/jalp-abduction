package uk.co.mtford.jalp.abduction.logic.instance;

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
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public Set<VariableInstance> getVariables() {
        throw new UnsupportedOperationException(); // TODO
    }
}
