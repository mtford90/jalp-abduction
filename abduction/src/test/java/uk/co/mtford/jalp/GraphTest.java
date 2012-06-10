package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
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
 * Date: 02/06/2012
 * Time: 07:14
 * To change this template use File | Settings | File Templates.
 */
public class GraphTest {

    JALPSystem system;

    public GraphTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    edge(node1,node2).
    edge(node2,node1).

    colour(red).
    colour(blue).

    ic :- colour(C), edge(N,M), has_colour(N,C), has_colour(M,C).

    abducible(has_colour(N,C)).

    Q = has_colour(node1,red)

    We expect 1 result i.e. integrity constraints that explain that node2 cannot have
    the same colour red.

     */
    @Test
    public void graphTest1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/graph/graph.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour = new PredicateInstance("has_colour",node1,red);
        query.add(has_colour);
        List<Result> result = system.generateDebugFiles(query, "debug/full/graph/graph-1");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALP.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(has_colour));
        DenialInstance denial = new DenialInstance(new PredicateInstance("has_colour",node2,red));
        assertTrue(resultOne.getStore().denials.contains(denial));
    }

    /*
   edge(node1,node2).
   edge(node2,node1).

   colour(red).
   colour(blue).

   ic :- colour(C), edge(N,M), has_colour(N,C), has_colour(M,C).

   abducible(has_colour(N,C)).

   Q = has_colour(node1,red)

    Q = {has_colour(node1,C), has_colour(node2, D)}

    We expect 9 results. Either assignments to C and D or blue/red or integrity constraints.
    This is as per the result that ASystem gives.

    */
    @Test
    public void graphTest2() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/graph/graph.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance C = new VariableInstance("C");
        VariableInstance D = new VariableInstance("D");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour1 = new PredicateInstance("has_colour",node1,C);
        PredicateInstance has_colour2 = new PredicateInstance("has_colour",node2,D);
        query.add(has_colour1);
        query.add(has_colour2);
        List<Result> result = system.generateDebugFiles(query, "debug/full/graph/graph-2");
        assertTrue(result.size()==7); // Matches jiefeis ASystem implementation.
    }

    /*

    edge(node1,node2).
    edge(node2,node1).
    edge(node1,node3).
    edge(node3,node1).

    colour(red).
    colour(blue).

    ic :- colour(C), edge(N,M), has_colour(N,C), has_colour(M,C).

    abducible(has_colour(N,C)).

    Q = has_colour(node1,red)

    Expected result is one with constraints explaining the fact that neither node2 or node3 can be red.

     */
    @Test
    public void graph2Test1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/graph/graph2.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance node3 = new CharConstantInstance("node3");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour = new PredicateInstance("has_colour",node1,red);
        query.add(has_colour);
        List<Result> result = system.generateDebugFiles(query, "debug/full/graph/graph2-1");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALP.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(has_colour));
        DenialInstance denial1 = new DenialInstance(new PredicateInstance("has_colour",node2,red));
        DenialInstance denial2 = new DenialInstance(new PredicateInstance("has_colour",node3,red));
        assertTrue(resultOne.getStore().denials.contains(denial1));
        assertTrue(resultOne.getStore().denials.contains(denial2));
    }

    /*
    edge(node1,node2).
    edge(node2,node1).

    colour(X) :- X in [red,blue].

    ic :- colour(C), edge(N,M), has_colour(N,C), has_colour(M,C).

    abducible(has_colour(N,C)).

    Q = has_colour(node1,red)

    We expect 1 result i.e. integrity constraints that explain that node2 cannot have
    the same colour red.
    */
    @Test
    public void graphConstraintTest1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/graph/graph-constraint.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour = new PredicateInstance("has_colour",node1,red);
        query.add(has_colour);
        List<Result> result = system.generateDebugFiles(query, "debug/full/graph/graph-constraint-1");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALP.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(has_colour));
        DenialInstance denial = new DenialInstance(new PredicateInstance("has_colour",node2,red));
        assertTrue(resultOne.getStore().denials.contains(denial));
    }

    /*
    edge(node1,node2).
    edge(node2,node1).

    colour(X) :- X in [red,blue].

    ic :- colour(C), edge(N,M), has_colour(N,C), has_colour(M,C).

    abducible(has_colour(N,C)).

    Q = {has_colour(node1,C), has_colour(node2, D)}

    We expect 9 results. Either assignments to C and D or blue/red or integrity constraints.
    This is as per the result that ASystem gives.
    */
    @Test
    public void graphConstraintTest2() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/graph/graph-constraint.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance C = new VariableInstance("C");
        VariableInstance D = new VariableInstance("D");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour1 = new PredicateInstance("has_colour",node1,C);
        PredicateInstance has_colour2 = new PredicateInstance("has_colour",node2,D);
        query.add(has_colour1);
        query.add(has_colour2);
        List<Result> result = system.generateDebugFiles(query, "debug/full/graph/graph-constraint-2");
        assertTrue(result.size()==7); // Matches jiefeis ASystem implementation.
    }
}
