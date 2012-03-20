/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.asystem;

import java.util.List;
import java.util.Stack;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;

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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Starting goal selection.");
        }
        IASystemInferable goal = null;
        if (!state.goals.isEmpty()) {
            goal = state.goals.get(0);
        }
        return goal;
    }

    @Override
    protected ASystemState stateTransition(IASystemInferable goal, ASystemState state) {
        if (goal==null) { // No more goals.
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Ran out of goals.");
            }
            return null;
        } 
        List<ASystemState> states = goal.applyInferenceRule(abductiveFramework, state);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Generated " + states.size() + " new states.");
        }
        stateStack.addAll(states);
        if (stateStack.isEmpty()) {
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

}
