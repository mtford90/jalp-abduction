/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem.rules;

import java.lang.Thread.State;

/**
 *
 * @author mtford
 */
public abstract class InferenceRule {
    protected InferenceRule next;

    public InferenceRule getNext() {
        return next;
    }

    public void setNext(InferenceRule next) {
        this.next = next;
    }
    
    public abstract State[] applyRuleChain(State s);
}
