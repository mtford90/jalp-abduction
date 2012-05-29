package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 29/05/2012
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class ChocoConstraintSolverProxy implements IConstraintSolverProxy {
    @Override
    public void visit(GreaterThanConstraintInstance constraintInstance) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void visit(GreaterThanEqConstraintInstance constraintInstance) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void visit(LessThanConstraintInstance constraintInstance) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void visit(LessThanEqConstraintInstance constraintInstance) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void visit(InConstraintInstance constraintInstance) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void visit(NegativeConstraintInstance constraintInstance) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public List<Map<VariableInstance, IUnifiableAtomInstance>> executeSolver() {
        throw new UnsupportedOperationException(); // TODO
    }
}
