package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.common.util.iterators.DisposableIntIterator;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 30/05/2012
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class ChocoConstraintSolverFacade implements IConstraintSolverFacade {

    private static final Logger LOGGER = Logger.getLogger(ChocoConstraintSolverFacade.class);

    private LinkedList<Constraint> chocoConstraints;
    private HashMap<ITermInstance,Variable> chocoVariables;
    private HashMap<Constraint,IConstraintInstance> constraintMap;

    public ChocoConstraintSolverFacade () {
        chocoConstraints = new LinkedList<Constraint>();
        chocoVariables = new HashMap<ITermInstance, Variable>();
        constraintMap = new HashMap<Constraint,IConstraintInstance>();
    }

    public LinkedList<Constraint> getChocoConstraints() {
        return chocoConstraints;
    }

    public HashMap<ITermInstance, Variable> getChocoVariables() {
        return chocoVariables;
    }

    @Override
    public List<Map<VariableInstance, IUnifiableAtomInstance>> executeSolver(Map<VariableInstance, IUnifiableAtomInstance> subst, List<IConstraintInstance> listConstraints) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Executing constraint solver");
            LOGGER.debug("Received "+listConstraints);
            LOGGER.debug("Already have "+chocoConstraints);
        }
        List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst = new LinkedList<Map<VariableInstance, IUnifiableAtomInstance>>();
        possSubst.add(subst);
        for (IConstraintInstance constraintInstance:listConstraints) {
            boolean reduce = constraintInstance.reduceToChoco(possSubst,chocoConstraints,chocoVariables,constraintMap);
            if (!reduce) return null; // One of the natively implemented constraint checks has failed.
        }
        // Now have all the choco constraints.
        Model m = new CPModel();
        for (Constraint c:chocoConstraints) {
            Variable[] variables = c.getVariables();
            boolean willAddConstraint = true;
            for (Variable v:variables) {
                if (v instanceof IntegerVariable)  {// TODO: SetVariables shouldn't be in here anyway.
                   IntegerVariable integerVariable = (IntegerVariable) v;
                   if (integerVariable.getUppB()==Integer.MAX_VALUE) willAddConstraint = false;
                   if (integerVariable.getLowB()==Integer.MIN_VALUE) willAddConstraint = false;
                }
            }
            if (willAddConstraint) {
                m.addConstraint(c);
            }
        }

        Iterator addedConstraintIterator = m.getConstraintIterator();

        while (addedConstraintIterator.hasNext()) chocoConstraints.remove(addedConstraintIterator.next());

        Solver s = new CPSolver();
        s.read(m);
        Boolean solveSuccess = s.solve();

        List<Map<VariableInstance,IUnifiableAtomInstance>> newPossSubst = new LinkedList<Map<VariableInstance, IUnifiableAtomInstance>>();

        if (solveSuccess) {

            boolean hadVariables = false;

            List<ITermInstance> expandedTerms = new LinkedList<ITermInstance>();
            for (Map<VariableInstance, IUnifiableAtomInstance> assignments:possSubst) {
                for (ITermInstance term:chocoVariables.keySet()) {
                    boolean expandedTerm = false;
                    for (VariableInstance v:term.getVariables()) {
                        IntegerVariable chocoVariable = (IntegerVariable) chocoVariables.get(v);
                        if (chocoVariable.getUppB()!=Integer.MAX_VALUE&&chocoVariable.getLowB()!=Integer.MIN_VALUE) {
                            hadVariables = true;
                            expandedTerm = true;
                            DisposableIntIterator iterator = chocoVariable.getDomainIterator();
                            while (iterator.hasNext()) {
                                int d = iterator.next();
                                HashMap<VariableInstance,IUnifiableAtomInstance> newSubst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
                                newSubst.putAll(subst);
                                boolean unificationSuccess = v.unify(new IntegerConstantInstance(d),newSubst);
                                if (unificationSuccess) {
                                    newPossSubst.add(newSubst);
                                }
                            }
                        }

                    }
                    if (expandedTerm) {
                        expandedTerms.add(term); // Clean up variables that have been expanded i.e. had non infinite domain.
                    }
                }
            }

            for (ITermInstance term:expandedTerms) {
                chocoVariables.remove(term); // Clean up variables that have been expanded i.e. had non infinite domain.
            }

            if (newPossSubst.isEmpty() && !hadVariables) return possSubst;

        }


        return newPossSubst;
    }

    public ChocoConstraintSolverFacade shallowClone() {
        ChocoConstraintSolverFacade clone = new ChocoConstraintSolverFacade();
        clone.chocoConstraints=new LinkedList<Constraint>(chocoConstraints);
        clone.chocoVariables=new HashMap<ITermInstance,Variable>(chocoVariables);
        clone.constraintMap=new HashMap<Constraint,IConstraintInstance>(constraintMap);
        return clone;
    }

    public HashMap<Constraint, IConstraintInstance> getConstraintMap() {
        return constraintMap;
    }

    public void setConstraintMap(HashMap<Constraint, IConstraintInstance> constraintMap) {
        this.constraintMap = constraintMap;
    }
}
