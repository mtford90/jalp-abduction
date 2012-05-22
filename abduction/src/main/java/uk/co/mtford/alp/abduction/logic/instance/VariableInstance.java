/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.tools.UniqueIdGenerator;

import java.util.*;

/**
 * @author mtford
 */
public class VariableInstance implements ITermInstance {

    private static final Logger LOGGER = Logger.getLogger(VariableInstance.class);
    private int uniqueId = UniqueIdGenerator.getUniqueId();

    String name;

    public int getUniqueId() {
        return uniqueId;
    }

    public VariableInstance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + uniqueId;
    }

    /**
     * Returns true if variable names at the same. Not concerned with value.
     */

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableInstance)) return false;

        VariableInstance that = (VariableInstance) o;

        if (uniqueId != that.uniqueId) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public List<IEqualitySolverResultInstance> equalitySolve(VariableInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        LinkedList<IEqualitySolverResultInstance> result = new LinkedList<IEqualitySolverResultInstance>();
        if (assignment.containsKey(this)) {
            return assignment.get(this).equalitySolve(other, assignment);
        } else if (assignment.containsKey(other)) {
            return assignment.get(other).equalitySolve(this, assignment);
        } else {
            assignment.put(this, other);
            return result;
        }
    }

    @Override
    public List<IEqualitySolverResultInstance> equalitySolve(ConstantInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        LinkedList<IEqualitySolverResultInstance> result = new LinkedList<IEqualitySolverResultInstance>();
        if (assignment.containsKey(this)) {
            return assignment.get(this).equalitySolve(other, assignment);
        } else {
            assignment.put(this, other);
            return result;
        }
    }

    @Override
    public List<IEqualitySolverResultInstance> equalitySolve(PredicateInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        LinkedList<IEqualitySolverResultInstance> result = new LinkedList<IEqualitySolverResultInstance>();
        if (assignment.containsKey(this)) {
            return assignment.get(this).equalitySolve(other, assignment);
        } else {
            assignment.put(this, other); // TODO Doing this incorrect? Want equational solved formed instead really?
            return result;
        }
    }

    @Override
    public List<IEqualitySolverResultInstance> equalitySolve(IUnifiableAtomInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        return other.equalitySolve(this, assignment);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        if (substitutions.containsKey(this)) {
            return substitutions.get(this).performSubstitutions(substitutions);
        } else {
            return this;
        }
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        if (substitutions.containsKey(this)) {
            return substitutions.get(this).performSubstitutions(substitutions);
        } else {
            VariableInstance clonedVariable = new VariableInstance(new String(name));
            substitutions.put(this, clonedVariable);
            return clonedVariable;
        }
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new VariableInstance(name);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        variables.add(this);
        return variables;
    }
}
