/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.*;

/**
 * @author mtford
 */
public class DenialInstance implements IASystemInferableInstance {

    private static final Logger LOGGER = Logger.getLogger(DenialInstance.class);

    private List<IASystemInferableInstance> body;
    private List<VariableInstance> universalVariables;

    public DenialInstance(List<IASystemInferableInstance> body,
                          List<VariableInstance> universalVariables) {
        this.body = body;
        this.universalVariables = universalVariables;
    }

    public DenialInstance() {
        body = new LinkedList<IASystemInferableInstance>();
        universalVariables = new LinkedList<VariableInstance>();
    }

    public List<IASystemInferableInstance> getBody() {
        return body;
    }

    public void setBody(List<IASystemInferableInstance> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        String rep = "ic";
        /* if (!universalVariables.isEmpty()) {
           rep+="(";
           for (VariableInstance v:universalVariables) rep+=v+",";
           rep = rep.substring(0,rep.length()-1);
           rep+=")";
       } */
        rep += " :- ";
        String bodyRep = body.toString();
        bodyRep = bodyRep.substring(1, bodyRep.length() - 1);
        rep += bodyRep + ".";
        return rep;
    }

    public DenialInstance shallowClone() {
        return new DenialInstance(new LinkedList<IASystemInferableInstance>(body),
                new LinkedList<VariableInstance>(universalVariables));
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferableInstance> goals) {
        LinkedList<DenialInstance> nestedDenialList = new LinkedList<DenialInstance>();
        nestedDenialList.add(this);
        return body.remove(0).getNegativeRootRuleNode(abductiveFramework, nestedDenialList, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferableInstance> goals) {
        nestedDenialList.add(0, this);
        return body.remove(0).getNegativeRootRuleNode(abductiveFramework, nestedDenialList, goals);
    }

    @Override
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        // Substitute universal variables.
        LinkedList<IASystemInferableInstance> newBody = new LinkedList<IASystemInferableInstance>();
        LinkedList<VariableInstance> newUniversalVariables = new LinkedList<VariableInstance>();
        for (VariableInstance v : universalVariables) {
            newUniversalVariables.add((VariableInstance) v.performSubstitutions(substitutions));
        }
        for (IASystemInferableInstance inferable : body) {
            newBody.add((IASystemInferableInstance) inferable.performSubstitutions(substitutions));
        }
        body = newBody;
        universalVariables = newUniversalVariables;
        return this;
    }

    @Override
    public IFirstOrderLogicInstance clone(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        // Substitute universal variables.
        LinkedList<IASystemInferableInstance> newBody = new LinkedList<IASystemInferableInstance>();
        LinkedList<VariableInstance> newUniversalVariables = new LinkedList<VariableInstance>();
        for (VariableInstance v : universalVariables) {
            newUniversalVariables.add((VariableInstance) v.clone(substitutions));
        }
        for (IASystemInferableInstance inferable : body) {
            newBody.add((IASystemInferableInstance) inferable.clone(substitutions));
        }
        return new DenialInstance(newBody, newUniversalVariables);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        for (IASystemInferableInstance inferable : body) {
            variables.addAll(inferable.getVariables());
        }
        return variables;
    }
}
