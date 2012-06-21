package uk.co.mtford.jalp.abduction.logic.instance.term;

import choco.Choco;
import choco.kernel.model.variables.Variable;
import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.logic.instance.IFirstOrderLogicInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An integer constant e.g. 50, 3
 */
public class IntegerConstantInstance extends ConstantInstance {
    private static final Logger LOGGER = Logger.getLogger(ConstantInstance.class);
    private Integer value;

    public IntegerConstantInstance(Integer value1) {
        value = value1;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return new IntegerConstantInstance(new Integer(value));
    }

    
    public IFirstOrderLogicInstance shallowClone() {
        return new IntegerConstantInstance(new Integer(value));
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerConstantInstance)) return false;

        IntegerConstantInstance that = (IntegerConstantInstance) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    
    public int hashCode() {
        return value.hashCode();
    }

    
    public String toString() {
        return value.toString();
    }

    
    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, HashMap<ITermInstance, Variable> termToVarMap) {
        if (!termToVarMap.containsKey(this)) {
            termToVarMap.put(this,Choco.constant(value));
        }
        return true;
    }

    
    public boolean inList(CharConstantListInstance constantList, List<Map<VariableInstance, IUnifiableInstance>> possSubst) {
        throw new UnsupportedOperationException(); // Dealt with by Choco.
    }
}
