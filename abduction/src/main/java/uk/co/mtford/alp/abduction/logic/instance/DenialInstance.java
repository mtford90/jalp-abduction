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
public class DenialInstance implements IASystemInferable {

    private static final Logger LOGGER = Logger.getLogger(DenialInstance.class);

    private List<IASystemInferable> body;
    private List<VariableInstance> universalVariables;

    public DenialInstance(List<IASystemInferable> body,
                          List<VariableInstance> universalVariables) {
        this.body = body;
        this.universalVariables = universalVariables;
    }

    public DenialInstance() {
        body = new LinkedList<IASystemInferable>();
        universalVariables = new LinkedList<VariableInstance>();
    }

    public List<IASystemInferable> getBody() {
        return body;
    }

    public void setBody(List<IASystemInferable> body) {
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
        return new DenialInstance(new LinkedList<IASystemInferable>(body),
                new LinkedList<VariableInstance>(universalVariables));
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        LinkedList<DenialInstance> nestedDenialList = new LinkedList<DenialInstance>();
        nestedDenialList.add(this);
        return body.remove(0).getNegativeRootRuleNode(abductiveFramework, nestedDenialList, goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenialList, List<IASystemInferable> goals) {
        nestedDenialList.add(0, this);
        return body.remove(0).getNegativeRootRuleNode(abductiveFramework, nestedDenialList, goals);
    }

    @Override
    public IFirstOrderLogic performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions) {
        // Substitute universal variables.
        LinkedList<IASystemInferable> newBody = new LinkedList<IASystemInferable>();
        LinkedList<VariableInstance> newUniversalVariables = new LinkedList<VariableInstance>();
        for (VariableInstance v : universalVariables) {
            newUniversalVariables.add((VariableInstance) v.performSubstitutions(substitutions));
        }
        for (IASystemInferable inferable : body) {
            newBody.add((IASystemInferable) inferable.performSubstitutions(substitutions));
        }
        return new DenialInstance(newBody, newUniversalVariables);
    }

    @Override
    public Set<VariableInstance> getVariables() {
        HashSet<VariableInstance> variables = new HashSet<VariableInstance>();
        for (IASystemInferable inferable : body) {
            variables.addAll(inferable.getVariables());
        }
        return variables;
    }
}
