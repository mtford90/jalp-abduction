/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction;

import uk.co.mtford.abduction.AbductiveFramework;
import java.util.List;
import java.util.Set;
import uk.co.mtford.abduction.asystem.IASystemInferable;
import uk.co.mtford.abduction.asystem.ASystemStore;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public interface IAbductiveLogicProgrammingSystem {
   public abstract ASystemStore computeExplanation(List<IASystemInferable> query);
}
