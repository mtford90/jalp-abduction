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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 02/06/2012
 * Time: 07:47
 * To change this template use File | Settings | File Templates.
 */
public class FliesTest {

    JALPSystem system;

    public FliesTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    flies(X) :- bird(X), not abnormal(X).

    abnormal(X) :- penguin(X).

    bird(X) :- penguin(X).
    bird(X) :- eagle(X).

    penguin(tweety).
    eagle(sam).

    Q = flies(X)

    We expect X/sam
     */
    @Test
    public void fliesX() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance likes = new PredicateInstance("flies",X);
        query.add(likes);
        List<Result> result = system.generateDebugFiles(query, "debug/flies/flies-X");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("sam")));
    }

    /*
   flies(X) :- bird(X), not abnormal(X).

   abnormal(X) :- penguin(X).

   bird(X) :- penguin(X).
   bird(X) :- eagle(X).

   penguin(tweety).
   eagle(sam).

   Q = flies(X)

   We expect no
    */
    @Test
    public void fliesTweety() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance likes = new PredicateInstance("flies",new CharConstantInstance("tweety"));
        query.add(likes);
        List<Result> result = system.generateDebugFiles(query, "debug/flies/flies-tweety");
        assertTrue(result.size()==0);
    }

    /*
   flies(X) :- bird(X), not abnormal(X).

   abnormal(X) :- penguin(X).

   bird(X) :- penguin(X).
   bird(X) :- eagle(X).

   penguin(tweety).
   eagle(sam).

   Q = flies(X)

   We expect yes
    */
    @Test
    public void fliesSam() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance likes = new PredicateInstance("flies",new CharConstantInstance("sam"));
        query.add(likes);
        List<Result> result = system.generateDebugFiles(query, "debug/flies/flies-sam");
        assertTrue(result.size()==1);
    }

    /*
   flies(X) :- bird(X), not abnormal(X).

   abnormal(X) :- penguin(X).

   bird(X) :- penguin(X).
   bird(X) :- eagle(X).

   penguin(tweety).
   eagle(sam).

   Q = flies(X)

   We expect X/tweety
    */
    @Test
    public void fliesAbnormalX() throws IOException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance abnormal = new PredicateInstance("abnormal",X);
        query.add(abnormal);
        List<Result> result = system.generateDebugFiles(query, "debug/flies/abnormal-X");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("tweety")));
    }
}
