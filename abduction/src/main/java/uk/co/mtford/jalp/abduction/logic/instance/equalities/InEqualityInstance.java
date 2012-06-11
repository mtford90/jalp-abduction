package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 21/05/2012
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class InEqualityInstance implements IInferableInstance {

    private EqualityInstance equalityInstance;

    public InEqualityInstance(EqualityInstance equalityInstance) {
        this.equalityInstance = equalityInstance;
    }

    public InEqualityInstance(IUnifiableAtomInstance left, IUnifiableAtomInstance right) {
        this.equalityInstance = new EqualityInstance(left,right);
    }

    public EqualityInstance getEqualityInstance() {
        return equalityInstance;
    }

    public void setEqualityInstance(EqualityInstance equalityInstance) {
        this.equalityInstance = equalityInstance;
    }

    @Override
    public String toString() {
        return equalityInstance.getLeft()+"!="+equalityInstance.getRight();
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (!goals.get(0).equals(this)) {  // Sanity check.
            throw new JALPException("Was expecting "+this+" to be at head of goals but have got "+goals.get(0));
        }
        return new InE1RuleNode(abductiveFramework, query, goals);

    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (!(goals.get(0) instanceof DenialInstance)) { // Sanity check.
            throw new JALPException("Was expecting a denial at goal head for creation of negative node, but it isnt!");
        }
        DenialInstance d = (DenialInstance) goals.get(0);
        if (!d.getBody().get(0).equals(this)) {
            throw new JALPException("Was expecting "+this+" at head of denial but got "+d.getBody().get(0));
        }
        return new InE2RuleNode(abductiveFramework, query, goals);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
       equalityInstance = ((EqualityInstance) equalityInstance.performSubstitutions(substitutions));
       return this;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        return new InEqualityInstance((EqualityInstance) equalityInstance.deepClone(substitutions));
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        return new InEqualityInstance((EqualityInstance) equalityInstance.shallowClone());
    }

    @Override
    public Set<VariableInstance> getVariables() {
        return equalityInstance.getVariables();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InEqualityInstance)) return false;

        InEqualityInstance that = (InEqualityInstance) o;

        if (equalityInstance != null ? !equalityInstance.equals(that.equalityInstance) : that.equalityInstance != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return equalityInstance != null ? equalityInstance.hashCode() : 0;
    }
}
