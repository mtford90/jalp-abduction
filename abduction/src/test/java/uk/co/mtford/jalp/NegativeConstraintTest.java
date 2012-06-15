package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 06/06/2012
 * Time: 07:20
 * To change this template use File | Settings | File Templates.
 */
public class NegativeConstraintTest {

    JALPSystem system;

    public NegativeConstraintTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y, not X in [2].

    Q = P(X,Y)

    We expect two results: {X/3, Y/1}, {X/3,Y/2}
    */
    @Test
    public void inIntegerTest1() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/in-integer.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",X,Y);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==2);
        boolean one = false;
        boolean two = false;
        List<VariableInstance> relevantVariables = new LinkedList<VariableInstance>();
        relevantVariables.add(X);
        relevantVariables.add(Y);
        for (Result r:result) {
            r.reduce(relevantVariables);
            if (r.getAssignments().get(X).equals(new IntegerConstantInstance(3)) &&
                    r.getAssignments().get(Y).equals(new IntegerConstantInstance(2))) {
                one = true;
            }
            if (r.getAssignments().get(X).equals(new IntegerConstantInstance(3)) &&
                    r.getAssignments().get(Y).equals(new IntegerConstantInstance(1))) {
                two = true;
            }
        }
        assertTrue(one && two);

    }

    /*
    p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y, not X in [2].

    Q = P(two,Y).
    */
    @Test
    public void inIntegerTest2() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/in-integer.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance two = new IntegerConstantInstance(2);
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",two,Y);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==0);

    }

    @Test
    public void inConstantListTest1() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/in-constant-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance q = new PredicateInstance("p",X);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        resultOne.reduce(q.getVariables());
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("bob")));
    }

    @Test
    public void inConstantListTest2() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/in-constant-list.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance q = new PredicateInstance("p",new CharConstantInstance("mary"));
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==0);
    }

    @Test
      public void lessThanTest1() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/less-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",X,Y);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==6);
        List<VariableInstance> relevantVariables = new LinkedList<VariableInstance>();
        relevantVariables.add(X);
        relevantVariables.add(Y);
        for (Result r:result) {
            r.reduce(relevantVariables);
            IntegerConstantInstance XVal = (IntegerConstantInstance) r.getAssignments().get(X);
            IntegerConstantInstance YVal = (IntegerConstantInstance) r.getAssignments().get(Y);
            assertTrue(YVal.getValue()>=XVal.getValue());
        }
    }

    @Test
    public void lessThanEqTest1() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/less-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",X,Y);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==3);
        for (Result r:result) {
            r.reduce(q.getVariables());
            IntegerConstantInstance XVal = (IntegerConstantInstance) r.getAssignments().get(X);
            IntegerConstantInstance YVal = (IntegerConstantInstance) r.getAssignments().get(Y);
            assertTrue(YVal.getValue()>XVal.getValue());
        }
    }

    @Test
    public void greaterThanTest1() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/greater-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",X,Y);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==6);
        for (Result r:result) {
            r.reduce(q.getVariables());
            IntegerConstantInstance XVal = (IntegerConstantInstance) r.getAssignments().get(X);
            IntegerConstantInstance YVal = (IntegerConstantInstance) r.getAssignments().get(Y);
            assertTrue(YVal.getValue()<=XVal.getValue());
        }
    }

    @Test
    public void greaterThanEqTest1() throws IOException, ParseException,  uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/constraint/negative/greater-than-eq.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",X,Y);
        query.add(q);
        List<Result> result = system.query(query);
        assertTrue(result.size()==3);
        for (Result r:result) {
            r.reduce(q.getVariables());
            IntegerConstantInstance XVal = (IntegerConstantInstance) r.getAssignments().get(X);
            IntegerConstantInstance YVal = (IntegerConstantInstance) r.getAssignments().get(Y);
            assertTrue(YVal.getValue()<XVal.getValue());
        }
    }


}
