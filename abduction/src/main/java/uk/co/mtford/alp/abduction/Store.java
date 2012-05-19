/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.alp.abduction.logic.instance.EqualityInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;

import java.util.LinkedList;
import java.util.List;


/**
 * An ASystem store of collectables.
 *
 * @author mtford
 */
public class Store implements Cloneable {

    private static final Logger LOGGER = Logger.getLogger(Store.class);

    public List<PredicateInstance> abducibles;
    public List<DenialInstance> denials;
    public List<EqualityInstance> equalities;

    public Store() {
        abducibles = new LinkedList<PredicateInstance>();
        denials = new LinkedList<DenialInstance>();
        equalities = new LinkedList<EqualityInstance>();
    }

    public Store shallowClone() {
        Store store = new Store();
        store.abducibles.addAll(abducibles);
        store.denials.addAll(denials);
        store.equalities.addAll(equalities);
        return store;
    }

}
