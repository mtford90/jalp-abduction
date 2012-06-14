package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
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
 * Date: 04/06/2012
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class ClpEx1Test {

    private JALPSystem system;

    public ClpEx1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    abducible(a(V)).
    abducible(b(V)).

    p(X, Y) :- a(X), q(Y).

    q(Y) :- Y in [1,2,3,4], b(Y).

    ic :- a(X), not X in [2,3].
    ic :- a(X), b(Y), X >= Y.

    Q = P(X,Y)

    We expect 3 results: {X/2,Y/4}, {X/2, Y/3}, {X/3,Y/4}

     */
    @Test
    public void clpEx1Test1() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/full/jiefei/clp-ex1.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        VariableInstance Y = new VariableInstance(("Y"));
        PredicateInstance p = new PredicateInstance("p",X,Y);
        query.add(p);
        List<Result> result = system.query(query);
        assertTrue(result.size()==3);

        //TODO Check results.
    }

}


