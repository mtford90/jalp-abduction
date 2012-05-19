/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * @author mtford
 */
public class DefinitionInstance {

    private static final Logger LOGGER = Logger.getLogger(DefinitionInstance.class);

    private PredicateInstance head;
    private List<IASystemInferable> body;
    private HashMap<String, VariableInstance> variables;

    public DefinitionInstance(PredicateInstance head, List<IASystemInferable> body,
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

}
