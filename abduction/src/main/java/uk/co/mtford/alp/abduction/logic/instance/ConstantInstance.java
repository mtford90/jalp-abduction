/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.asystem.DenialInstance;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mtford
 */
public class ConstantInstance implements ITermInstance {
    
    private static final Logger LOGGER = Logger.getLogger(ConstantInstance.class);
    
    private String value;

    public ConstantInstance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof VariableInstance) { // Special case whereby variables have been assigned a constant value.
            VariableInstance var = (VariableInstance)obj;
            IAtomInstance varValue = var.getValue();
            if (varValue==null) {
                return false;
            }
            while (varValue instanceof VariableInstance) {
                varValue = ((VariableInstance)varValue).value;
            }
            if (varValue==null) {
                return false;
            }
            if (varValue instanceof ConstantInstance) {
                return this.equals(varValue);
            }
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConstantInstance other = (ConstantInstance) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }
    
    public boolean deepEquals(Object obj) {
        return equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    public ILogicInstance clone(Map<String, VariableInstance> variablesSoFar) {
        return new ConstantInstance(value);
    }

    @Override
    public List<VariableInstance> getVariables() {
        return new LinkedList<VariableInstance>();
    }

    @Override
    public List<IASystemInferable> positiveEqualitySolve(IAtomInstance other) {
        if (!(other instanceof ConstantInstance)) {
            return other.positiveEqualitySolve(this);
        }
        LinkedList<IASystemInferable> newInferables = new LinkedList<IASystemInferable>();
        if (this.equals(other)) newInferables.add(new TrueInstance());
        else newInferables.add(new FalseInstance());
        return newInferables;
    }

    @Override
    public List<IASystemInferable> negativeEqualitySolve(DenialInstance denial, IAtomInstance other) {
        List<IASystemInferable> newInferables = new LinkedList<IASystemInferable>();
        denial.addLiteral(0,positiveEqualitySolve(other));
        newInferables.add(denial);
        return newInferables;
    }

}
