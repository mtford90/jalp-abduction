/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.asystem.ASystemState;
import uk.co.mtford.alp.abduction.asystem.DenialInstance;
import uk.co.mtford.alp.abduction.asystem.EqualityInstance;
import uk.co.mtford.alp.abduction.asystem.RuleUnfoldException;
import uk.co.mtford.alp.unification.Unifier;

/**
 *
 * @author mtford
 */
public class PredicateInstance implements ILiteralInstance, IAtomInstance {
    
    private static final Logger LOGGER = Logger.getLogger(PredicateInstance.class);
    
    protected String name;
    protected IAtomInstance[] parameters;
    
    public PredicateInstance(String name, IAtomInstance ... parameters) {
        this.name=name;
        this.parameters=parameters;
    }
    
     public PredicateInstance(String name, List<IAtomInstance> parameters) {
        this.name=name;
        this.parameters=parameters.toArray(new IAtomInstance[1]);
    }
    
    public PredicateInstance(String name, String varName, IAtomInstance varValue) {
        this.name=name;
        this.parameters=new IAtomInstance[1];
        parameters[0] = new VariableInstance(varName,varValue);
    }
    
    public PredicateInstance(String name, String varName) {
        this.name=name;
        this.parameters=new IAtomInstance[1];
        parameters[0] = new VariableInstance(varName);
    }
    
    public PredicateInstance(String name) {
        this.name = name;
        this.parameters=null;
    }

    public IAtomInstance[] getParameters() {
        return parameters;
    }

    public void setParameters(IAtomInstance[] parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Returns true if same name and same num parameters.
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
        if (this.parameters.length!=other.parameters.length) {
            return false;
        }
        return true;
    }
    
    /** Returns true if same name and parameters are the same.
     * 
     * @param obj
     * @return 
     */
    
    public boolean deepEquals(Object obj) {
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
        if (this.parameters.length!=other.parameters.length) {
            return false;
        }
        for (int i=0;i<parameters.length;i++) {
            if (!parameters[i].deepEquals(other.parameters[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean contains(IAtomInstance parameter) {
        for (int i=0;i<parameters.length;i++) {
            if (parameters[i].equals(parameter)) return true;
        }
        return false;
    }
    
    public boolean replaceParameter(int num, VariableInstance parameter) {
        if (num<0||num>parameters.length) {
            return false;
        }
        parameters[num] = parameter;
        return true;
    }
    
    public boolean replaceParameter(VariableInstance toBeReplaced, VariableInstance toReplace) {
        for (int i=0; i<parameters.length; i++) {
            if (parameters[i].equals(toBeReplaced)) {
                parameters[i] = toReplace;
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 43 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
    }
    
    public int getNumParams() {
        return parameters.length;
    }

    public void setParameters(VariableInstance[] params) {
        this.parameters=params;
    }
    
    public IAtomInstance getParameter(int i) {
        if (i>parameters.length||i<0) return null;
        return parameters[i];
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
    public Object clone() {
        String clonedName = new String(name);
        IAtomInstance[] clonedParams = new IAtomInstance[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IAtomInstance) parameters[i].clone();
        }
        return new PredicateInstance(clonedName,clonedParams);
    }

    public boolean equalitySolveAssign(IAtomInstance other) {
        List<EqualityInstance> unify = Unifier.unify(this, other);
        if (unify==null) return false;
        return true;
    }
    
    public List<EqualityInstance> equalitySolve(IAtomInstance other) {
        List<EqualityInstance> equalityInstances = new LinkedList<EqualityInstance>();
        if (other.getClass().equals(this.getClass())) {
            PredicateInstance otherPredicate = (PredicateInstance)other;
            if (this.equals(otherPredicate)) {
                for (int i=0;i<parameters.length;i++) {
                    EqualityInstance equalityInstance = new EqualityInstance(parameters[i],otherPredicate.getParameter(i));
                    equalityInstances.add(equalityInstance);
                }
                return equalityInstances;
            }
            return null;
        }
        return null;
    }

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s) {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        if (framework.isAbducible(this)) { 
            possibleStates.addAll(applyRuleA1(framework,s));
        }
        else { 
            try {
                possibleStates.addAll(applyRuleD1(framework,s));
            }
            catch (RuleUnfoldException e) {
                return possibleStates;
            }
        }
        return possibleStates;
    }

    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s) {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        if (framework.isAbducible(this)) { 

                possibleStates.addAll(applyRuleA2(framework,s));

        } 
        else { 
            try {
                possibleStates.addAll(applyRuleD2(framework,s));
            }
            catch (RuleUnfoldException e) {
                return possibleStates;
            }
        }
        return possibleStates;
    }
    
    public List<ASystemState> applyRuleD1(AbductiveFramework framework, ASystemState s) throws RuleUnfoldException {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inference rule D1 to "+this);
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        PredicateInstance thisClone = (PredicateInstance) s.popGoal();
        List<List<ILiteralInstance>> possibleUnfolds = framework.unfoldRule(thisClone);
        for (List<ILiteralInstance> unfold:possibleUnfolds) {
            ASystemState clonedState = (ASystemState) s.clone();
            clonedState.putGoals(unfold);
            possibleStates.add(clonedState);
        }
        return possibleStates;
    }
    
    public List<ASystemState> applyRuleD2(AbductiveFramework framework, ASystemState s) throws RuleUnfoldException {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        DenialInstance denial = (DenialInstance) s.popGoal();
        PredicateInstance thisClone = (PredicateInstance) denial.removeLiteral(0);
        List<List<ILiteralInstance>> possibleUnfolds = framework.unfoldRule(thisClone);
        for (List<ILiteralInstance> unfold:possibleUnfolds) {
            denial.addLiteral(0, unfold);
        }   
        s.putGoal(denial);
        possibleStates.add(s);
        return possibleStates;
    }
    
    public List<ASystemState> applyRuleA1(AbductiveFramework framework, ASystemState s)   {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        // Unify with an already collected abducible
        List<PredicateInstance> collectedAbducibles = s.getStore().getAbducibles();
        for (PredicateInstance abducible:collectedAbducibles) {
            ASystemState clonedState = (ASystemState) s.clone();
            PredicateInstance clonedThis = (PredicateInstance) clonedState.popGoal();
            List<EqualityInstance> unificationResult = null;
                unificationResult = Unifier.unify(clonedThis,abducible);
            for (EqualityInstance e:unificationResult) {
                clonedState.putGoal(e);
            }
            possibleStates.add(clonedState);
        }
        // OR Add a new collected abducible and check that the 
        // collected constraints are satisified.
        ASystemState clonedState = (ASystemState) s.clone();
        PredicateInstance clonedThis = (PredicateInstance) clonedState.popGoal();
        // Check against existing constraints.
        List<DenialInstance> denials = clonedState.getStore().getDenials();
        for (DenialInstance d:denials) {
            ILiteralInstance literal = d.peekLiteral();
            if (literal.equals(clonedThis)) {
                List<EqualityInstance> equalities = null;
                equalities = Unifier.unify((IAtomInstance)literal, clonedThis);
                d.popLiteral();
                for (EqualityInstance e:equalities) {
                    d.addLiteral(0,e);
                }
                clonedState.putGoal(d);
            }
        }
        // Check against existing abducibles.
        List<PredicateInstance> abducibles = clonedState.getStore().getAbducibles();
        for (PredicateInstance a:abducibles) {
            if (a.equals(clonedThis)) {
                List<EqualityInstance> equalities = Unifier.unify(a,clonedThis);
                for (EqualityInstance e:equalities) {
                    clonedState.putGoal(e);
                }
            }
        }
        // Add new abducible.
        clonedState.getStore().getAbducibles().add(clonedThis);
        possibleStates.add(clonedState);
        
        return possibleStates;
    }
    
    public List<ASystemState> applyRuleA2(AbductiveFramework framework, ASystemState s) {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        DenialInstance denial = (DenialInstance) s.popGoal();
        PredicateInstance thisClone = (PredicateInstance) denial.removeLiteral(0);
        for (PredicateInstance collectedAbducible:s.getStore().getAbducibles()) {
            if (thisClone.name.equals(collectedAbducible.name)) {
                List<EqualityInstance> equalities = null;
                    equalities = Unifier.unify(thisClone, collectedAbducible);

                DenialInstance newDenial = new DenialInstance();
                for (EqualityInstance e:equalities) {
                    newDenial.addLiteral(e);
                }
                newDenial.addLiteral(0,((DenialInstance)denial.clone()).getBody());
                s.putGoal(newDenial);
            }
        }
        possibleStates.add(s);
        return possibleStates;
    }

    public Object clone(Map<String, VariableInstance> variablesSoFar) {
        String clonedName = new String(name);
        IAtomInstance[] clonedParams = new IAtomInstance[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IAtomInstance) parameters[i].clone(variablesSoFar);
        }
        return new PredicateInstance(clonedName,clonedParams);
    }
    
}
