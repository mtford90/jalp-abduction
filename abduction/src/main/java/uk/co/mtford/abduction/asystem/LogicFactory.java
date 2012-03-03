/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

import java.util.Arrays;
import java.util.List;
import uk.co.mtford.abduction.asystem.IASystemInferable;
import uk.co.mtford.abduction.logic.program.Rule;
import uk.co.mtford.abduction.tools.NameGenerator;

/**
 *
 * @author mtford
 */
public class LogicFactory {
    public ConstantInstance getConstantInstance(String name) {
        return new ConstantInstance("name");
    }
    public ConstantInstance getConstantInstance() {
        String name = NameGenerator.lowerCaseNameGen.getNextName();
        return new ConstantInstance(name);
    }
    public VariableInstance getVariableInstance(String name) {
        return new VariableInstance("name");
    }
    public VariableInstance getVariableInstance() {
        String name = NameGenerator.upperCaseNameGen.getNextName();
        return new VariableInstance(name);
    }

    public PredicateInstance getPredicateInstance(String name, 
                      VariableInstance ... variables) {
        return new PredicateInstance(name,variables);
    }
    
    public PredicateInstance getPredicateInstance(String name, String ... variables) {
        VariableInstance[] variableInstances = new VariableInstance[variables.length];
        for (int i=0;i<variables.length;i++) {
            variableInstances[i]=new VariableInstance(variables[i]);
        }
        return new PredicateInstance(name,variableInstances);
    }
    
    public PredicateInstance getPredicateInstance(String name, IUnifiableInstance ... params) {
        return new PredicateInstance(name,params);
    }
    
    public NegationInstance getNegationInstance(IASystemInferable inferable) {
        return new NegationInstance(inferable);
    }
    
    public EqualityInstance getEqualityInstance(IASystemInferable left, IASystemInferable right) {
        return new EqualityInstance(left,right);
    }
    
    public DenialInstance getDenialInstance(List<IASystemInferable> clauses) {
        return new DenialInstance(clauses);
    }
    
    public DenialInstance getDenialInstance(IASystemInferable[] clause) {
        return new DenialInstance(Arrays.asList(clause));
    }
    
    public Rule getRuleInstance(PredicateInstance head, PredicateInstance[] clause) {
        return new Rule(head,Arrays.asList(clause));
    }
    
    public Rule getRuleInstance(PredicateInstance head, List<PredicateInstance> clause) {
        return new Rule(head,clause);
    }
    
    private boolean isLowerCase(char c) {
        if (c>='a'&&c<='z') return true;
        return false;
    }
    
}
