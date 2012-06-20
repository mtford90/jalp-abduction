package uk.co.mtford.jalp.abduction.logic.instance.term;

import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public class CharConstantListInstance extends ListInstance<CharConstantInstance> {

    public CharConstantListInstance() {
        super();
    }

    public CharConstantListInstance(Collection<CharConstantInstance> constants) {
        super();
        this.getList().addAll(constants);
    }

    public CharConstantListInstance(CharConstantInstance[] constants) {
        super();
        for (CharConstantInstance instance:constants) {
            this.getList().add(instance);
        }
    }

    public CharConstantListInstance(String... strings) {
        super();
        for (String s:strings) {
            this.getList().add(new CharConstantInstance(s));
        }
    }

    
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        CharConstantListInstance newListInstance = new CharConstantListInstance();
        for (CharConstantInstance term:list) {
            CharConstantInstance newTerm = (CharConstantInstance) term.performSubstitutions(substitutions);
            newListInstance.getList().add(newTerm);
        }
        list = newListInstance.getList();
        return this;
    }

    
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        CharConstantListInstance newListInstance = new CharConstantListInstance();
        for (CharConstantInstance term:list) {
            newListInstance.getList().add((CharConstantInstance) term.deepClone(substitutions));
        }
        return newListInstance;
    }

    
    public IFirstOrderLogicInstance shallowClone() {
        CharConstantListInstance newListInstance = new CharConstantListInstance();
        for (CharConstantInstance term:list) {
            newListInstance.getList().add((CharConstantInstance) term.shallowClone());
        }
        return newListInstance;
    }

    
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, HashMap<ITermInstance, Variable> termToVarMap) {
        throw new UnsupportedOperationException(); // Dealt with natively.
    }

    
    public boolean inList(CharConstantListInstance constantList, List<Map<VariableInstance, IUnifiableInstance>> possSubst) {
        throw new UnsupportedOperationException();
    }
}
