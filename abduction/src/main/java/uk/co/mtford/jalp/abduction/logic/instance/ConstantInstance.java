/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import org.apache.log4j.Logger;

import java.util.*;

/**
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
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return this;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new ConstantInstance(value);
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new ConstantInstance(value);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return new HashSet<VariableInstance>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstantInstance)) return false;

        ConstantInstance that = (ConstantInstance) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    @Override
    public String toString() {
        return value;
    }


    @Override
    public List<IReductionResultInstance> reduce(VariableInstance other) {
        return new LinkedList<IReductionResultInstance>();
    }

    @Override
    public List<IReductionResultInstance> reduce(ConstantInstance other) {
        return new LinkedList<IReductionResultInstance>();
    }

    @Override
    public List<IReductionResultInstance> reduce(PredicateInstance other) {
        return new LinkedList<IReductionResultInstance>();
    }

    @Override
    public List<IReductionResultInstance> reduce(IUnifiableAtomInstance other) {
        return new LinkedList<IReductionResultInstance>();
    }

    @Override
    public List<IReductionResultInstance> acceptReduceVisitor(IUnifiableAtomInstance unifiableAtom) {
        return unifiableAtom.reduce(this);
    }

    @Override
    public boolean unify(VariableInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        return other.unify(this,assignment);
    }

    @Override
    public boolean unify(ConstantInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        return this.equals(other);
    }

    @Override
    public boolean unify(PredicateInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        return false;
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
