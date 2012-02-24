/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public interface IAbductiveLogicProgrammingSystem {
   public abstract Store computeExplanation(List<PredicateInstance> query,
                                            AbductiveFramework abductiveFramework);
}
