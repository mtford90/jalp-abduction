package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.solver.Solver;
import choco.kernel.solver.variables.integer.IntVar;
import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.*;

/**
 * An interface to the Choco constraint solver.
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

    public List<Map<VariableInstance, IUnifiableInstance>> execute(Map<VariableInstance, IUnifiableInstance> subst, List<IConstraintInstance> listConstraints) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Executing constraint solver");
            LOGGER.debug("Received "+listConstraints);
        }

        List<Map<VariableInstance,IUnifiableInstance>> possSubst = new LinkedList<Map<VariableInstance, IUnifiableInstance>>();
        possSubst.add(subst);
        for (IConstraintInstance constraintInstance:listConstraints) {
            boolean reduce = constraintInstance.reduceToChoco(possSubst,chocoConstraints,chocoVariables,constraintMap);
            if (!reduce) {
                if (LOGGER.isDebugEnabled()) LOGGER.debug("Native constraint checked failed. Found 0 possible assignments.");
                return new LinkedList<Map<VariableInstance, IUnifiableInstance>>(); // One of the natively implemented constraint checks has failed.
            }
        }



        // Now have all the choco constraints.
        Model m = new CPModel();
        for (Variable v:chocoVariables.values()) {
            m.addVariable(v);
        }
        for (Constraint c:chocoConstraints) {
                m.addConstraint(c);
        }

        Iterator addedConstraintIterator = m.getConstraintIterator();

        while (addedConstraintIterator.hasNext()) chocoConstraints.remove(addedConstraintIterator.next());

        Solver s = new CPSolver();

        s.read(m);

        List<Map<VariableInstance,IUnifiableInstance>> newPossSubst = new LinkedList<Map<VariableInstance, IUnifiableInstance>>();

        boolean success = s.solve();

        if (success) {
            do {
                for (Map<VariableInstance,IUnifiableInstance> nativeSubst:possSubst) {
                    HashMap<VariableInstance,IUnifiableInstance> newSubst = new HashMap<VariableInstance,IUnifiableInstance>(nativeSubst);
                    boolean unificationSuccess = true;
                    for (ITermInstance term:chocoVariables.keySet()) {
                        Variable chocoVariable = chocoVariables.get(term);
                        int n = ((IntVar)s.getVar(chocoVariable)).getVal();
                        IUnifiableInstance v = (IUnifiableInstance) term;
                        unificationSuccess = v.unify(new IntegerConstantInstance(n),newSubst);
                    }
                    if (unificationSuccess) newPossSubst.add(newSubst);
                }
            } while (success = s.nextSolution() == true);
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
