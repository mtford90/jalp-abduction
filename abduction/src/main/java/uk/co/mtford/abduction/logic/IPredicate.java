/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

/**
 *
 * @author mtford
 */
public interface IPredicate extends IUnifiable {
    public String getName();
    public IUnifiable[] getParameters();
}
