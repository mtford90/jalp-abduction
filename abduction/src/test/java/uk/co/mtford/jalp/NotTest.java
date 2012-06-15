package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 02/06/2012
 * Time: 08:07
 * To change this template use File | Settings | File Templates.
 */
public class NotTest {

    JALPSystem system;

    public NotTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    abducible(girl(Y)).

    boy(john).

    likes(X,Y) :- boy(X), not girl(Y).

    Q = likes(john, jane)

    Result should be that jane isnt a girl (denial).
     */
    @Test
    public void notTest1() throws ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException, FileNotFoundException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/basic/negation/not.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        CharConstantInstance jane = new CharConstantInstance("jane");
        PredicateInstance likes = new PredicateInstance("likes",john,jane);
        query.add(likes);
        List<Result> result = system.query(query);
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        assertTrue(resultOne.getStore().denials.size()==1);
        assertTrue(resultOne.getStore().denials.get(0).getBody().size()==1);
        assertTrue(resultOne.getStore().denials.get(0).getBody().get(0).equals(new PredicateInstance("girl",jane)));
    }
}
