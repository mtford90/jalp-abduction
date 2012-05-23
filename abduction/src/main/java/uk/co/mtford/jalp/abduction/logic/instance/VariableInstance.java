/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

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

    @Override
    public List<EqualityInstance> reduce(VariableInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    @Override
    public List<EqualityInstance> reduce(ConstantInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    @Override
    public List<EqualityInstance> reduce(PredicateInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    @Override
    public List<EqualityInstance> reduce(IUnifiableAtomInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    @Override
    public List<EqualityInstance> acceptReduceVisitor(IUnifiableAtomInstance unifiableAtom) {
        return unifiableAtom.reduce(this);
    }

    @Override
    public boolean unify(VariableInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        if (!this.equals(other)) {
            assignment.put(this,other);
        }
        return true;
    }

    @Override
    public boolean unify(ConstantInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        assignment.put(this,other);
        return true;
    }

    @Override
    public boolean unify(PredicateInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        assignment.put(this,other);
        return true;
    }

    @Override
    public boolean unify(IUnifiableAtomInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        return other.acceptUnifyVisitor(this,assignment);
    }

    @Override
    public boolean acceptUnifyVisitor(IUnifiableAtomInstance unifiableAtom, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        return unifiableAtom.unify(this,assignment);
    }
}
