/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.asystem.*;

import java.util.*;

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
        List<List<IASystemInferable>> possibleUnfolds = framework.unfoldRule(thisClone);
        for (List<IASystemInferable> unfold:possibleUnfolds) {
            s.putGoals(unfold);
            ASystemState clonedState = (ASystemState) s.clone(); // TODO. Breaks here. Cloning is fucked up.
            for (int i=0;i<unfold.size();i++) s.popGoal();
            possibleStates.add(0,clonedState);
        }
        return possibleStates;
    }
    
    public List<ASystemState> applyRuleD2(AbductiveFramework framework, ASystemState s) throws RuleUnfoldException {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inference rule D2 to "+this);
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        DenialInstance denial = (DenialInstance) s.popGoal();
        PredicateInstance thisClone = (PredicateInstance) denial.removeLiteral(0);
        List<List<IASystemInferable>> possibleUnfolds = framework.unfoldRule(thisClone); // TODO change literalinstance to denial instance.
        for (List<IASystemInferable> unfold:possibleUnfolds) {
            for (IASystemInferable l:unfold) {
                denial.addLiteral(l);
                for (VariableInstance v:l.getVariables()) {
                    denial.getUniversalVariables().add(v);
                }
                
            }
            
        }
        
        s.putGoal(denial);
        possibleStates.add(0,s);
        return possibleStates;
    }
    
    public List<ASystemState> applyRuleA1(AbductiveFramework framework, ASystemState s)   {
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        // SELECT an arbitrary matching collected abducible and unify with it.
        for (PredicateInstance abducible:s.getStore().getAbducibles()) {
            ASystemState clonedState = s.clone();
            PredicateInstance clonedThis = (PredicateInstance)s.popGoal();
            List<IASystemInferable> equationalSolved = clonedThis.positiveEqualitySolve(abducible);
            s.putGoals(equationalSolved);
            possibleStates.add(s);
        }

        // OR
        // Unify with all constraints that have matching abducible as head and add those constraints to the goals.
        // Create inequalities for parameters in our abducible and all those collected to ensure that they cant
        // be unified. Add these to the goals.
        // Add our abducible to the collected abducibles.

        ASystemState clonedState = s.clone();
        PredicateInstance clonedThis = (PredicateInstance)s.popGoal();
        for (DenialInstance denial:s.getStore().getDenials()) {
            IASystemInferable inferable = denial.peekLiteral();
            if (clonedThis.equals(inferable)) {
                denial.popLiteral();
                List<IASystemInferable> equationalSolved = clonedThis.positiveEqualitySolve((IAtomInstance) inferable);



            }
        }

    }
    
    public List<ASystemState> applyRuleA2(AbductiveFramework framework, ASystemState s) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inference rule A2 to "+this);
        LinkedList<ASystemState> possibleStates = new LinkedList<ASystemState>();
        DenialInstance denial = (DenialInstance) s.popGoal();
        for (PredicateInstance collectedAbducible:s.getStore().getAbducibles()) {
            if (collectedAbducible.equals(this)) { // Predicate with same name and arity.
                DenialInstance clonedDenial = (DenialInstance) denial.clone(new HashMap<String, VariableInstance>());
                PredicateInstance clonedThis = (PredicateInstance) clonedDenial.popLiteral();
                List<IASystemInferable> newInferables = clonedThis.positiveEqualitySolve(collectedAbducible);
                clonedDenial.addLiteral(0,newInferables);
                s.putGoal(clonedDenial);
            }
        }
        s.getStore().getDenials().add(denial);
        possibleStates.add(s);
        return possibleStates;
    }

    public ILogicInstance clone(Map<String, VariableInstance> variablesSoFar) {
        String clonedName = new String(name);
        IAtomInstance[] clonedParams = new IAtomInstance[parameters.length];
        for (int i=0;i<clonedParams.length;i++) {
            clonedParams[i]=(IAtomInstance) parameters[i].clone(variablesSoFar);
        }
        return new PredicateInstance(clonedName,clonedParams);
    }

    @Override
    public List<IASystemInferable> positiveEqualitySolve(IAtomInstance other) {
        LinkedList<IASystemInferable> newInferables = new LinkedList<IASystemInferable>();
        if (this.equals(other)) {
            for (int i=0;i<parameters.length;i++) {
                PredicateInstance otherPredicate = (PredicateInstance)other;
                newInferables.add(new EqualityInstance(parameters[i],otherPredicate.getParameter(i)));
            }
        }
        else {
            newInferables.add(new FalseInstance());
        }
        return newInferables;
    }

    @Override
    public List<IASystemInferable> negativeEqualitySolve(DenialInstance denial, IAtomInstance other) {
        List<IASystemInferable> newInferables = new LinkedList<IASystemInferable>();
        denial.addLiteral(0,positiveEqualitySolve(other));
        newInferables.add(denial);
        return newInferables;
    }

    @Override
    public List<VariableInstance> getVariables() { // TODO: This is probs very inefficient.
        LinkedList<VariableInstance> variables = new LinkedList<VariableInstance>();
        for (IAtomInstance atom:parameters) {
            if (atom instanceof VariableInstance) {
                atom = (VariableInstance) ((VariableInstance) atom).getDeepValue();
            }
            if (atom instanceof VariableInstance) {
                variables.add((VariableInstance)atom);
            }
            if (atom instanceof PredicateInstance) {
                variables.addAll(((PredicateInstance) atom).getVariables());
            }
        }
        return variables;
    }
}
