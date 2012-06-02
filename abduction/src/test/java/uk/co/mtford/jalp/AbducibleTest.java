package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 01/06/2012
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class AbducibleTest {

    JALPSystem system;

    public AbducibleTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    boy(john).
    likes(X,Y) :- boy(X), girl(Y).
    abducible(girl(Y)).

    Q = likes(john,Y).

    We expect to collect an abducible girl(Y).
     */
    @Test
    public void abducibleTest1() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/abducible/abducible.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",new CharConstantInstance("john"),Y);
        query.add(likes);
        List<Result> result = system.generateDebugFiles(query, "debug/abducible/abducible-test-1");
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).isSameFunction(new PredicateInstance("girl",Y)));
    }

    /*
   boy(john).
   likes(X,Y) :- boy(X), girl(Y).
   abducible(girl(Y)).

   Q = likes(john,jane).

   We expect to collect an abducible girl(jane).
    */
    @Test
    public void abducibleTest2() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/abducible/abducible.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance(("john"));
        CharConstantInstance jane = new CharConstantInstance("jane");
        PredicateInstance likes = new PredicateInstance("likes",john,jane);
        query.add(likes);
        List<Result> result = system.generateDebugFiles(query, "debug/abducible/abducible-test-2");
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("girl", jane)));
    }

    /*
    p(X) :- a(X), not q(X).
    q(1).

    abducible(a(X)).

    Q = p(X).

    We expect to collect an inequality X!=1
    */
    @Test
    public void ungroundAbducible() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/abducible/unground-abducible.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance a = new PredicateInstance("p",X);
        query.add(a);
        List<Result> result = system.generateDebugFiles(query, "debug/abducible/unground-abducible");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        IUnifiableAtomInstance XAssignment = resultOne.getAssignments().get(X);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("a",XAssignment)));
        assertTrue(resultOne.getStore().equalities.contains(new InEqualityInstance(XAssignment,new IntegerConstantInstance(1))));
    }
}
