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
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 02/06/2012
 * Time: 07:59
 * To change this template use File | Settings | File Templates.
 */
public class DefinitionTest {
    JALPSystem system;

    public DefinitionTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    boy(john).
    girl(jane).
    girl(mary).

    likes(X,Y) :- boy(X),girl(Y).

    Q = likes(john,Y)

    We expect two results, Y/jane or Y/mary
     */
    @Test
    public void definitionTest1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/definition/definition.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",new CharConstantInstance("john"),Y);
        query.add(likes);
        List<Result> result = system.generateDebugFiles(query, "debug/basic/definition/definition");
        for (Result r:result) {
            r.reduce(likes.getVariables());
        }
        assertTrue(result.size()==2);
        assertTrue(result.get(0).getAssignments().get(Y).equals(new CharConstantInstance("mary")));
        assertTrue(result.get(1).getAssignments().get(Y).equals(new CharConstantInstance("jane")));
    }
}
