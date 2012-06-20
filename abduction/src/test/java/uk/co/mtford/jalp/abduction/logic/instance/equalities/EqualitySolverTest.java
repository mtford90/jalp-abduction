package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 23/05/2012
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
public class EqualitySolverTest {

    private static Logger LOGGER = Logger.getLogger(EqualitySolverTest.class);

    EqualitySolver solver = new EqualitySolver();

    VariableInstance X, B, Y;
    CharConstantInstance john, bob;
    EqualityInstance E1, E2, E3;
    InEqualityInstance IE1;

    public EqualitySolverTest() {

    }

    @Before
    public void noSetup() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        X = new VariableInstance("X");
        Y = new VariableInstance("Y");
        B = new VariableInstance("B");
        john = new CharConstantInstance("john");
        bob = new CharConstantInstance("bob");
        E1 = new EqualityInstance(john, X);
        E2 = new EqualityInstance(B,Y);
        E3 = new EqualityInstance(X,B);
    }

    @After
    public void noTearDown() {

    }

    // Theta = {Y/bob}, Equalities = {B==Y}
    @Test
    public void simpleEqualityTest1() {
        UniqueIdGenerator.reset();

        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        assignments.put(Y,bob);
        List<EqualityInstance> equalities = new LinkedList<EqualityInstance>();
        equalities.add(new EqualityInstance(B,Y));
        assertTrue(solver.execute(assignments, equalities));
        assertTrue(assignments.size()==2);
        assertTrue(assignments.get(Y)==bob);
        assertTrue(assignments.get(B)==bob);
    }

    @Test
    public void simpleEqualityTest2() {
        UniqueIdGenerator.reset();
        HashMap<VariableInstance,IUnifiableInstance> assignments;
        assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        assignments.put(Y,bob);
        List<EqualityInstance> equalities = new LinkedList<EqualityInstance>();
        equalities.add(new EqualityInstance(Y,bob));
        boolean solverSuccess = solver.execute(assignments, equalities);
        assertTrue(solverSuccess);
        assertTrue(assignments.size()==1);
        assertTrue(assignments.get(Y)==bob);
    }

    // theta = {Y/bob}, equalities = {john=X, B=Y}
    @Test
    public void simpleEqualityTest3() {
        UniqueIdGenerator.reset();
        HashMap<VariableInstance,IUnifiableInstance> assignments;
        assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        assignments.put(Y,bob);
        List<EqualityInstance> equalities = new LinkedList<EqualityInstance>();
        equalities.add(E1);
        equalities.add(E2);
        boolean solverSuccess = solver.execute(assignments, equalities);
        assertTrue(solverSuccess);
        assertTrue(assignments.get(Y)==bob);
        assertTrue(assignments.get(X)==john);
        assertTrue(assignments.get(B)==bob);
    }

    // theta = {Y/bob}, equalities = {john=X, B=Y, X=B}
    @Test
    public void simpleEqualityTest4() {
        UniqueIdGenerator.reset();
        HashMap<VariableInstance,IUnifiableInstance> assignments;
        assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        assignments.put(Y,bob);
        List<EqualityInstance> equalities = new LinkedList<EqualityInstance>();
        equalities.add(E1);
        equalities.add(E2);
        equalities.add(E3);
        boolean solverSuccess = solver.execute(assignments, equalities);

        assertTrue(!solverSuccess);
    }


}
