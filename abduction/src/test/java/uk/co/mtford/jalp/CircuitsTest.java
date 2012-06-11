package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 05/06/2012
 * Time: 08:10
 * To change this template use File | Settings | File Templates.
 */
public class CircuitsTest {
    JALPSystem system;

    public CircuitsTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    output(Gate, Value) :-
        inverter(Gate),
        input(Gate,InValue),
        opposite(InValue,Value),
        not broken(Gate).

    output(Gate, InValue) :-
            inverter(Gate),
            input(Gate,InValue),
            broken(Gate).

    input(g2,Value):- output(g1,Value).
    input(g1,on).


    inverter(g1).
    inverter(g2).

    opposite(on,off).
    opposite(off,on).

    ic :- output(Gate, on),output(Gate, off).
    ic :- input(Gate,on),input(Gate,off).

    abducible(broken(X)).


    Q = output(g2,on)

     */
    @Test
    public void circuitTest1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/jiefei/circuits1.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance g1 = new CharConstantInstance("g1");
        CharConstantInstance g2 = new CharConstantInstance("g2");
        CharConstantInstance on = new CharConstantInstance("on");
        CharConstantInstance off = new CharConstantInstance("off");
        PredicateInstance output = new PredicateInstance("output",g2,on);
        query.add(output);
        List<Result> result = system.generateDebugFiles(query,"debug/full/jiefei/circuits1-1");
        assertTrue(result.size()==2);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        assertTrue(resultOne.getStore().abducibles.size() == 2);
        assertTrue(resultOne.getStore().abducibles.contains(new PredicateInstance("broken", g1)));
        assertTrue(resultOne.getStore().abducibles.contains(new PredicateInstance("broken",g2)));
        assertTrue(resultTwo.getStore().abducibles.size() == 0);
    }

    /*
   output(Gate, Value) :-
       inverter(Gate),
       input(Gate,InValue),
       opposite(InValue,Value),
       not broken(Gate).

   output(Gate, InValue) :-
           inverter(Gate),
           input(Gate,InValue),
           broken(Gate).

   input(g2,Value):- output(g1,Value).
   input(g1,on).


   inverter(g1).
   inverter(g2).

   opposite(on,off).
   opposite(off,on).

   ic :- output(Gate, on),output(Gate, off).
   ic :- input(Gate,on),input(Gate,off).

   abducible(broken(X)).


   Q = output(g2,off)

    */
    @Test
    public void circuitTest2() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/jiefei/circuits1.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance g1 = new CharConstantInstance("g1");
        CharConstantInstance g2 = new CharConstantInstance("g2");
        CharConstantInstance on = new CharConstantInstance("on");
        CharConstantInstance off = new CharConstantInstance("off");
        PredicateInstance output = new PredicateInstance("output",g2,off);
        query.add(output);
        List<Result> result = system.generateDebugFiles(query,"debug/full/jiefei/circuits1-2");
        assertTrue(result.size()==2);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        assertTrue(resultOne.getStore().abducibles.size() == 1);
        assertTrue(resultOne.getStore().abducibles.contains(new PredicateInstance("broken",g2)));
        assertTrue(resultTwo.getStore().abducibles.size() == 1);
        assertTrue(resultTwo.getStore().abducibles.contains(new PredicateInstance("broken",g1)));
    }

    /*
    output(Gate, Value) :-
	inverter(Gate),
	input(Gate,InValue),
	opposite(InValue,Value),
	not broken(Gate).

    output(Gate, InValue) :-
        inverter(Gate),
        input(Gate,InValue),
        broken(Gate).

    input(g2,Value):- output(g1,Value).
    input(g3,Value):- output(g2,Value).
    input(g1,on).

    inverter(g1).
    inverter(g2).
    inverter(g3).

    opposite(on,off).
    opposite(off,on).

    ic :- output(Gate, on),output(Gate, off).
    ic :- input(Gate,on),input(Gate,off).

    abducible(broken(X)).


   Q = output(g2,on)

    */
    @Test
    public void circuitTest3() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/jiefei/circuits2.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance g1 = new CharConstantInstance("g1");
        CharConstantInstance g2 = new CharConstantInstance("g2");
        CharConstantInstance on = new CharConstantInstance("on");
        CharConstantInstance off = new CharConstantInstance("off");
        PredicateInstance output = new PredicateInstance("output",g2,on);
        query.add(output);
        List<Result> result = system.generateDebugFiles(query,"debug/full/jiefei/circuits2-1");
        assertTrue(result.size()==2);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        assertTrue(resultOne.getStore().abducibles.size() == 2);
        assertTrue(resultOne.getStore().abducibles.contains(new PredicateInstance("broken",g1)));
        assertTrue(resultOne.getStore().abducibles.contains(new PredicateInstance("broken",g2)));
        assertTrue(resultTwo.getStore().abducibles.size() == 0);
    }

    /*
    output(Gate, Value) :-
        inverter(Gate),
        input(Gate,InValue),
        opposite(InValue,Value),
        not broken(Gate).

    output(Gate, InValue) :-
        inverter(Gate),
        input(Gate,InValue),
        broken(Gate).

    input(g2,Value):- output(g1,Value).
    input(g3,Value):- output(g2,Value).
    input(g1,on).

    inverter(g1).
    inverter(g2).
    inverter(g3).

    opposite(on,off).
    opposite(off,on).

    ic :- output(Gate, on),output(Gate, off).
    ic :- input(Gate,on),input(Gate,off).

    abducible(broken(X)).



   Q = output(g2,off)

   Expected result is an abducible hypothesizing that the car has no fuel.
    */
    @Test
    public void circuitTest4() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/full/jiefei/circuits2.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance g1 = new CharConstantInstance("g1");
        CharConstantInstance g2 = new CharConstantInstance("g2");
        CharConstantInstance g3 = new CharConstantInstance("g3");
        CharConstantInstance on = new CharConstantInstance("on");
        CharConstantInstance off = new CharConstantInstance("off");
        PredicateInstance output = new PredicateInstance("output",g2,off);
        query.add(output);
        List<Result> result = system.generateDebugFiles(query,"debug/full/jiefei/circuits2-2");
        assertTrue(result.size()==4);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        Result resultThree = result.get(2);
        Result resultFour = result.get(3);
        assertTrue(resultOne.getStore().abducibles.size() == 1);
        assertTrue(resultOne.getStore().abducibles.contains(new PredicateInstance("broken",g2)));
        assertTrue(resultTwo.getStore().abducibles.size() == 2);
        assertTrue(resultTwo.getStore().abducibles.contains(new PredicateInstance("broken",g2)));
        assertTrue(resultTwo.getStore().abducibles.contains(new PredicateInstance("broken",g3)));
        assertTrue(resultThree.getStore().abducibles.size() == 1);
        assertTrue(resultThree.getStore().abducibles.contains(new PredicateInstance("broken",g1)));
        assertTrue(resultFour.getStore().abducibles.size() == 2);
        assertTrue(resultFour.getStore().abducibles.contains(new PredicateInstance("broken",g1)));
        assertTrue(resultFour.getStore().abducibles.contains(new PredicateInstance("broken",g3)));
    }
}
