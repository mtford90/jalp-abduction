/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.*;

import java.util.LinkedList;
import java.util.List;


/** An ASystem store of collectables.
 *
 * @author mtford
 */
public class Store {
    
    private static final Logger LOGGER = Logger.getLogger(Store.class);
    
    public List<PredicateInstance> abducibles;
    public List<DenialInstance> denials;
    public List<IEqualityInstance> equalities;
    
    public Store() {
        abducibles = new LinkedList<PredicateInstance>();
        denials = new LinkedList<DenialInstance>();
        equalities = new LinkedList<IEqualityInstance>();
    }
    
}
