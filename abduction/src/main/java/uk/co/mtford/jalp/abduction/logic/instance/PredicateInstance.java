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
 * Represents a predicate of the form p(\bar u)
 *
 * @author mtford
 */
public class PredicateInstance implements IUnifiableInstance, IInferableInstance, IAtomInstance {

    private static final Logger LOGGER = Logger.getLogger(PredicateInstance.class);

    protected String name;
    protected IUnifiableInstance[] parameters;

    public PredicateInstance(String name, IUnifiableInstance... parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public PredicateInstance(String name, List<IUnifiableInstance> parameters) {
        if (parameters.size()==0) {
            this.parameters=null;
        }
        else {
            this.parameters = parameters.toArray(new IUnifiableInstance[1]);
        }
        this.name = name;
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
        if (parameters==null) return 0;
        return parameters.length;
    }

    public IUnifiableInstance getParameter(int i) {
        if (i > parameters.length || i < 0) return null;
        return parameters[i];
    }

    
    public List<EqualityInstance> reduce(VariableInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    
    public List<EqualityInstance> reduce(ConstantInstance other) {
        return new LinkedList<EqualityInstance>();
    }

    
    public List<EqualityInstance> reduce(PredicateInstance other) {
        LinkedList<EqualityInstance> newEqualities = new LinkedList<EqualityInstance>();
        if (this.isSameFunction(other)) {
            if (parameters != null) {
                for (int i = 0;i<parameters.length;i++) {
                    newEqualities.add(new EqualityInstance(parameters[i],other.getParameter(i)));
                }
            }
        }

        return newEqualities;
    }

    
    public List<EqualityInstance> reduce(IUnifiableInstance other) {
        return other.acceptReduceVisitor(this);
    }

    
    public List<EqualityInstance> acceptReduceVisitor(IUnifiableInstance unifiable) {
        return unifiable.reduce(this);
    }

    
    public boolean unify(VariableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return other.unify(this,assignment);
    }

    
    public boolean unify(ConstantInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return false;
    }

    
    public boolean unify(PredicateInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        if (this.isSameFunction(other)) {
            if (parameters!=null) {
                for (int i = 0;i<parameters.length;i++) {
                    if (!parameters[i].unify(other.getParameter(i),assignment)) {
                        return false;
                    }
                }
            }

            return true;
        }
        return false;
    }

    
    public boolean unify(IUnifiableInstance other, Map<VariableInstance, IUnifiableInstance> assignment) {
        return other.acceptUnifyVisitor(this,assignment);
    }

    
    public boolean acceptUnifyVisitor(IUnifiableInstance unifiable, Map<VariableInstance, IUnifiableInstance> assignment) {
        return unifiable.unify(this,assignment);
    }

    
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (abductiveFramework.isAbducible(this)) return new A1RuleNode(abductiveFramework, query,goals);
        else return new D1RuleNode(abductiveFramework, query, goals);
    }

    
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        if (abductiveFramework.isAbducible(this))
            return new A2RuleNode(abductiveFramework, query, goals);
        else return new D2RuleNode(abductiveFramework,query, goals);
    }

    
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions) {
        LinkedList<IUnifiableInstance> newParameters = new LinkedList<IUnifiableInstance>();
        if (parameters!=null) {
            for (IUnifiableInstance parameter : parameters) {
                IUnifiableInstance newParameter = (IUnifiableInstance) parameter.performSubstitutions(substitutions);
                newParameters.add(newParameter);
            }
            parameters = newParameters.toArray(new IUnifiableInstance[parameters.length]);
        }
        return this;
    }

    
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions) {
        LinkedList<IUnifiableInstance> newParameters = new LinkedList<IUnifiableInstance>();
        if (parameters!=null) {
            for (IUnifiableInstance parameter : parameters) {
                IUnifiableInstance newParameter = (IUnifiableInstance) parameter.deepClone(substitutions);
                newParameters.add(newParameter);
            }
        }
        return new PredicateInstance(new String(name), newParameters);
    }

    
    public IFirstOrderLogicInstance shallowClone() {
        IUnifiableInstance[] newParameters = null;
        if (parameters!=null)  {
           newParameters = new IUnifiableInstance[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                newParameters[i] = (IUnifiableInstance) parameters[i].shallowClone();
            }
        }

        return new PredicateInstance(new String(name), newParameters);
    }

    
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        if (parameters != null) {
            for (IUnifiableInstance parameter : parameters) {
                variables.addAll(parameter.getVariables());
            }
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
        if (this.getNumParams() != other.getNumParams()) {
            return false;
        }
        return true;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PredicateInstance)) return false;

        PredicateInstance that = (PredicateInstance) o;

        if (!name.equals(that.name)) return false;
        if (!Arrays.equals(parameters, that.parameters)) return false;

        return true;
    }

    
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    
    public String toString() {
        String paramList = "";
        if (parameters!=null) {
            for (IUnifiableInstance v : parameters) {
                paramList += v + ",";
            }
            paramList = paramList.substring(0, paramList.length() - 1);
        }

        return name + "(" + paramList + ")";
    }

}
