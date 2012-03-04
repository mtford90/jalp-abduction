/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic.instance;

/**
 *
 * @author mtford
 */
public interface ILogicInstance extends Cloneable {
    @Override
    public boolean equals(Object obj);
    public boolean deepEquals(Object obj);
    public Object clone();
}
