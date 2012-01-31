/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.rules;

import java.lang.Thread.State;

/**
 *
 * @author mtford
 */
public abstract class InferenceRule {
    protected InferenceRule next;
    public abstract State[] applyRule(State s);
}
