/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferableInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    public List<IASystemInferableInstance> expandDefinition(PredicateInstance definedPredicate) throws DefinitionException {
        // TODO
    }

}
