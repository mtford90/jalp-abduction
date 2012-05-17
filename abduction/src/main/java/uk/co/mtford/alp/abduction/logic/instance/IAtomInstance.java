/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import uk.co.mtford.alp.abduction.asystem.DenialInstance;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;

import java.util.List;

/** Implementation of equality solver.
 *
 * @author mtford
 */
public interface IAtomInstance extends ILogicInstance {
    public List<IASystemInferable> positiveEqualitySolve(IAtomInstance other);
    public List<IASystemInferable> negativeEqualitySolve(DenialInstance denial, IAtomInstance other);
}
