/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.program;


import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mtford
 */
public class LogicProgram {
    Set<Rule> rules;
    
    public LogicProgram() {
        rules = new HashSet<Rule>();
    }

    public LogicProgram(Set<Rule> rules) {
        this.rules = rules;
    }
    
    public void addRule(Rule rule) {
        rules.add(rule);
    }
    
    public boolean removeRule(Rule rule) {
        return rules.remove(rule);
    }
    
    public boolean containsRule(Rule rule) {
        return rules.contains(rule);
    }
    
}
