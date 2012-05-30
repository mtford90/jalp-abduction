package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.kernel.model.constraints.Constraint;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class ChocoConstraintSolverFacade implements IConstraintSolverFacade {
    @Override
    public List<Map<VariableInstance, IUnifiableAtomInstance>> executeSolver(Map<VariableInstance, IUnifiableAtomInstance> subst, List<IConstraintInstance> listConstraints) {
        LinkedList<Constraint> chocoConstraints = new LinkedList<Constraint>();
        List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst = new LinkedList<Map<VariableInstance, IUnifiableAtomInstance>>();
        possSubst.add(subst);
        for (IConstraintInstance constraintInstance:listConstraints) {
            boolean reduce = constraintInstance.reduceToChoco(possSubst,chocoConstraints);
            if (!reduce) return null; // One of the natively implemented constraint checks has failed.
        }
        // Now have all the choco constraints.
        // TODO: Do choco stuff.
        return possSubst;
    }


}
