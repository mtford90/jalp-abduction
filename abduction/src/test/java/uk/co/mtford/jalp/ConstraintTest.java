package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.*;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 02/06/2012
 * Time: 06:36
 * To change this template use File | Settings | File Templates.
 */
public class ConstraintTest {

    JALPSystem system;

    public ConstraintTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    q(Y) :- Y in [john,bob,mary].
    Q  = q(X)

    We expect three results i.e. an assignment to X of one of 3 in the list.
     */
    @Test
    public void constantListTest1() throws Exception, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/constant-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance q = new PredicateInstance("q",X);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/constant-list-1");
        assertTrue(result.size()==3);
        boolean mary = false;
        boolean bob = false;
        boolean john = false;
        for (Result r:result) {
            r.reduce(q.getVariables());
            if (r.getAssignments().get(X).equals(new CharConstantInstance("mary"))) {
                mary = true;
            }
            if (r.getAssignments().get(X).equals(new CharConstantInstance("bob"))) {
                bob = true;
            }
            if (r.getAssignments().get(X).equals(new CharConstantInstance("john"))) {
                john = true;
            }
        }
        assertTrue(mary && bob && john);
    }

    /*
   q(Y) :- Y in [john,bob,mary].
   Q  = q(mike)

   We expect to fail i.e. 'no'
    */
    @Test
    public void constantListTest2() throws Exception, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/constant-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance mike = new CharConstantInstance("mike");
        PredicateInstance q = new PredicateInstance("q",mike);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/constant-list-2");
        assertTrue(result.size()==0);
    }

    /*
   q(Y) :- Y in [john,bob,mary].
   Q  = q(bob)

   We expect one result i.e. 'yes'
    */
    @Test
    public void constantListTest3() throws Exception, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/constant-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance bob = new CharConstantInstance("bob");
        PredicateInstance q = new PredicateInstance("q",bob);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/constant-list-3");

        assertTrue(result.size()==1);
    }

    /*
    q(X) :- X in [1,2,3].
    Q = q(3)

    We expect 'yes'
     */
    @Test
    public void integerListTest1() throws Exception, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/integer-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance three = new IntegerConstantInstance(3);
        PredicateInstance q = new PredicateInstance("q",three);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/integer-list-1");
        assertTrue(result.size()==1);
    }


    /*
   q(X) :- X in [1,2,3].
   Q = q(5)

   We expect 'no'
    */
    @Test
    public void integerListTest2() throws Exception, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/integer-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance three = new IntegerConstantInstance(5);
        PredicateInstance q = new PredicateInstance("q",three);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/integer-list-2");
        assertTrue(result.size()==0);
    }


    /*
   q(X) :- X in [1,2,3].
   Q = q(X)

   We expect 3 results.
    */
    @Test
    public void integerListTest3() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/integer-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance q = new PredicateInstance("q",X);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/integer-list-3");
        assertTrue(result.size()==3);
    }

    /*
    p(X) :- X in [1,2,3], Y in [1,2], X<Y.
    Q = p(X)

    We expect one result: X/1
     */
    @Test
    public void lessThanTest1() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance p = new PredicateInstance("p",X);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-test-1");
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        resultOne.reduce(p.getVariables());
        assertTrue(resultOne.getAssignments().get(X).equals(new IntegerConstantInstance(1)));
    }

    /*
   p(X) :- X in [1,2,3], Y in [1,2], X<Y.
   Q = p(1)

   We expect one result: yes
    */
    @Test
    public void lessThanTest2() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance p = new PredicateInstance("p",new IntegerConstantInstance(1));
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-test-2");
        assertTrue(result.size()==1);
    }

    /*
   p(X) :- X in [1,2,3], Y in [1,2], X<Y.
   Q = p(2)

   We expect one result no
    */
    @Test
    public void lessThanTest3() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance p = new PredicateInstance("p",new IntegerConstantInstance(2));
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-test-3");
        assertTrue(result.size()==0);
    }

    /*
   p(X) :- X in [1,2,3], Y in [1,2], X<Y.
   Q = p(3)

   We expect result no
    */
    @Test
    public void lessThanTest4() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance p = new PredicateInstance("p",new IntegerConstantInstance(3));
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-test-4");
        assertTrue(result.size()==0);
    }

    /*
   p(X) :- X in [1,2,3], Y in [1,2], X<Y.
   Q = p(4)

   We expect result no
    */
    @Test
    public void lessThanTest5() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance p = new PredicateInstance("p",new IntegerConstantInstance(4));
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-test-5");
        assertTrue(result.size()==0);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y.
   Q = p(X,Y)

   We expect 3 results.
    */
    @Test
    public void greaterThanTest1() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",X,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-1");
        assertTrue(result.size()==3);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y.
   Q = p(1,Y)

   We expect no result.
    */
    @Test
    public void greaterThanTest2() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance one = new IntegerConstantInstance(1);
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",one,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-2");
        assertTrue(result.size()==0);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y.
   Q = p(4,Y)

   We expect no result.
    */
    @Test
    public void greaterThanTest3() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance four = new IntegerConstantInstance(4);
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",four,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-3");
        assertTrue(result.size()==0);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y.
   Q = p(1,Y)

   We expect Y/1
    */
    @Test
    public void greaterThanTest4() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance two = new IntegerConstantInstance(2);
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",two,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-4");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        resultOne.reduce(p.getVariables());
        assertTrue(resultOne.getAssignments().get(Y).equals(new IntegerConstantInstance(1)));
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>=Y.
   Q = p(X,Y)

   We expect 5 results.
    */
    @Test
    public void greaterThanEqTest1() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",X,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-eq-1");
        assertTrue(result.size()==5);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>=Y.
   Q = p(1,Y)

   We expect Y/1
    */
    @Test
    public void greaterThanEqTest2() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance one = new IntegerConstantInstance(1);
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",one,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-eq-2");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        resultOne.reduce(p.getVariables());
        assertTrue(resultOne.getAssignments().get(Y).equals(new IntegerConstantInstance(1)));
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X>=Y.
   Q = p(4,Y)

   We expect no result.
    */
    @Test
    public void greaterThanEqTest3() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/greater-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance four = new IntegerConstantInstance(4);
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",four,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/greater-than-eq-3");
        assertTrue(result.size()==0);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X<=Y.
   Q = p(X,Y)

   We expect 3 explanations.
    */
    @Test
    public void lessThanEqTest1() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",X,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-eq-1");
        assertTrue(result.size()==3);
    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X<=Y.
   Q = p(X,Y)

    We expect 2 results, Y/2 and Y/1
    */
    @Test
    public void lessThanEqTest2() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance one = new IntegerConstantInstance(1);
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",one,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-eq-2");
        boolean resultOne = false;
        boolean resultTwo = false;
        for (Result r:result) {
            r.reduce(p.getVariables());
            if (r.getAssignments().get(Y).equals(new IntegerConstantInstance(2))) {
                resultOne=true;
            }
            else if (r.getAssignments().get(Y).equals(new IntegerConstantInstance(1))) {
                resultTwo = true;
            }

    }
        assertTrue(resultOne && resultTwo);

    }

    /*
   p(X,Y) :- X in [1,2,3], Y in [1,2], X<=Y.
   Q = p(X,Y)

    */
    @Test
    public void lessThanEqTest3() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance four = new IntegerConstantInstance(4);
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p = new PredicateInstance("p",four,Y);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-eq-3");
        assertTrue(result.size()==0);
    }

    /*

    /*
    p(X) :- a(X), not q(X).
    q(X) :- X in [1].

    abducible(a(X)).

    We expect to collect a FD not X in [1].

    @Test
    public void ungroundAbducible() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/unground-abducible.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance a = new PredicateInstance("p",X);
        query.add(a);
        List<Result> result = system.generateDebugFiles(query, "debug/constraint/unground-abducible");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALP.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        IUnifiableAtomInstance XAssignment = resultOne.getAssignments().get(X);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("a",XAssignment)));
        IntegerConstantListInstance list = new IntegerConstantListInstance();
        list.getList().add(new IntegerConstantInstance(1));
        NegativeConstraintInstance expectedConstraint = new NegativeConstraintInstance(new InIntegerListConstraintInstance((ITermInstance) XAssignment, list));
        assertTrue(resultOne.getStore().constraints.contains(expectedConstraint));
    }

    */

    /*
   p(X) :- X<Y, X in [1,2,3], Y in [1,2].
   Q = p(X)

   We expect one result: X/1
    */
    @Test
    public void lessThanInfDomTest1() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/individual/less-than-inf-dom.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance p = new PredicateInstance("p",X);
        query.add(p);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/constraint/individual/less-than-inf-dom-test-1");
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        resultOne.reduce(p.getVariables());
        assertTrue(resultOne.getAssignments().get(X).equals(new IntegerConstantInstance(1)));
    }

}
