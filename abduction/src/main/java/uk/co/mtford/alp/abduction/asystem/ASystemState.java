/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;

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
        List<IASystemInferable> newGoals = new LinkedList<IASystemInferable>();
        HashMap<String, VariableInstance> variablesSoFar = new HashMap<String, VariableInstance>();
        for (IASystemInferable g:goals) {
            newGoals.add((IASystemInferable)g.clone(variablesSoFar));
        }
        ASystemState newState = new ASystemState(newGoals);
        newState.store=(ASystemStore) store.clone(variablesSoFar);
        return newState;
    }

    @Override
    public String toString() {
        String representation = "G:"+goals+"\n"+
                                "S:"+store;
        return representation;
    }

    public void putGoals(List<IASystemInferable> unfold) {
        goals.addAll(0, unfold);
    }

    public void putGoal(IASystemInferable goal) {
        goals.add(0,goal);
    }
    
    public boolean hasGoalsRemaining() {
        return !goals.isEmpty();
    }
    
}
