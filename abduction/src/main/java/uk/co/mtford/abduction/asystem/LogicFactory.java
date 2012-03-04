/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import uk.co.mtford.abduction.logic.instance.ILiteralInstance;
import java.io.File;
import uk.co.mtford.abduction.asystem.EqualityInstance;
import uk.co.mtford.abduction.asystem.DenialInstance;
import java.util.Arrays;
import java.util.List;
import uk.co.mtford.abduction.asystem.IASystemInferable;
import uk.co.mtford.abduction.asystem.RuleInstance;
import uk.co.mtford.abduction.logic.instance.ConstantInstance;
import uk.co.mtford.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.abduction.logic.instance.VariableInstance;
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
    
    public PredicateInstance getPredicateInstance(String name, IAtomInstance ... params) {
        return new PredicateInstance(name,params);
    }
   
    
    public DenialInstance getDenialInstance(List<ILiteralInstance> clauses) {
        return new DenialInstance(clauses);
    }
    
    public DenialInstance getDenialInstance(ILiteralInstance[] clause) {
        return new DenialInstance(Arrays.asList(clause));
    }
    
    public RuleInstance getRuleInstance(PredicateInstance head, ILiteralInstance[] clause) {
        return new RuleInstance(head,Arrays.asList(clause));
    }
    
    public RuleInstance getRuleInstance(PredicateInstance head, List<ILiteralInstance> clause) {
        return new RuleInstance(head,clause);
    }
    
    private boolean isLowerCase(char c) {
        if (c>='a'&&c<='z') return true;
        return false;
    }
    
}
