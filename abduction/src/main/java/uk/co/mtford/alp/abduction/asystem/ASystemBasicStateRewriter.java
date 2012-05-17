/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;

import java.util.List;
import java.util.Stack;

/** A basic implementation of an ASystem state rewriter.
 *  Uses no heuristics. Simply LIFO on next state and goal selection.
 *
 * @author mtford
 */
public class ASystemBasicStateRewriter extends ASystemStateRewriter {
    
    final private static Logger LOGGER = Logger.getLogger(ASystemBasicStateRewriter.class);
    
    private Stack<ASystemState> stateStack = new Stack<ASystemState>();

    public ASystemBasicStateRewriter(AbductiveFramework abductiveFramework) {
        super(abductiveFramework);
    }

    @Override
    protected IASystemInferable getNextGoal(ASystemState state) {
        IASystemInferable goal = null;
        if (!state.goals.isEmpty()) {
            goal = state.goals.get(0);
        }
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Selected goal "+goal);
        return goal;
    }

    @Override
    protected ASystemState stateTransition(IASystemInferable goal, ASystemState state) {
        if (goal==null && LOGGER.isDebugEnabled()) {
           LOGGER.debug("Ran out of goals.");
        }
        if (goal!=null) {
            List<ASystemState> states = goal.applyInferenceRule(abductiveFramework, state);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Generated " + states.size() + " new states.");
            }
            for (int i=0;i<states.size();i++) stateStack.push(states.get(i));
        }
        if (stateStack.isEmpty()) { // No more possible states.
            return null;
        }
        ASystemState nextState = stateStack.pop();
        return nextState;
    }

    @Override
    public void reset() {
        LOGGER.info("ASystemRewriter has been reset.");
        this.abductiveFramework=new AbductiveFramework();
        this.stateStack=new Stack<ASystemState>();
    }

    @Override
    protected boolean hasMoreStates() {
        return !stateStack.isEmpty();
    }

}
