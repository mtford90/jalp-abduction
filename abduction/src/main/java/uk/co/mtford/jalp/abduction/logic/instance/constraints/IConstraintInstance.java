package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.ITermInstance;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public interface IConstraintInstance extends IInferableInstance {
    public void acceptVisitor(IConstraintSolverProxy constraintSolverProxy);
}
