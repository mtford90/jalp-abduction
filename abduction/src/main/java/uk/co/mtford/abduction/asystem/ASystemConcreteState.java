/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.LinkedList;
import uk.co.mtford.abduction.AbductiveFramework;
import java.util.List;
import java.util.Stack;
import org.apache.log4j.Logger;
import uk.co.mtford.abduction.logic.PredicateInstance;

/**
 *
 * @author mtford
 */
public class ASystemConcreteState extends ASystemState {
    
    private static Logger LOGGER = Logger.getLogger(ASystemConcreteState.class);
    
    private Stack<ASystemState> stateStack = new Stack<ASystemState>();

    public ASystemConcreteState(List<IASystemInferable> goals, AbductiveFramework abductiveFramework) {
        super(goals, abductiveFramework);
    }

    /** Simply chooses first goal from the list.
     *  If none left, returns null.
     * 
     * @return 
     */
    @Override
    protected IASystemInferable getNextGoal() {
        String logHead = "getNextGoal(): ";
        LOGGER.info(logHead+"starting goal selection.");
        IASystemInferable goal = null;
        if (!goals.isEmpty()) {
            goal = goals.get(0);
            goals.remove(0);
        }
        LOGGER.info(logHead+"chosen goal is "+goal);
        return goal;
    }

    /** Simple depth first search strategy.
     * 
     * @param goal
     * @return 
     */
    @Override
    protected boolean stateTransition(IASystemInferable goal) {
        String logHead = "stateTransition("+goal+"): ";
        LOGGER.info(logHead+"moving to next state.");
        if (goal==null) { // No more goals.
            LOGGER.info(logHead+"Ran out of goals.");
            return false;
        } 
        LinkedList<IASystemInferable> list = new LinkedList<IASystemInferable>();
        list.add(goal);
        List<ASystemState> states = goal.applyInferenceRule(list, this);
        LOGGER.info(logHead+"Generated new states "+states);
        stateStack.addAll(states);
        ASystemConcreteState nextState = (ASystemConcreteState)stateStack.pop();
        LOGGER.info(logHead+"Next chosen state is "+nextState);
        changeState(nextState);
        return true;
    }
    
    private void changeState(ASystemConcreteState newState) {
        this.stateStack = newState.stateStack;
        this.goals = newState.goals;
        this.abductiveFramework = newState.abductiveFramework;
        this.store = newState.store;
    }

    @Override
    public Object clone() {
        ASystemConcreteState newState = new ASystemConcreteState(goals,abductiveFramework);
        newState.store=(ASystemStore) store.clone();
        return newState;
    }
    
}
