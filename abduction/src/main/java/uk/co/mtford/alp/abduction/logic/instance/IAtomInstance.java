/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import java.util.List;

import uk.co.mtford.alp.abduction.asystem.DenialInstance;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;

/**
 *
 * @author mtford
 */
public interface IAtomInstance extends ILogicInstance {
    /** 
     * 
     * @param other
     * @return Empty list if can't be broken down into further equalities
     *         otherwise list of new equalities. Returns null if incompatible i.e. different predicates.
     */
    public List<IASystemInferable> positiveEqualitySolve(IAtomInstance other);
    public List<IASystemInferable> negativeEqualitySolve(IAtomInstance other);
}
