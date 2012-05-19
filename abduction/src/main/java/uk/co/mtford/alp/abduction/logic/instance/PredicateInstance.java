/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author mtford
 */
public class PredicateInstance implements ILiteralInstance, IUnifiableAtomInstance {

    private static final Logger LOGGER = Logger.getLogger(PredicateInstance.class);

    protected String name;
    protected IAtomInstance[] parameters;

    public PredicateInstance(String name, IAtomInstance... parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public PredicateInstance(String name, List<IAtomInstance> parameters) {
        this.name = name;
        this.parameters = parameters.toArray(new IAtomInstance[1]);
    }

    public PredicateInstance(String name, String varName) {
        this.name = name;
        this.parameters = new IAtomInstance[1];
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

    public IAtomInstance[] getParameters() {
        return parameters;
    }

    public void setParameters(IAtomInstance[] parameters) {
        this.parameters = parameters;
    }

    public int getNumParams() {
        return parameters.length;
    }

    public IAtomInstance getParameter(int i) {
        if (i > parameters.length || i < 0) return null;
        return parameters[i];
    }

    /**
     * Returns true if same name and same num parameters.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
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
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 43 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
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

    @Override
    public List<IEqualitySolverResult> equalitySolve(VariableInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        LinkedList<IEqualitySolverResult> result = new LinkedList<IEqualitySolverResult>();
        if (!assignment.containsKey(other)) {
            assignment.put(other, this);
            return result;
        } else {
            return assignment.get(other).equalitySolve(this, assignment);
        }
    }

    @Override
    public List<IEqualitySolverResult> equalitySolve(ConstantInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        LinkedList<IEqualitySolverResult> result = new LinkedList<IEqualitySolverResult>();
        result.add(new FalseInstance());
        return result;
    }

    @Override
    public List<IEqualitySolverResult> equalitySolve(PredicateInstance other, Map<VariableInstance, IUnifiableAtomInstance> assignment) {
        LinkedList<IEqualitySolverResult> result = new LinkedList<IEqualitySolverResult>();
        if (!this.equals(other)) {  // Same name and arity?
            result.add(new FalseInstance());
        } else { // s_bar = t_bar
            for (int i = 0; i < parameters.length; i++) {
                result.add(new EqualityInstance(parameters[i], other.getParameter(0)));
            }
        }
        return result;
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        if (abductiveFramework.getA().containsKey(this)) return new A1RuleNode(abductiveFramework, this, goals);
        else return new D1RuleNode(abductiveFramework, this, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferable> goals) {
        if (abductiveFramework.getA().containsKey(this))
            return new A2RuleNode(abductiveFramework, this, goals, nestedDenialList);
        else return new D2RuleNode(abductiveFramework, this, goals, nestedDenialList);
    }
}
