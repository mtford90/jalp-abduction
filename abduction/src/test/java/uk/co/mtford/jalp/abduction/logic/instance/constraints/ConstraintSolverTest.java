package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantListInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class ConstraintSolverTest {

    private static Logger LOGGER = Logger.getLogger(ConstraintSolverTest.class);

    public ConstraintSolverTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @Test
    public void blockWorldResultTest() {

        VariableInstance T = new VariableInstance("T");
        VariableInstance E1 = new VariableInstance("E1");
        VariableInstance E2 = new VariableInstance("E1");
        VariableInstance E3 = new VariableInstance("E1");
        VariableInstance E4 = new VariableInstance("E1");

        IntegerConstantListInstance list = new IntegerConstantListInstance(1,2,3,4,5,6);

        InIntegerListConstraintInstance listConstraint1 = new InIntegerListConstraintInstance(T,list);
        InIntegerListConstraintInstance listConstraint2 = new InIntegerListConstraintInstance(E1,list);
        InIntegerListConstraintInstance listConstraint3 = new InIntegerListConstraintInstance(E2,list);
        InIntegerListConstraintInstance listConstraint4 = new InIntegerListConstraintInstance(E3,list);
        InIntegerListConstraintInstance listConstraint5 = new InIntegerListConstraintInstance(E4,list);

        LessThanConstraintInstance lessThanConstraint1 = new LessThanConstraintInstance(E1,T);
        LessThanConstraintInstance lessThanConstraint2 = new LessThanConstraintInstance(E3,T);
        LessThanConstraintInstance lessThanConstraint3 = new LessThanConstraintInstance(E4,T);
        LessThanConstraintInstance lessThanConstraint4 = new LessThanConstraintInstance(E2,E1);
        LessThanConstraintInstance lessThanConstraint5 = new LessThanConstraintInstance(E3,E2);
        LessThanConstraintInstance lessThanConstraint6 = new LessThanConstraintInstance(E2,E4);
        LessThanConstraintInstance lessThanConstraint7 = new LessThanConstraintInstance(E1,E4);

        List<IConstraintInstance> constraints = new LinkedList<IConstraintInstance>();

        constraints.add(listConstraint1);
        constraints.add(listConstraint2);
        constraints.add(listConstraint3);
        constraints.add(listConstraint4);
        constraints.add(listConstraint5);

        constraints.add(lessThanConstraint1);
        constraints.add(lessThanConstraint2);
        constraints.add(lessThanConstraint3);
        constraints.add(lessThanConstraint4);
        constraints.add(lessThanConstraint5);
        constraints.add(lessThanConstraint6);
        constraints.add(lessThanConstraint7);

        ChocoConstraintSolverFacade solver = new ChocoConstraintSolverFacade();

        List<Map<VariableInstance,IUnifiableAtomInstance>> solutions = solver.execute(new HashMap<VariableInstance, IUnifiableAtomInstance>(), constraints);

        assertTrue(solutions.size()==6);

        for (Map<VariableInstance, IUnifiableAtomInstance> s:solutions) {
            LOGGER.info("Possible Solution: "+s);
        }

    }
}
