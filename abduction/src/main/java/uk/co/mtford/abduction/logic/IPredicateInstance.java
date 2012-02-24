/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

/**
 *
 * @author mtford
 */
public interface IPredicateInstance extends IUnifiableInstance {
    public String getName();
    public IUnifiableInstance[] getParameters();
}
