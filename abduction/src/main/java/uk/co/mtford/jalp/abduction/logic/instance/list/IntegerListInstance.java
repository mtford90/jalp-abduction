package uk.co.mtford.jalp.abduction.logic.instance.list;

import uk.co.mtford.jalp.abduction.logic.instance.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public class IntegerListInstance extends ListInstance<IntegerConstantInstance> {
    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        IntegerListInstance newListInstance = new IntegerListInstance();
        for (IntegerConstantInstance term:list) {
            newListInstance.getList().add((IntegerConstantInstance) term.performSubstitutions(substitutions));
        }
        return newListInstance;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        IntegerListInstance newListInstance = new IntegerListInstance();
        for (IntegerConstantInstance term:list) {
            newListInstance.getList().add((IntegerConstantInstance) term.deepClone(substitutions));
        }
        return newListInstance;
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        IntegerListInstance newListInstance = new IntegerListInstance();
        for (IntegerConstantInstance term:list) {
            newListInstance.getList().add(term);
        }
        return newListInstance;
    }
}
