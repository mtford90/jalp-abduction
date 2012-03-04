/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.List;
import uk.co.mtford.abduction.AbductiveFramework;

/**
 *
 * @author mtford
 */
public class ASystemState implements Cloneable {
    protected List<IASystemInferable> goals;
    protected ASystemStore store;
    
    public ASystemState(List<IASystemInferable> goals) {
        this.goals = goals;
        store = new ASystemStore();
    }
    
    public ASystemStore getStore() {
        return store;
    }

    public List<IASystemInferable> getGoals() {
        return goals;
    }
    
    @Override
    public Object clone() {
        ASystemState newState = new ASystemState(goals);
        newState.store=(ASystemStore) store.clone();
        return newState;
    }

    @Override
    public String toString() {
        return "ASystemState{" + "goals=" + goals + ", store=" + store + '}';
    }
    
}
