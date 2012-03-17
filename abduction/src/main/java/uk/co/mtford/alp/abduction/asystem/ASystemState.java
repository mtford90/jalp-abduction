/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.List;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;

/**
 *
 * @author mtford
 */
public class ASystemState implements Cloneable {
    
    private static final Logger LOGGER = Logger.getLogger(ASystemState.class);
    
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
    
    public IASystemInferable popGoal() {
        IASystemInferable goal = goals.get(0);
        goals.remove(0);
        return goal;
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

    public void putGoals(List<ILiteralInstance> unfold) {
        goals.addAll(0, unfold);
    }

    public void putGoal(IASystemInferable goal) {
        goals.add(0,goal);
    }
    
}
