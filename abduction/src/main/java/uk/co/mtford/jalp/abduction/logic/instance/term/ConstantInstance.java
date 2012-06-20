/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance.term;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.rules.F2RuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.*;

/**
 * @author mtford
 */
public abstract class ConstantInstance implements ITermInstance, IUnifiableInstance {

    private static final Logger LOGGER = Logger.getLogger(ConstantInstance.class);

    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        return this;
    }

    public Set<VariableInstance> getVariables() {
        return new HashSet<VariableInstance>();
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
        return other.unify(this,assignment);
    }

    public boolean unify(ConstantInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return this.equals(other);
    }

    public boolean unify(PredicateInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return false;
    }

    public boolean unify(IUnifiableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return other.acceptUnifyVisitor(this,assignment);
    }

    public boolean acceptUnifyVisitor(IUnifiableInstance unifiable, Map<VariableInstance, IUnifiableInstance> assignment) {
        return unifiable.unify(this,assignment);
    }

    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return new F2RuleNode(abductiveFramework,query,goals);
    }


}
