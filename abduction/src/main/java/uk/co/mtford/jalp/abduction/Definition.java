/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;

import java.util.*;

/**
 * @author mtford
 */
public class Definition {

    private static final Logger LOGGER = Logger.getLogger(Definition.class);

    private PredicateInstance head;
    private List<IInferableInstance> body;
    private HashMap<String, VariableInstance> variables;

    public Definition(PredicateInstance head, List<IInferableInstance> body,
                      HashMap<String, VariableInstance> variables) {
        this.body = body;
        this.head = head;
        this.variables = variables;
    }

    public boolean isFact() {
        if (body == null) return true;
        if (body.size() == 0) return true;
        return false;
    }

    public PredicateInstance getHead() {
        return head;
    }

    public void setHead(PredicateInstance head) {
        this.head = head;
    }

    public List<IInferableInstance> getBody() {
        return body;
    }

    public void setBody(List<IInferableInstance> body) {
        this.body = body;
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

    public List<IInferableInstance> unfoldDefinition(IUnifiableAtomInstance... newParameters) throws DefinitionException {
        if (newParameters.length!=head.getNumParams()) throw new DefinitionException("Incorrect number of parameters expanding "+this);
        List<IInferableInstance> unfold = new LinkedList<IInferableInstance>();
        Map<VariableInstance,IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        if (!isFact()) {
            PredicateInstance clonedHead = (PredicateInstance) head.deepClone(subst);
            for (IInferableInstance inferable:body) {
                unfold.add((IInferableInstance) inferable.deepClone(subst));
            }
            for (int i=0;i<newParameters.length;i++){
                unfold.add(0,new EqualityInstance(newParameters[i],clonedHead.getParameter(i)));
            }
        }
        else {

            for (int i=0;i<newParameters.length;i++){
                unfold.add(0,new EqualityInstance(newParameters[i],head.getParameter(i)));
            }
        }

        return unfold;
    }

}
