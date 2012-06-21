/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance.term;

import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.rules.F2RuleNode;
import uk.co.mtford.jalp.abduction.rules.F2bRuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.*;

import static choco.Choco.makeIntVar;
import static choco.Choco.makeSetVar;

/**
 * Represents a variable e.g. X, Y
 *
 * @author mtford
 */
public class VariableInstance implements ITermInstance, IUnifiableInstance {

    private static final Logger LOGGER = Logger.getLogger(VariableInstance.class);
    private int uniqueId;

    String name;

    public int getUniqueId() {
        return uniqueId;
    }

    public VariableInstance(String name) {
        this.name = name;
        uniqueId = UniqueIdGenerator.getUniqueId(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (uniqueId==1) {
            return name;
        }
        else {
            return name + uniqueId;
        }

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

    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        if (substitutions.containsKey(this)) {
            return substitutions.get(this).performSubstitutions(substitutions);
        } else {
            return this;
        }
    }

    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        if (substitutions.containsKey(this)) {
            if (substitutions.get(this).equals(this)) {
                return this;
            }
            return substitutions.get(this).performSubstitutions(substitutions);
        } else {
            VariableInstance clonedVariable = new VariableInstance(new String(name));
            substitutions.put(this, clonedVariable);
            return clonedVariable;
        }
    }

    public IFirstOrderLogicInstance shallowClone() {
        return this;
    }

    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        variables.add(this);
        return variables;
    }

    public List<EqualityInstance> reduce(VariableInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    public List<EqualityInstance> reduce(ConstantInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    public List<EqualityInstance> reduce(PredicateInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    public List<EqualityInstance> reduce(IUnifiableInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    public List<EqualityInstance> acceptReduceVisitor(IUnifiableInstance unifiable) {
        return unifiable.reduce(this);
    }

    public boolean unify(VariableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        List<IUnifiableInstance> keySet = new LinkedList<IUnifiableInstance>(assignment.keySet());
        IUnifiableInstance left = this;
        IUnifiableInstance right = other;
        if (this.equals(other)) return true;
        if (keySet.contains(left)) {
            while (keySet.contains(left)) left = assignment.get(left);
            return left.unify(right,assignment);
        }
        else if (keySet.contains(right)) {
            while (keySet.contains(right)) right = assignment.get(right);
            return left.unify(right,assignment);
        }
        else {
            assignment.put(this,other);
        }
        return true;
    }

    public boolean unify(ConstantInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        List<IUnifiableInstance> keySet = new LinkedList<IUnifiableInstance>(assignment.keySet());
        IUnifiableInstance left = this;
        if (keySet.contains(left)) {
            while (keySet.contains(left)) left = assignment.get(left);
            return left.unify(other,assignment);
        }
        else {
            assignment.put(this,other);
        }
        return true;
    }

    public boolean unify(PredicateInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        List<IUnifiableInstance> keySet = new LinkedList<IUnifiableInstance>(assignment.keySet());
        IUnifiableInstance left = this;
        if (keySet.contains(left)) {
            while (keySet.contains(left)) left = assignment.get(left);
            return left.unify(other,assignment);
        }
        else {
            assignment.put(this,other);
        }
        return true;
    }

    public boolean unify(IUnifiableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return other.acceptUnifyVisitor(this,assignment);
    }

    public boolean acceptUnifyVisitor(IUnifiableInstance unifiable, Map<VariableInstance, IUnifiableInstance> assignment) {
        return unifiable.unify(this,assignment);
    }

    public boolean reduceToChoco(List<Map<VariableInstance, IUnifiableInstance>> possSubst, HashMap<ITermInstance, Variable> termToVarMap) {
        if (!termToVarMap.containsKey(this)) {
            IntegerVariable var = makeIntVar(name+uniqueId);
            termToVarMap.put(this,var);
        }
        return true;
    }

    public boolean inList(CharConstantListInstance constantList, List<Map<VariableInstance, IUnifiableInstance>> possSubst) {
        if (possSubst.isEmpty()) {
            Map<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
            possSubst.add(subst);
        }
        List<Map<VariableInstance, IUnifiableInstance>> newSubstList = new LinkedList<Map<VariableInstance, IUnifiableInstance>>();
        for (Map<VariableInstance,IUnifiableInstance> subst:possSubst) {
            for (CharConstantInstance constant:constantList.getList()) {
                HashMap<VariableInstance,IUnifiableInstance> newSubst = new HashMap<VariableInstance, IUnifiableInstance>(subst);
                boolean unificationSuccess = this.unify(constant,newSubst);
                if (unificationSuccess) newSubstList.add(newSubst);
            }
        }
        if (newSubstList.isEmpty()) return false;
        else {
            while (!possSubst.isEmpty()) possSubst.remove(0);
            possSubst.addAll(newSubstList);
            return true;
        }
    }

    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (((DenialInstance)goals.get(0)).getUniversalVariables().contains(this)) {
            return new F2bRuleNode(abductiveFramework,query,goals);
        }
        else {
            return new F2RuleNode(abductiveFramework,query,goals);
        }
    }

}
