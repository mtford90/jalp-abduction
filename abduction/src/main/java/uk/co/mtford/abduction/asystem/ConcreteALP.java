/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.PriorityQueue;
import uk.co.mtford.abduction.logic.program.Denial;

/**
 *
 * @author mtford
 */
public class ConcreteALP extends ALP {
    
    private PriorityQueue<State> fringe;

    public ConcreteALP(PriorityQueue<State> fringe) {
        this.fringe = fringe;
    }
    
    

    @Override
    public Denial getNextGoal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public State getNextState(Denial goal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
