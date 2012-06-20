/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.*;

import java.util.*;

/**
 * @author mtford
 */
public class PredicateInstance implements IUnifiableInstance, IInferableInstance {

    private static final Logger LOGGER = Logger.getLogger(PredicateInstance.class);

    protected String name;
    protected IUnifiableInstance[] parameters;

    public PredicateInstance(String name, IUnifiableInstance... parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public PredicateInstance(String name, List<IUnifiableInstance> parameters) {
        this.name = name;
        this.parameters = parameters.toArray(new IUnifiableInstance[1]);
    }

    public PredicateInstance(String name, String varName) {
        this.name = name;
        this.parameters = new IUnifiableInstance[1];
        parameters[0] = new VariableInstance(varName);
    }

    public PredicateInstance(String name) {
        this.name = name;
        this.parameters = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IUnifiableInstance[] getParameters() {
        return parameters;
    }

    public void setParameters(IUnifiableInstance[] parameters) {
        this.parameters = parameters;
    }

    public int getNumParams() {
        return parameters.length;
    }

    public IUnifiableInstance getParameter(int i) {
        if (i > parameters.length || i < 0) return null;
        return parameters[i];
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
        LinkedList<EqualityInstance> newEqualities = new LinkedList<EqualityInstance>();
        if (this.isSameFunction(other)) {
            for (int i = 0;i<parameters.length;i++) {
                newEqualities.add(new EqualityInstance(parameters[i],other.getParameter(i)));
            }
        }

        return newEqualities;
    }

    @Override
    public List<EqualityInstance> reduce(IUnifiableInstance other) {
        return other.acceptReduceVisitor(this);
    }

    @Override
    public List<EqualityInstance> acceptReduceVisitor(IUnifiableInstance unifiable) {
        return unifiable.reduce(this);
    }

    @Override
    public boolean unify(VariableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return other.unify(this,assignment);
    }

    @Override
    public boolean unify(ConstantInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return false;
    }

    @Override
    public boolean unify(PredicateInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        if (this.isSameFunction(other)) {
            for (int i = 0;i<parameters.length;i++) {
                if (!parameters[i].unify(other.getParameter(i),assignment)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean unify(IUnifiableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return other.acceptUnifyVisitor(this,assignment);
    }

    @Override
    public boolean acceptUnifyVisitor(IUnifiableInstance unifiable, Map<VariableInstance, IUnifiableInstance> assignment) {
        return unifiable.unify(this,assignment);
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (abductiveFramework.isAbducible(this)) return new A1RuleNode(abductiveFramework, query,goals);
        else return new D1RuleNode(abductiveFramework, query, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (abductiveFramework.isAbducible(this))
            return new A2RuleNode(abductiveFramework, query, goals);
        else return new D2RuleNode(abductiveFramework,query, goals);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        LinkedList<IAtomInstance> newParameters = new LinkedList<IAtomInstance>();
        for (IAtomInstance parameter : parameters) {
            IAtomInstance newParameter = (IAtomInstance) parameter.performSubstitutions(substitutions);
            newParameters.add(newParameter);
        }
        parameters = newParameters.toArray(new IUnifiableInstance[parameters.length]);
        return this;
    }

    @Override
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        LinkedList<IUnifiableInstance> newParameters = new LinkedList<IUnifiableInstance>();
        for (IUnifiableInstance parameter : parameters) {
            IUnifiableInstance newParameter = (IUnifiableInstance) parameter.deepClone(substitutions);
            newParameters.add(newParameter);
        }
        return new PredicateInstance(new String(name), newParameters);
    }

    @Override
    public IFirstOrderLogicInstance shallowClone() {
        IUnifiableInstance[] newParameters = new IUnifiableInstance[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            newParameters[i] = (IUnifiableInstance) parameters[i].shallowClone();
        }
        return new PredicateInstance(new String(name), newParameters);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        for (IAtomInstance parameter : parameters) {
            variables.addAll(parameter.getVariables());
        }
        return variables;
    }

    /**
     * Returns true if same name and same num parameters.
     *
     * @param obj
     * @return
     */
    public boolean isSameFunction(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PredicateInstance other = (PredicateInstance) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.parameters.length != other.parameters.length) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PredicateInstance)) return false;

        PredicateInstance that = (PredicateInstance) o;

        if (!name.equals(that.name)) return false;
        if (!Arrays.equals(parameters, that.parameters)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public String toString() {
        String paramList = "";
        for (IAtomInstance v : parameters) {
            paramList += v + ",";
        }
        paramList = paramList.substring(0, paramList.length() - 1);
        return name + "(" + paramList + ")";
    }

}
