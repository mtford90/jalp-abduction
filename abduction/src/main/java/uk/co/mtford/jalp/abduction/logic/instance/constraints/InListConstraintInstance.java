package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.logic.instance.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class InListConstraintInstance extends ConstraintInstance {
    public InListConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    @Override
    public String toString () {
        return left + " in "+ right;
    }

}
