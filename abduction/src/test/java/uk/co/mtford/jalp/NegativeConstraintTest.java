package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;

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
    p(X,Y) :- X in [1,2,3], Y in [1,2], X>Y.

    ic :- X in [2].

    We expect two results: {X/3, Y/1}, {X/3,Y/2}
    */
    @Test
    public void greaterThanTest1() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/negative/greater-than.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance q = new PredicateInstance("p",X,Y);
        query.add(q);
        List<Result> result = system.generateDebugFiles(query, "debug/constraint/negative/greater-than-1");
        assertTrue(result.size()==2);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        JALP.reduceResult(resultOne);
        JALP.reduceResult(resultTwo);
        //assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("mary"));
        //assertTrue(resultTwo.getAssignments().get(X).equals(new CharConstantInstance("bob")));
    }


}
