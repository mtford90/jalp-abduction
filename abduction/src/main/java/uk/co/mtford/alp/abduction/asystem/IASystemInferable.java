/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.ILogicInstance;

import java.util.List;

/**
 *
 * @author mtford
 */
public interface IASystemInferable extends ILogicInstance {

    public List<ASystemState> applyInferenceRule(AbductiveFramework framework, ASystemState s);
    public List<ASystemState> applyDenialInferenceRule(AbductiveFramework framework, ASystemState s);

}
