package uk.co.mtford.jalp.abduction.logic.instance.list;

import uk.co.mtford.jalp.abduction.logic.instance.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public class ConstantListInstance extends ListInstance<CharConstantInstance> {
    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        ConstantListInstance newListInstance = new ConstantListInstance();
        for (CharConstantInstance term:list) {
            newListInstance.getList().add((CharConstantInstance) term.performSubstitutions(substitutions));
        }
        return newListInstance;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        ConstantListInstance newListInstance = new ConstantListInstance();
        for (CharConstantInstance term:list) {
            newListInstance.getList().add((CharConstantInstance) term.deepClone(substitutions));
        }
        return newListInstance;
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        ConstantListInstance newListInstance = new ConstantListInstance();
        for (CharConstantInstance term:list) {
            newListInstance.getList().add(term);
        }
        return newListInstance;
    }
}
