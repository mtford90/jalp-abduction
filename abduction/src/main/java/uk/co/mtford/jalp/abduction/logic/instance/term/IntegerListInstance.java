package uk.co.mtford.jalp.abduction.logic.instance.term;

import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.set.SetVariable;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.tools.NameGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static choco.Choco.makeSetVar;

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

    @Override
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst, HashMap<ITermInstance, Variable> termToVarMap) {
        if (!termToVarMap.containsKey(this)) {
            SetVariable setVariable = makeSetVar(NameGenerator.upperCaseNameGen.getNextName(), getMin(), getMax());
            termToVarMap.put(this,setVariable);
        }
        return true;
    }

    @Override
    public boolean inList(ConstantListInstance constantList, List<Map<VariableInstance, IUnifiableAtomInstance>> possSubst) {
        throw new UnsupportedOperationException();
    }

    public int getMax() {
        int max = Integer.MIN_VALUE;
        for (IntegerConstantInstance constantInstance:list) {
            if (constantInstance.getValue()>max) max = constantInstance.getValue();
        }
        return max;
    }

    public int getMin() {
        int min = Integer.MAX_VALUE;
        for (IntegerConstantInstance constantInstance:list) {
            if (constantInstance.getValue()<min) min = constantInstance.getValue();
        }
        return min;
    }
}
