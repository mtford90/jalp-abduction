/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.List;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.ILogicInstance;

/**
 *
 * @author mtford
 */
public interface IASystemInferable extends ILogicInstance {
    /** Applies the ASystem inference rule relevant to this particular
     *  logical formula.
     * @param s
     * @return 
     */
    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s);
    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s);

}
