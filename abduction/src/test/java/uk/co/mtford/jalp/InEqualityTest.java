package uk.co.mtford.jalp;

import org.junit.*;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 09:55
 * To change this template use File | Settings | File Templates.
 */
public class InEqualityTest {
    JALPSystem system;

    public InEqualityTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    blah(X) :- X in [1,2,3], a(X).

    ic :- a(X), X!=1.

    abducible(a(X)).

    Q = blah(X)

    */
    @org.junit.Test
    public void oneTest() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/inequality/one.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance one = new IntegerConstantInstance(1);
        VariableInstance X = new VariableInstance("X");
        PredicateInstance blah = new PredicateInstance("blah",X);
        query.add(blah);
        List<Result> result = system.generateDebugFiles(query, "debug/inequality/one");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALP.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size() == 1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("a",one)));
    }
}
