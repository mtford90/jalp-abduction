/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import java.util.List;
import uk.co.mtford.alp.abduction.asystem.ASystemStore;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;

/** Describes methods required by an abductive logic system.
 *  Simply computes an explanation when given a query.
 *
 * @author mtford
 */
public interface IAbductiveLogicProgrammingSystem {
   /** Given a query returns an collected abducibles, denials and inequalities
    *  as way of an explanation.
    * 
    * @param query
    * @return 
    */
   public abstract ASystemStore computeExplanation(List<IASystemInferable> query);
}
