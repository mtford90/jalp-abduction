/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;

import java.util.*;

/**
 * @author mtford
 */
public abstract class ConstantInstance implements ITermInstance, IUnifiableAtomInstance {

    private static final Logger LOGGER = Logger.getLogger(ConstantInstance.class);

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return this;
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return new HashSet<VariableInstance>();
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
