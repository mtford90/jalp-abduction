/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.Set;
import uk.co.mtford.abduction.logic.AbstractPredicate;

/**
 *
 * @author mtford
 */
public interface IAbductiveLogicProgrammingSystem {
   public abstract State computeExplanation(Set<AbstractPredicate> query);
}
