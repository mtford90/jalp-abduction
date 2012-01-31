/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.Set;
import uk.co.mtford.abduction.logic.IUnifiable;

/**
 *
 * @author mtford
 */
public interface iALP {
    public Set<IUnifiable> compute();
}
