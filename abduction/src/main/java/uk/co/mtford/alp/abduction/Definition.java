/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferableInstance;
import uk.co.mtford.alp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

import java.util.*;

/**
 * @author mtford
 */
public class Definition {

    private static final Logger LOGGER = Logger.getLogger(Definition.class);

    private PredicateInstance head;
    private List<IASystemInferableInstance> body;
    private HashMap<String, VariableInstance> variables;

    public Definition(PredicateInstance head, List<IASystemInferableInstance> body,
                      HashMap<String, VariableInstance> variables) {
        this.body = body;
        this.head = head;
        this.variables = variables;
    }

    public boolean isFact() {
        if (body == null) return false;
        if (body.size() == 0) return false;
        return true;
    }

    @Override
    public String toString() {
        String ruleRep = "";
        ruleRep += head;
        if (body != null) {
            String bodyRep = body.toString();
            bodyRep = bodyRep.substring(1, bodyRep.length() - 1);
            ruleRep += " :- " + bodyRep;
        }
        ruleRep += ".";
        return ruleRep;
    }

    public Set<VariableInstance> getHeadVariables() {
        return head.getVariables();
    }

    public List<IASystemInferableInstance> unfoldDefinition(Map<VariableInstance, IUnifiableAtomInstance> substitution) throws DefinitionException {
        LinkedList<IASystemInferableInstance> unfoldedBody = new LinkedList<IASystemInferableInstance>();
        Set<VariableInstance> variables = getHeadVariables();
        if (substitution.size() != variables.size())
            throw new DefinitionException("Incorrect number of parameters when unfolding " + this);
        for (VariableInstance v : variables) {
            if (!substitution.containsKey(v))
                throw new DefinitionException("Missing substitution for " + v + " when unfolding " + this);
        }
        for (IASystemInferableInstance inferable : body) {
            unfoldedBody.add((IASystemInferableInstance) inferable.deepClone(substitution));
        }
        return unfoldedBody;
    }

}
